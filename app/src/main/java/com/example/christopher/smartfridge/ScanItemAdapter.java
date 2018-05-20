//Christopher Schwandt SMIB + Quelle: https://stackoverflow.com/questions/8166497/custom-adapter-for-list-view

package com.example.christopher.smartfridge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScanItemAdapter extends ArrayAdapter<ScanItem> implements Filterable {

    private List<ScanItem> items;
    private Context context;

    public ScanItemAdapter(Context context, int resource, List<ScanItem> items) {
        super(context, resource, items);
        this.context = context;
        this.items = items;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {     //spezieller Adapter, damit ein Objekt dargestellt werden kann
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.scan_item_list, null);
        }
        ScanItem p = getItem(position);
        if (p != null) {
            TextView tt1 = v.findViewById(R.id.name);
            TextView tt2 = v.findViewById(R.id.barcode);
                tt1.setText(p.getName());
                tt2.setText("Barcode: " + p.getBarcode());
        }
        return v;
    }

    @Override
    @NonNull
    public Filter getFilter() {

        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<ScanItem> scanItems = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                OrmDataHelper ormDataHelper = new OrmDataHelper(context);
                for(ScanItem e : ormDataHelper.getAllScanItem()) {
                    if(e.getName().toLowerCase().startsWith(constraint.toString())) {
                        scanItems.add(e);
                    }
                }
                results.count = scanItems.size();
                results.values = scanItems;
                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<ScanItem>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
