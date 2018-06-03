package com.example.christopher.smartfridge;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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

    @SuppressWarnings("unused")
    public DialogBuilder() {   }

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
        textView.setText(context.getResources().getString(R.string.editScanBarcode, scanItem.getBarcode()));
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

    private BestandItem temp;
    public void createNewBestandItem(final ScanItem scanItem) {
        AlertDialog.Builder createBestandItem = new AlertDialog.Builder(context);
        createBestandItem.setTitle("Setze notwendige Details:");
        createBestandItem.setIcon(R.mipmap.fridge_icon);
        final LinearLayout linearLayout = new LinearLayout(context);
        DatePicker datePicker = new DatePicker(context);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        TextView textView = new TextView(context);
        textView.setText(R.string.createNewBestand);
        NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(1000);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(datePicker);
        linearLayout.addView(textView);
        linearLayout.addView(numberPicker);
        createBestandItem.setView(linearLayout);
        createBestandItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.DAY_OF_MONTH, ((DatePicker) linearLayout.getChildAt(0)).getDayOfMonth());
                date.set(Calendar.MONTH, ((DatePicker) linearLayout.getChildAt(0)).getMonth());
                date.set(Calendar.YEAR, ((DatePicker) linearLayout.getChildAt(0)).getYear());
                boolean exist = false;
                for(BestandItem e : ormDataHelper.getAllBestandItem()) {
                    if(scanItem.getBarcode().equals(e.getScanItem().getBarcode()) && date.get(Calendar.DAY_OF_MONTH) == e.getAblaufDatum().get(Calendar.DAY_OF_MONTH) && date.get(Calendar.MONTH) == e.getAblaufDatum().get(Calendar.MONTH) && date.get(Calendar.YEAR) == e.getAblaufDatum().get(Calendar.YEAR)) {
                        temp = e;
                        AlertDialog.Builder dialogExist = new AlertDialog.Builder(context);
                        dialogExist.setIcon(R.mipmap.fridge_icon_round);
                        dialogExist.setTitle("Gegenstand existiert bereits!");
                        final LinearLayout linearLayout1 = new LinearLayout(context);
                        linearLayout1.setOrientation(LinearLayout.VERTICAL);
                        TextView textView = new TextView(context);
                        textView.setText(R.string.createNewBestandExist);
                        NumberPicker numberPicker1 = new NumberPicker(context);
                        numberPicker1.setMinValue(1);
                        numberPicker1.setMaxValue(1000);
                        linearLayout1.addView(textView);
                        linearLayout1.addView(numberPicker1);
                        dialogExist.setView(linearLayout1);
                        dialogExist.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ormDataHelper.deleteBestandItem(temp);
                                MainFragment.bestandItemAdapter.remove(temp);
                                temp.setAmount(temp.getAmount() + ((NumberPicker) linearLayout1.getChildAt(1)).getValue());
                                ormDataHelper.saveBestandItem(temp);
                                MainFragment.bestandItemAdapter.add(temp);
                                createNotification(temp);
                            }
                        });
                        dialogExist.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialogExist.show();
                        exist = true;
                    }
                }
                if(!exist) {
                    BestandItem temp = new BestandItem(scanItem, date, ((NumberPicker) linearLayout.getChildAt(2)).getValue());
                    ormDataHelper.saveBestandItem(temp);
                    MainFragment.bestandItemAdapter.add(temp);
                    createNotification(temp);
                }
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
        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        DatePicker datePicker = new DatePicker(context);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        TextView textView = new TextView(context);
        textView.setText(R.string.editBestandItem);
        NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(bestandItem.getAmount() + 1000);
        numberPicker.setValue(bestandItem.getAmount());
        linearLayout.addView(datePicker);
        linearLayout.addView(textView);
        linearLayout.addView(numberPicker);
        editBestandItem.setView(linearLayout);
        datePicker.init(bestandItem.getAblaufDatum().get(Calendar.YEAR), bestandItem.getAblaufDatum().get(Calendar.MONTH), bestandItem.getAblaufDatum().get(Calendar.DAY_OF_MONTH), null);
        editBestandItem.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.DAY_OF_MONTH, ((DatePicker) linearLayout.getChildAt(0)).getDayOfMonth());
                date.set(Calendar.MONTH, ((DatePicker) linearLayout.getChildAt(0)).getMonth());
                date.set(Calendar.YEAR, ((DatePicker) linearLayout.getChildAt(0)).getYear());
                ormDataHelper.deleteBestandItem(bestandItem);
                MainFragment.bestandItemAdapter.remove(bestandItem);
                deleteNotification(bestandItem);
                BestandItem temp = new BestandItem(bestandItem.getScanItem(), date, ((NumberPicker) linearLayout.getChildAt(2)).getValue());
                ormDataHelper.saveBestandItem(temp);
                MainFragment.bestandItemAdapter.add(temp);
                createNotification(temp);
            }
        });
        editBestandItem.setNeutralButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ormDataHelper.deleteBestandItem(bestandItem);
                MainFragment.bestandItemAdapter.remove(bestandItem);
                deleteNotification(bestandItem);
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
        notificationPublisher.scheduleNotification(bestandItem, context);
    }

    public void deleteNotification(BestandItem bestandItem) {
        NotificationPublisher notificationPublisher = new NotificationPublisher();
        notificationPublisher.deleteNotification(bestandItem, context);
    }
}
