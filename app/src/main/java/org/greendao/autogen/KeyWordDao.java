package org.greendao.autogen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tgithubc.kumao.bean.KeyWord;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "KEY_WORD".
*/
public class KeyWordDao extends AbstractDao<KeyWord, Long> {

    public static final String TABLENAME = "KEY_WORD";

    /**
     * Properties of entity KeyWord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property ID = new Property(0, Long.class, "ID", true, "_id");
        public final static Property KeyWord = new Property(1, String.class, "keyWord", false, "KEY_WORD");
        public final static Property SearchTime = new Property(2, long.class, "searchTime", false, "SEARCH_TIME");
    }


    public KeyWordDao(DaoConfig config) {
        super(config);
    }
    
    public KeyWordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"KEY_WORD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: ID
                "\"KEY_WORD\" TEXT," + // 1: keyWord
                "\"SEARCH_TIME\" INTEGER NOT NULL );"); // 2: searchTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"KEY_WORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, KeyWord entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String keyWord = entity.getKeyWord();
        if (keyWord != null) {
            stmt.bindString(2, keyWord);
        }
        stmt.bindLong(3, entity.getSearchTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, KeyWord entity) {
        stmt.clearBindings();
 
        Long ID = entity.getID();
        if (ID != null) {
            stmt.bindLong(1, ID);
        }
 
        String keyWord = entity.getKeyWord();
        if (keyWord != null) {
            stmt.bindString(2, keyWord);
        }
        stmt.bindLong(3, entity.getSearchTime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public KeyWord readEntity(Cursor cursor, int offset) {
        KeyWord entity = new KeyWord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ID
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // keyWord
            cursor.getLong(offset + 2) // searchTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, KeyWord entity, int offset) {
        entity.setID(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setKeyWord(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setSearchTime(cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(KeyWord entity, long rowId) {
        entity.setID(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(KeyWord entity) {
        if(entity != null) {
            return entity.getID();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(KeyWord entity) {
        return entity.getID() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
