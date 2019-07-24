package com.jaycodes.rebtel_assignment.utils;



import android.widget.Filter;
import com.jaycodes.rebtel_assignment.adapters.CountryAdapter;
import com.jaycodes.rebtel_assignment.repository.models.countryModel;
import java.util.ArrayList;


public class countryListFilter extends Filter {
    private ArrayList<countryModel> countryList;
    private ArrayList<countryModel> countryListFull;
    private CountryAdapter adapter;

    public countryListFilter(CountryAdapter adapter, ArrayList<countryModel> countryList) {
        super();
        this.adapter = adapter;
        this.countryList = countryList;
        this.countryListFull = new ArrayList<>(countryList);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        String charString = constraint.toString();
        if (charString.isEmpty()) {
            countryListFull = countryList;
        } else {
            ArrayList<countryModel> filteredList = new ArrayList<>();
            for (countryModel model : countryList) {
                if (model.getName().toLowerCase().startsWith(charString.toLowerCase().trim())) {
                   // filteredList.clear();
                    filteredList.add(model);
                }
            }
            countryListFull = filteredList;
        }
        FilterResults results = new FilterResults();
        results.values = countryListFull;
        results.count = countryListFull.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.countryListFull = (ArrayList<countryModel>) results.values;
        adapter.notifyDataSetChanged();

    }
}
