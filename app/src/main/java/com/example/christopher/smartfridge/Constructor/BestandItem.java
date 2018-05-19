package com.example.christopher.smartfridge.Constructor;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable (tableName = "bestandItem")
public class BestandItem {
    @DatabaseField (generatedId = true)
    private int id;
    @DatabaseField
    private ScanItem scanItem;
    @DatabaseField
    private Date ablaufDatum;

    public BestandItem() {

    }

    public BestandItem(ScanItem scanItem, Date ablaufDatum) {
        this.scanItem = scanItem;
        this.ablaufDatum = ablaufDatum;
    }

    public Date getAblaufDatum() {
        return ablaufDatum;
    }

    public void setAblaufDatum(Date ablaufDatum) {
        this.ablaufDatum = ablaufDatum;
    }

    public ScanItem getScanItem() {
        return scanItem;
    }

    public void setScanItem(ScanItem scanItem) {
        this.scanItem = scanItem;
    }
}
