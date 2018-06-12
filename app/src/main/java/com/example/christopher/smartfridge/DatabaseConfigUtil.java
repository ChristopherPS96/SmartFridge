/*
 ** Erstellt von Christopher Schwandt, Anna Rochow, Jennifer Tönjes und Alina Pohl der SMIB
 */

package com.example.christopher.smartfridge;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

//beschreibt die config.txt mit notwendigen Informationen von den angegebenen Klassen für die Datenbank
class DatabaseConfigUtil extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            BestandItem.class, ScanItem.class, SettingsItem.class
    };
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
