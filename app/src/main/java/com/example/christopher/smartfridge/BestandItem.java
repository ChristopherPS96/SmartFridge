package com.example.christopher.smartfridge;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Calendar;

@DatabaseTable (tableName = "bestandItem")
public class BestandItem {
    @DatabaseField (generatedId = true)
    private int id;
    @DatabaseField (foreign = true, foreignAutoCreate = true)
    private ScanItem scanItem;
    @DatabaseField (dataType = DataType.SERIALIZABLE)
    private Calendar ablaufDatum;

    public BestandItem() {    }

    public BestandItem(ScanItem scanItem, Calendar ablaufDatum) {
        this.scanItem = scanItem;
        this.ablaufDatum = ablaufDatum;
    }

    public Calendar getAblaufDatum() {
        return ablaufDatum;
    }

    public void setAblaufDatum(Calendar ablaufDatum) {
        this.ablaufDatum = ablaufDatum;
    }

    public ScanItem getScanItem() {
        return scanItem;
    }

    public void setScanItem(ScanItem scanItem) {
        this.scanItem = scanItem;
    }

    @Override
    public boolean equals(Object obj) {     //damit die Objekt ordentlich verglichen werden können (z.B. für Remove)
        return (this.scanItem.getBarcode().equals(((BestandItem) obj).scanItem.getBarcode()) &&
                this.scanItem.getName().equals(((BestandItem) obj).scanItem.getName()) &&
                (this.ablaufDatum.getTime().equals(((BestandItem) obj).ablaufDatum.getTime())));
    }
}
