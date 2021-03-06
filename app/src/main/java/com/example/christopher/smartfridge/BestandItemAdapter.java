/*
** Erstellt von Christopher Schwandt, Anna Rochow, Jennifer Tönjes und Alina Pohl der SMIB
 */
// Quelle: https://stackoverflow.com/questions/8166497/custom-adapter-for-list-view
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

import com.example.christopher.smartfridge.Fragments.MainFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BestandItemAdapter extends ArrayAdapter<BestandItem> implements Filterable {

    private final Context context;

    public BestandItemAdapter(Context context, int resource, List<BestandItem> items) {
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
            v = vi.inflate(R.layout.bestand_item_list, parent, false);
        }
        BestandItem p = getItem(position);
        if (p != null) {
            //setzt und beschreibt die einzelnen Felder der Liste
            TextView tt1 = v.findViewById(R.id.name);
            TextView tt2 = v.findViewById(R.id.ablaufdatum);
            TextView tt3 = v.findViewById(R.id.barcode);
            TextView tt4 = v.findViewById(R.id.amount);
                tt1.setText(p.getScanItem().getName());
                tt2.setText(context.getResources().getString(R.string.ablaufDatumBestandItem, p.getAblaufDatum().get(Calendar.DAY_OF_MONTH), p.getAblaufDatum().get(Calendar.MONTH)+1, p.getAblaufDatum().get(Calendar.YEAR)));
                tt3.setText(context.getResources().getString(R.string.editScanBarcode, p.getScanItem().getBarcode()));
                tt4.setText(context.getResources().getString(R.string.amountBestandItem, p.getAmount()));
        }
        return v;
    }

    @Override
    @NonNull
//erstellen des Filters und seiner Funktionen und Rückgabe
    public Filter getFilter() {

        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                OrmDataHelper ormDataHelper = new OrmDataHelper(context);
                ArrayList<BestandItem> filterResults = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                if(constraint.length() > 0) {
                    for(BestandItem e : ormDataHelper.getAllBestandItem()) {
                        if(e.getScanItem().getName().toLowerCase().startsWith(constraint.toString())) {
                            filterResults.add(e);
                        }
                    }
                    results.count = filterResults.size();
                    results.values = filterResults;
                } else {
                    results.count = ormDataHelper.getAllBestandItem().size();
                    results.values = ormDataHelper.getAllBestandItem();
                }
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                MainFragment.bestandItemAdapter.clear();
                MainFragment.bestandItemAdapter.addAll((List<BestandItem>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
