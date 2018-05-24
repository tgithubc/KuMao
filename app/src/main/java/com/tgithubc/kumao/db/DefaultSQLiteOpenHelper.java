package com.tgithubc.kumao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greendao.autogen.DaoMaster;
import org.greendao.autogen.KeyWordDao;

public class DefaultSQLiteOpenHelper extends DaoMaster.OpenHelper {

    public DefaultSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DefaultMigrationHelper.migrate(db, KeyWordDao.class);
    }
}