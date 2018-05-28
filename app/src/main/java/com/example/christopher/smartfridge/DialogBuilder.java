package com.example.christopher.smartfridge;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.christopher.smartfridge.Fragments.MainFragment;
import com.example.christopher.smartfridge.Fragments.ScanFragment;

import java.util.Calendar;

public class DialogBuilder extends AppCompatActivity {

    private OrmDataHelper ormDataHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public DialogBuilder(Context context) {
        this.context = context;
        ormDataHelper = new OrmDataHelper(context);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    
    public void listScanItem() {
        AlertDialog.Builder listScanItem = new AlertDialog.Builder(context);
        listScanItem.setTitle("Wähle ein ScanItem:");
        listScanItem.setIcon(R.mipmap.fridge_icon);
        final ScanItemAdapter scanItemAdapter = new ScanItemAdapter(context, R.layout.scan_item_list, ormDataHelper.getAllScanItem());
        listScanItem.setAdapter(scanItemAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNewBestandItem(scanItemAdapter.getItem(which));
            }
        });
        listScanItem.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        listScanItem.show();
    }

    public void createNewScanItem(final String barcode) {
        AlertDialog.Builder newScanItem = new AlertDialog.Builder(context);
        newScanItem.setTitle("Gebe dem Produkt einen Namen:");
        newScanItem.setIcon(R.mipmap.fridge_icon);
        final EditText editText = new EditText(context);
        newScanItem.setView(editText);
        newScanItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText.getText().toString().length() > 2) {
                    ormDataHelper.saveScanItem(new ScanItem(barcode, editText.getText().toString()));
                    ScanFragment.scanItemAdapter.add(new ScanItem(barcode, editText.getText().toString()));
                } else {
                    AlertDialog.Builder revert = new AlertDialog.Builder(context);
                    revert.setTitle("Eingabe zu kurz. Wiederholen?");
                    revert.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createNewScanItem(barcode);
                        }
                    });
                    revert.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    revert.show();
                }
            }
        });
        newScanItem.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        newScanItem.show();
    }

    public void editScanItem(final ScanItem scanItem) {
        final AlertDialog.Builder editScanItem = new AlertDialog.Builder(context);
        editScanItem.setTitle("Bearbeiten oder Löschen ihrer Auswahl:");
        editScanItem.setIcon(R.mipmap.fridge_icon);
        final EditText editText = new EditText(context);
        TextView textView = new TextView(context);
        TextView name = new TextView(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(name);
        linearLayout.addView(editText);
        linearLayout.addView(textView);
        name.setText(scanItem.getName());
        editText.setHint("Name hier eingeben...");
        textView.setText("Barcode: " + scanItem.getBarcode());
        editScanItem.setView(linearLayout);
        editScanItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText.getText().toString().length() > 2) {
                    ormDataHelper.deleteScanItem(scanItem);
                    ScanFragment.scanItemAdapter.remove(scanItem);
                    ormDataHelper.saveScanItem(new ScanItem(scanItem.getBarcode(), editText.getText().toString()));
                    ScanFragment.scanItemAdapter.add(new ScanItem(scanItem.getBarcode(), editText.getText().toString()));
                } else {
                    AlertDialog.Builder revert = new AlertDialog.Builder(context);
                    revert.setTitle("Name zu kurz! Wiederholen?");
                    revert.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editScanItem(scanItem);
                        }
                    });
                    revert.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    });
                    revert.show();
                }
            }
        });
        editScanItem.setNeutralButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ormDataHelper.deleteScanItem(scanItem);
                ScanFragment.scanItemAdapter.remove(scanItem);
            }
        });
        editScanItem.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        editScanItem.show();
    }

    public void createNewBestandItem(final ScanItem scanItem) {
        AlertDialog.Builder createBestandItem = new AlertDialog.Builder(context);
        createBestandItem.setTitle("Setze ein Ablaufdatum:");
        createBestandItem.setIcon(R.mipmap.fridge_icon);
        final DatePicker datePicker = new DatePicker(context);
        createBestandItem.setView(datePicker);
        createBestandItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                date.set(Calendar.MONTH, datePicker.getMonth());
                date.set(Calendar.YEAR, datePicker.getYear());
                ormDataHelper.saveBestandItem(new BestandItem(scanItem, date));
                MainFragment.bestandItemAdapter.add(new BestandItem(scanItem,date));
            }
        });
        createBestandItem.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        createBestandItem.show();
    }

    public void editBestandItem(final BestandItem bestandItem) {
        AlertDialog.Builder editBestandItem = new AlertDialog.Builder(context);
        editBestandItem.setIcon(R.mipmap.fridge_icon);
        editBestandItem.setTitle("Bearbeite ihre Auswahl:");
        final DatePicker datePicker = new DatePicker(context);
        editBestandItem.setView(datePicker);
        datePicker.init(bestandItem.getAblaufDatum().get(Calendar.YEAR), bestandItem.getAblaufDatum().get(Calendar.MONTH), bestandItem.getAblaufDatum().get(Calendar.DAY_OF_MONTH), null);
        editBestandItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                date.set(Calendar.MONTH, datePicker.getMonth());
                date.set(Calendar.YEAR, datePicker.getYear());
                ormDataHelper.deleteBestandItem(bestandItem);
                MainFragment.bestandItemAdapter.remove(bestandItem);
                ormDataHelper.saveBestandItem(new BestandItem(bestandItem.getScanItem(), date));
                MainFragment.bestandItemAdapter.add(new BestandItem(bestandItem.getScanItem(), date));
            }
        });
        editBestandItem.setNeutralButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ormDataHelper.deleteBestandItem(bestandItem);
                MainFragment.bestandItemAdapter.remove(bestandItem);
            }
        });
        editBestandItem.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        editBestandItem.show();
    }

    public void createNotification(BestandItem bestandItem) {
        NotificationPublisher notificationPublisher = new NotificationPublisher();
        notificationPublisher.scheduleNotification(bestandItem, getApplicationContext());
    }

    public void deleteNotification(BestandItem bestandItem) {
        NotificationPublisher notificationPublisher = new NotificationPublisher();
        notificationPublisher.deleteNotification(bestandItem, getApplicationContext());
    }
}
