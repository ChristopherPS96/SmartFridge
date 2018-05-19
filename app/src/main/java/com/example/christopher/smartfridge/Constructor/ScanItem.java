package com.example.christopher.smartfridge.Constructor;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "scanItem")
public class ScanItem{
    @DatabaseField(id = true)
    private String barcode;
    @DatabaseField
    private String name;

    public ScanItem(String barcode, String name) {
        this.barcode = barcode;
        this.name = name;
    }

    public ScanItem(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
