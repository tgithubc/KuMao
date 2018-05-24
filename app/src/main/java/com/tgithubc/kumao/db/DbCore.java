package com.tgithubc.kumao.db;


import android.annotation.SuppressLint;
import android.content.Context;

import org.greendao.autogen.DaoMaster;
import org.greendao.autogen.DaoSession;

/**
 * Created by tc :)
 */
public class DbCore {

    private static final String DEFAULT_DB_NAME = "kumao.db";

    private Context mContext;
    private DaoSession mDaoSession;
    private DaoMaster mDaoMaster;
    private String mDataBaseName;

    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        private static final DbCore INSTANCE = new DbCore();
    }

    public static DbCore getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private DbCore() {
    }

    public void init(Context context) {
        init(context, DEFAULT_DB_NAME);
    }

    public void init(Context context, String dbName) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null");
        }
        mDataBaseName = dbName;
        mContext = context.getApplicationContext();
    }

    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    private DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            DaoMaster.OpenHelper helper = new DefaultSQLiteOpenHelper(mContext, mDataBaseName, null);
            mDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return mDaoMaster;
    }
}
