package com.jaycodes.rebtel_assignment.adapters;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.jaycodes.rebtel_assignment.databinding.CountryItemViewBinding;
import com.jaycodes.rebtel_assignment.repository.models.countryModel;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.mViewHolder> implements Filterable {

    private ArrayList<countryModel> countryList;//list of countries populated from mainActivity
    private ArrayList<countryModel> countryListFull; //copy of List used for filtering
    private recyclerViewClickListener recyclerViewClickListener; //class variable to track click items
    private Context context;

    public RecyclerAdapter(Context context, ArrayList<countryModel> countryList, recyclerViewClickListener recyclerViewClickListener) {
        this.countryList = countryList;
        countryListFull = countryList; //initialize the copy of our list with the original
        this.context = context;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        //bind our layout and return the view with our clickListener
        CountryItemViewBinding countryItemViewBinding = CountryItemViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new mViewHolder(countryItemViewBinding.getRoot(), recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.mViewHolder viewHolder, int i) {
        countryModel item =  countryListFull.get(i);
        viewHolder.bind(item); //bind our viewHolder to get current item and position
    }

    @Override
    public int getItemCount() {
        return countryListFull.size();
    } //return list of filtered items

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    countryListFull = countryList;
                }else{
                    ArrayList<countryModel> filteredList = new ArrayList<>();
                    for(countryModel model: countryList){
                        if (model.getName().toLowerCase().startsWith(charString.toLowerCase().trim())) {
                           // filteredList.clear();
                            filteredList.add(model);
                        }
                    }
                    countryListFull = filteredList;
                }
                FilterResults results = new FilterResults();
                results.count = countryListFull.size();
                results.values = countryListFull;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryListFull = (ArrayList<countryModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView countries;
        ImageView flags;
        recyclerViewClickListener recyclerViewClickListener;
        private CountryItemViewBinding countryItemViewBinding;
        private countryModel currentItem; //declare variable of type countryModel

        private mViewHolder(View itemView, recyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            countryItemViewBinding = DataBindingUtil.bind(itemView); //bind the returned view
            assert countryItemViewBinding != null;
            countries = countryItemViewBinding.countries;
            flags = countryItemViewBinding.flags;
            this.recyclerViewClickListener = recyclerViewClickListener;
            itemView.setOnClickListener(this); //set onClickListener to this
        }

        void bind(countryModel country){
            countryItemViewBinding.countries.setText(country.getName()); //set our bound items to corresponding views
            GlideToVectorYou.justLoadImage((Activity) context, Uri.parse(country.getFlag()), countryItemViewBinding.flags);
            currentItem = country; //set current item to the item clicked which is passed down from or onBindViewHolder
        }

        @Override
        public void onClick(View v) {
            //implement our interface onclick method and pass down the view, position and current item clicked
            //which we will have access to in our mainActivity
            recyclerViewClickListener.onRecyclerViewClick(v, getAdapterPosition(),currentItem);
        }
    }

    //public interface to detect clicks on items and pass position
    public interface recyclerViewClickListener {
        void onRecyclerViewClick(View v, int position,countryModel currentItem);
    }

}



