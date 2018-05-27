package org.greendao.autogen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.tgithubc.kumao.bean.KeyWord;

import org.greendao.autogen.KeyWordDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig keyWordDaoConfig;

    private final KeyWordDao keyWordDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        keyWordDaoConfig = daoConfigMap.get(KeyWordDao.class).clone();
        keyWordDaoConfig.initIdentityScope(type);

        keyWordDao = new KeyWordDao(keyWordDaoConfig, this);

        registerDao(KeyWord.class, keyWordDao);
    }
    
    public void clear() {
        keyWordDaoConfig.clearIdentityScope();
    }

    public KeyWordDao getKeyWordDao() {
        return keyWordDao;
    }

}