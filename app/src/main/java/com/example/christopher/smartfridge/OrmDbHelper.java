//Christopher Schwandt SMIB + Folie aus Mobile Systeme

package com.example.christopher.smartfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

    public static final String LOG = OrmDbHelper.class.getName();
    public static final String DB_NAME = "bestand.db";
    public static final int DB_VERSION = 1;

    public OrmDbHelper(Context context) {
        super(context, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
                + File.separator + DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, ScanItem.class);
            TableUtils.createTableIfNotExists(connectionSource, BestandItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Creating table", ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ScanItem.class, true);
            TableUtils.dropTable(connectionSource, BestandItem.class, true);
            TableUtils.createTable(connectionSource, ScanItem.class);
            TableUtils.createTable(connectionSource, BestandItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Updating table", ex);
        }
    }

    public com.j256.ormlite.dao.Dao<ScanItem, Integer> createScanItemDAO() {
        try {
            return DaoManager.createDao(connectionSource, ScanItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Creating DAO for Todo class", ex);
        }
        return null;
    }

    public com.j256.ormlite.dao.Dao<BestandItem, Integer> createBestandItemDAO() {
        try {
            return DaoManager.createDao(connectionSource, BestandItem.class);
        } catch (SQLException ex) {
            Log.e(LOG, "Error Creating DAO for Todo class", ex);
        }
        return null;
    }
}
