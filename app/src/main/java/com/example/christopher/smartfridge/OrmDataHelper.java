/*
 ** Erstellt von Christopher Schwandt, Anna Rochow, Jennifer Tönjes und Alina Pohl der SMIB
 */

package com.example.christopher.smartfridge;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

//gesamte Klasse ist eine Helferklasse mit Ansammlungen verschiedenener Methoden zur Interaktion wie Speichern, Löschen oder Sammeln mit der Datenbank.
public class OrmDataHelper {

    private final Dao<ScanItem, Integer> scanItemDAO;
    private final Dao<BestandItem, Integer> bestandItemDAO;
    private final Dao<SettingsItem, Integer> settingsItemsDAO;

    public OrmDataHelper(Context context) {
        OrmDbHelper ormDbHelper = new OrmDbHelper(context);
        scanItemDAO = ormDbHelper.createScanItemDAO();
        bestandItemDAO = ormDbHelper.createBestandItemDAO();
        settingsItemsDAO = ormDbHelper.createSettingItemDAO();
    }

    public void saveScanItem(ScanItem scanItem) {
        try {
            scanItemDAO.create(scanItem);
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void deleteScanItem(ScanItem scanItem) {
        try {
            scanItemDAO.delete(scanItem);
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public ArrayList<ScanItem> getAllScanItem() {
        ArrayList<ScanItem> temp = null;
        try {
            temp = new ArrayList<>(scanItemDAO.queryForAll());
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return temp;
    }

    public void saveBestandItem(BestandItem bestandItem) {
        try {
            bestandItemDAO.create(bestandItem);
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void deleteBestandItem(BestandItem bestandItem) {
        try {
            bestandItemDAO.delete(bestandItem);
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public ArrayList<BestandItem> getAllBestandItem() {
        ArrayList<BestandItem> temp = null;
        try {
            temp = new ArrayList<>(bestandItemDAO.queryForAll());
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return temp;
    }

    public void saveSettingItem(SettingsItem settingsItem) {
        try {
            settingsItemsDAO.create(settingsItem);
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void deleteSettingItem(SettingsItem settingsItem) {
        try {
            settingsItemsDAO.delete(settingsItem);
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public ArrayList<SettingsItem> getSettingItem() {
        ArrayList<SettingsItem> temp = null;
        try {
            temp = new ArrayList<>(settingsItemsDAO.queryForAll());
        } catch (SQLException ex) {
            ex.getMessage();
        }
       return temp;
    }
}
