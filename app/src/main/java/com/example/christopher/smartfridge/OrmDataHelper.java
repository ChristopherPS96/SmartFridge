package com.example.christopher.smartfridge;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrmDataHelper {

    private Dao<ScanItem, Integer> scanItemDAO;
    private Dao<BestandItem, Integer> bestandItemDAO;

    public OrmDataHelper(Context context) {
        OrmDbHelper ormDbHelper = new OrmDbHelper(context);
         scanItemDAO = ormDbHelper.createScanItemDAO();
         bestandItemDAO = ormDbHelper.createBestandItemDAO();
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
}
