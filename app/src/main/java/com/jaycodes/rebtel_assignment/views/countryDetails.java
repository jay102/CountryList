package com.jaycodes.rebtel_assignment.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.jaycodes.rebtel_assignment.R;
import com.jaycodes.rebtel_assignment.databinding.ActivityCountryDetailsBinding;

public class countryDetails extends AppCompatActivity implements countryDetailsFragment.TitleUpdater {
    ActivityCountryDetailsBinding countryDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //bind the required views
        countryDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_country_details);
        Toolbar toolbar = countryDetailsBinding.detailToolbar;
        ImageView header = countryDetailsBinding.header;
        setSupportActionBar(toolbar);
        //get access to actionbar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            //load flag to collapsing toolbar header
            Bundle arguments = new Bundle();
            GlideToVectorYou.justLoadImage(countryDetails.this, Uri.parse(getIntent().getStringExtra("flag")), header);

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            //also transport details to fragment using a bundle
            arguments.putString(countryDetailsFragment.COUNTRY_ID,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_ID));
            arguments.putString(countryDetailsFragment.COUNTRY_FLAG,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_FLAG));
            arguments.putString(countryDetailsFragment.COUNTRY_CAPITAL,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_CAPITAL));
            arguments.putString(countryDetailsFragment.COUNTRY_CALLCODES,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_CALLCODES));
            arguments.putString(countryDetailsFragment.COUNTRY_TIMEZONE,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_TIMEZONE));
            arguments.putString(countryDetailsFragment.COUNTRY_POPULATION,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_POPULATION));
            arguments.putString(countryDetailsFragment.COUNTRY_CURRENCY_NAME,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_CURRENCY_NAME));
            arguments.putString(countryDetailsFragment.COUNTRY_CURRENCY_CODE,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_CURRENCY_CODE));
            arguments.putString(countryDetailsFragment.COUNTRY_CURRENCY_SYMBOL,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_CURRENCY_SYMBOL));
            arguments.putString(countryDetailsFragment.COUNTRY_LANGUAGES_NAME,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_LANGUAGES_NAME));
            arguments.putString(countryDetailsFragment.COUNTRY_LANGUAGES_NATIVENAME,
                    getIntent().getStringExtra(countryDetailsFragment.COUNTRY_LANGUAGES_NATIVENAME));
            countryDetailsFragment fragment = new countryDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }
 //handle back button with navigateUpTo method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
//implement interface method from fragment to get access to title being passed then set title of activity
    @Override
    public void updateAppBar(String title) {
        countryDetailsBinding.toolbarLayout.setTitle(title);
    }
}
