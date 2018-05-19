package com.example.christopher.smartfridge;

public enum CoustomPagerEnum {

    HAUPTSEITE("Hauptseite", R.layout.activity_maincontent),
    SCANSEITE("Scanseite", R.layout.activity_scan_item);

    private String title;
    private int layoutID;

    CoustomPagerEnum(String title, int layoutID) {
        this.title = title;
        this.layoutID = layoutID;
    }

    public int getLayoutID() {
        return layoutID;
    }

    public String getTitle() {
        return title;
    }
}
