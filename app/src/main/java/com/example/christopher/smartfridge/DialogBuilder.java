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

import java.util.Date;

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
        final ScanItemAdapter scanItemAdapter = new ScanItemAdapter(context, R.layout.scan_item_list, ormDataHelper.getAllScanItem());
        listScanItem.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        listScanItem.setAdapter(scanItemAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createNewBestandItem(scanItemAdapter.getItem(which));
            }
        });
        listScanItem.show();
    }

    public void createNewScanItem(final String barcode) {
        AlertDialog.Builder newScanItem = new AlertDialog.Builder(context);
        newScanItem.setTitle("Gebe dem Produkt einen Namen:");
        final EditText editText = new EditText(context);
        newScanItem.setView(editText);
        newScanItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(editText.getText().toString().length() > 2) {
                    ormDataHelper.saveScanItem(new ScanItem(barcode, editText.getText().toString()));
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
                    ormDataHelper.saveScanItem(new ScanItem(scanItem.getBarcode(), editText.getText().toString()));
                    ormDataHelper.deleteScanItem(scanItem);
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
        final DatePicker datePicker = new DatePicker(context);
        createBestandItem.setView(datePicker);
        createBestandItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date date = new Date();
                date.setDate(datePicker.getDayOfMonth());
                date.setMonth(datePicker.getMonth());
                date.setYear(datePicker.getYear());
                ormDataHelper.saveBestandItem(new BestandItem(scanItem, date));
            }
        });
        createBestandItem.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public void editBestandItem(final BestandItem bestandItem) {
        AlertDialog.Builder editBestandItem = new AlertDialog.Builder(context);
        editBestandItem.setTitle("Bearbeite ihre Auswahl:");
        final DatePicker datePicker = new DatePicker(context);
        editBestandItem.setView(datePicker);
        datePicker.init(bestandItem.getAblaufDatum().getYear(), bestandItem.getAblaufDatum().getMonth(), bestandItem.getAblaufDatum().getDate(), null);
        editBestandItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date date = new Date();
                date.setYear(datePicker.getYear());
                date.setMonth(datePicker.getMonth());
                date.setDate(datePicker.getDayOfMonth());
                ormDataHelper.saveBestandItem(new BestandItem(bestandItem.getScanItem(), date));
                ormDataHelper.deleteBestandItem(bestandItem);
            }
        });
        editBestandItem.setNeutralButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ormDataHelper.deleteBestandItem(bestandItem);
            }
        });
        editBestandItem.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }
}
