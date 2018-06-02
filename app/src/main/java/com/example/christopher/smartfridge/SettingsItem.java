package com.example.christopher.smartfridge;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "settingItem")
public class SettingsItem {
    @SuppressWarnings("unused")
    @DatabaseField (generatedId = true)
   private int id;
    @DatabaseField
    private boolean autofocus;
    @DatabaseField
    private boolean lightning;
    @DatabaseField
    private boolean notifications;

    public SettingsItem(){

    }

    public SettingsItem(int textSize, boolean autofocus,  boolean lightning, boolean notifications){
        this.autofocus = autofocus;
        this.lightning = lightning;
        this.notifications = notifications;
    }

    public boolean isAutofocus() {
        return autofocus;
    }

    public void setAutofocus(boolean autofocus) {
        this.autofocus = autofocus;
    }

    public boolean isLightning() {
        return lightning;
    }

    public void setLightning(boolean lightning) {
        this.lightning = lightning;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
