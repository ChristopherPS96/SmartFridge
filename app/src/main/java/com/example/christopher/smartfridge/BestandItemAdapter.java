//Christopher Schwandt SMIB + Quelle: https://stackoverflow.com/questions/8166497/custom-adapter-for-list-view
// 2. Quelle: https://stackoverflow.com/questions/14663725/list-view-filter-android

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

import com.example.christopher.bestands_app.R;

import java.util.ArrayList;
import java.util.List;

public class BestandItemAdapter extends ArrayAdapter<BestandItem> implements Filterable {

    private List<BestandItem> items;
    private Context context;

    public BestandItemAdapter(Context context, int resource, List<BestandItem> items) {
        super(context, resource, items);
        this.items = items;
        this.context = context;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {     //spezieller Adapter, damit ein Objekt dargestellt werden kann
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.bestand_item_list, null);
        }
        BestandItem p = getItem(position);
        if (p != null) {
            TextView tt1 = v.findViewById(R.id.name);
            TextView tt2 = v.findViewById(R.id.ablaufdatum);
            TextView tt3 = v.findViewById(R.id.barcode);
                tt1.setText(p.getScanItem().getName());
                tt2.setText("Ablaufdatum: " + p.getAblaufDatum().toString());
                tt3.setText("Barcode: " + p.getScanItem().getBarcode());
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
                ArrayList<BestandItem> bestandItems = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                OrmDataHelper ormDataHelper = new OrmDataHelper(context);
                for(BestandItem e : ormDataHelper.getAllBestandItem()) {
                    if(e.getScanItem().getName().toLowerCase().startsWith(constraint.toString())) {
                        bestandItems.add(e);
                    }
                }
                results.count = bestandItems.size();
                results.values = bestandItems;
               return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<BestandItem>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
