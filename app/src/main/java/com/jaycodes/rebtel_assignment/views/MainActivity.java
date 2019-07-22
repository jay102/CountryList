package com.jaycodes.rebtel_assignment.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.jaycodes.rebtel_assignment.R;
import com.jaycodes.rebtel_assignment.adapters.RecyclerAdapter;
import com.jaycodes.rebtel_assignment.databinding.CountriesBinding;
import com.jaycodes.rebtel_assignment.repository.models.countryModel;
import com.jaycodes.rebtel_assignment.viewModels.MainActivityViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.recyclerViewClickListener, countryDetailsFragment.TitleUpdater {
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private boolean mTwoPane;
    MainActivityViewModel  mainActivityViewModel;
    ArrayList<countryModel> countryModelArrayList = new ArrayList<>();
    CountriesBinding countriesBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countriesBinding = DataBindingUtil.setContentView(this,R.layout.countries);
        recyclerView = countriesBinding.included.recyclerview;
        progressBar = countriesBinding.indeterminateBar;
        Toolbar toolbar =  countriesBinding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (countriesBinding.included.itemDetailContainer != null) {
            mTwoPane = true;
        }
        //setting up viewModels
        //subscribe to viewModel to get updated data and for our data to survive configuration changes
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel = new MainActivityViewModel(); //initialize viewModel
        mainActivityViewModel.init(this);
        mainActivityViewModel.getCountryList().observe(this, listResource -> { //using lambdas to get access to observer and data here
            assert listResource != null;
            switch (listResource.status){
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE); //case where data is loading show progressbar and set to false on success and failure
                    progressBar.setIndeterminate(true);
                    break;
                case SUCCESS:
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.GONE);
                    assert listResource.data != null;
                    countryModelArrayList.addAll(listResource.data);
                    break;
                case ERROR:
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.GONE);
                    if(listResource.data!= null){
                        countryModelArrayList.addAll(listResource.data);
                    }else{
                        Toast.makeText(MainActivity.this, "No data available in Cache, Connect to a network", Toast.LENGTH_LONG).show();
                        Log.e("Error", listResource.message);
                    }
                    break;
            }
            setUpRecyclerView();
        });


    }
//setup recyclerView, layout manager and adapter
    void setUpRecyclerView(){
        if(adapter == null){
            adapter = new RecyclerAdapter(this,countryModelArrayList,this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        }else{
            adapter.notifyDataSetChanged();
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);  //inflate menu
        //find menu item and setQuery listener to detect text and filter
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
//implement interface onClick method and override it to get access to position and current Item being clicked.
    //if screen is large(landscape) replace fragment with details of clicked item else move to next activity with the details of the clicked item
    @Override
    public void onRecyclerViewClick(View v, int position,countryModel currentItem) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(countryDetailsFragment.COUNTRY_ID, currentItem.getName());
            arguments.putString(countryDetailsFragment.COUNTRY_FLAG, currentItem.getFlag());
            arguments.putString(countryDetailsFragment.COUNTRY_CAPITAL, currentItem.getCapital());
            arguments.putString(countryDetailsFragment.COUNTRY_CALLCODES, currentItem.getCallingCodes()[0]);
            arguments.putString(countryDetailsFragment.COUNTRY_TIMEZONE, currentItem.getTimezones()[0]);
            arguments.putString(countryDetailsFragment.COUNTRY_POPULATION, currentItem.getPopulation());
            arguments.putString(countryDetailsFragment.COUNTRY_CURRENCY_NAME, currentItem.getCurrencies().get(0).getName());
            arguments.putString(countryDetailsFragment.COUNTRY_CURRENCY_CODE, currentItem.getCurrencies().get(0).getCode());
            arguments.putString(countryDetailsFragment.COUNTRY_CURRENCY_SYMBOL, currentItem.getCurrencies().get(0).getSymbol());
            arguments.putString(countryDetailsFragment.COUNTRY_LANGUAGES_NAME, currentItem.getLanguages().get(0).getName());
            arguments.putString(countryDetailsFragment.COUNTRY_LANGUAGES_NATIVENAME, currentItem.getLanguages().get(0).getNativeName());
            countryDetailsFragment fragment = new countryDetailsFragment();
            fragment.setArguments(arguments);
            this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, countryDetails.class);
            intent.putExtra("flag",currentItem.getFlag());
            intent.putExtra(countryDetailsFragment.COUNTRY_ID, currentItem.getName());
            intent.putExtra(countryDetailsFragment.COUNTRY_FLAG, currentItem.getFlag());
            intent.putExtra(countryDetailsFragment.COUNTRY_CAPITAL, currentItem.getCapital());
            intent.putExtra(countryDetailsFragment.COUNTRY_CALLCODES, currentItem.getCallingCodes()[0]);
            intent.putExtra(countryDetailsFragment.COUNTRY_TIMEZONE, currentItem.getTimezones()[0]);
            intent.putExtra(countryDetailsFragment.COUNTRY_POPULATION, currentItem.getPopulation());
            intent.putExtra(countryDetailsFragment.COUNTRY_CURRENCY_NAME, currentItem.getCurrencies().get(0).getName());
            intent.putExtra(countryDetailsFragment.COUNTRY_CURRENCY_CODE, currentItem.getCurrencies().get(0).getCode());
            intent.putExtra(countryDetailsFragment.COUNTRY_CURRENCY_SYMBOL, currentItem.getCurrencies().get(0).getSymbol());
            intent.putExtra(countryDetailsFragment.COUNTRY_LANGUAGES_NAME, currentItem.getLanguages().get(0).getName());
            intent.putExtra(countryDetailsFragment.COUNTRY_LANGUAGES_NATIVENAME, currentItem.getLanguages().get(0).getNativeName());
            startActivity(intent);
        }
    }
//override our title update here for the sake of our Tab view layout
    @Override
    public void updateAppBar(String title) {
        countriesBinding.toolbar.setTitle(title);
    }
}
