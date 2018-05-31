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

import com.example.christopher.smartfridge.Fragments.ScanFragment;

import java.util.ArrayList;
import java.util.List;

public class ScanItemAdapter extends ArrayAdapter<ScanItem> implements Filterable {

    private Context context;

    public ScanItemAdapter(Context context, int resource, List<ScanItem> items) {
        super(context, resource, items);
        this.context = context;

    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {     //spezieller Adapter, damit ein Objekt dargestellt werden kann
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.scan_item_list, parent, false);
        }
        ScanItem p = getItem(position);
        if (p != null) {
            TextView tt1 = v.findViewById(R.id.name);
            TextView tt2 = v.findViewById(R.id.barcode);
                tt1.setText(p.getName());
                tt2.setText(context.getResources().getString(R.string.editScanBarcode, p.getBarcode()));
        }
        return v;
    }

    @Override
    @NonNull
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                OrmDataHelper ormDataHelper = new OrmDataHelper(context);
                ArrayList<ScanItem> filterResults = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                if(constraint.length() > 0) {
                    for(ScanItem e : ormDataHelper.getAllScanItem()) {
                        if(e.getName().toLowerCase().startsWith(constraint.toString())) {
                            filterResults.add(e);
                        }
                    }
                    results.count = filterResults.size();
                    results.values = filterResults;
                } else {
                    results.count = ormDataHelper.getAllScanItem().size();
                    results.values = ormDataHelper.getAllScanItem();
                }
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ScanFragment.scanItemAdapter.clear();
                ScanFragment.scanItemAdapter.addAll((List<ScanItem>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
