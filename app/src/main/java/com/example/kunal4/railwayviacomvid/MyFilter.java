package com.example.kunal4.railwayviacomvid;


import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal4 on 12/26/16.
 */

public class MyFilter extends Filter {

    private List<ListItem> videos;
    private List<ListItem> filteredvideos;
    private CardAdapter adapter;

    public MyFilter(List<ListItem> videos, CardAdapter adapter) {
        this.adapter = adapter;
        this.videos = videos;
        this.filteredvideos = new ArrayList<ListItem>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredvideos.clear();
        final FilterResults results = new FilterResults();

        //here you need to add proper items do filteredContactList
        for (final ListItem item : videos) {
            if (item.getName().toLowerCase().trim().contains(constraint)) {
                filteredvideos.add(item);
            }
        }

        results.values = filteredvideos;
        results.count = filteredvideos.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.setList(filteredvideos);
        adapter.notifyDataSetChanged();
    }
}
