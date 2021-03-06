/*
 ** Erstellt von Christopher Schwandt, Anna Rochow, Jennifer Tönjes und Alina Pohl der SMIB
 */
//Quelle: Folie aus Mobile Systeme

package com.example.christopher.smartfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

    public static final String LOG = OrmDbHelper.class.getName();
    public static final String DB_NAME = "bestand.db";
    public static final int DB_VERSION = 1;

    public OrmDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, ScanItem.class);
            TableUtils.createTableIfNotExists(connectionSource, BestandItem.class);
            TableUtils.createTableIfNotExists(connectionSource, SettingsItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Creating table", ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ScanItem.class, true);
            TableUtils.dropTable(connectionSource, BestandItem.class, true);
            TableUtils.dropTable(connectionSource, SettingsItem.class, true);
            TableUtils.createTable(connectionSource, ScanItem.class);
            TableUtils.createTable(connectionSource, BestandItem.class);
            TableUtils.createTable(connectionSource, SettingsItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Updating table", ex);
        }
    }

    public Dao<ScanItem, Integer> createScanItemDAO() {
        try {
            return DaoManager.createDao(connectionSource, ScanItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Creating DAO for Todo class", ex);
        }
        return null;
    }

    public Dao<BestandItem, Integer> createBestandItemDAO() {
        try {
            return DaoManager.createDao(connectionSource, BestandItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Creating DAO for Todo class", ex);
        }
        return null;
    }

    public Dao<SettingsItem, Integer> createSettingItemDAO() {
        try {
            return DaoManager.createDao(connectionSource, SettingsItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Creating DAO for Todo class", ex);
        }
        return null;
    }
}
