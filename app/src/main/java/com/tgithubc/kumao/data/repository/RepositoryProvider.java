package com.tgithubc.kumao.data.repository;

import com.tgithubc.kumao.data.repository.local.KuMaoLocalDataSource;
import com.tgithubc.kumao.data.repository.remote.KuMaoRemoteDataSource;

/**
 * Created by tc :)
 */
public class RepositoryProvider {

    public static KuMaoRepository getTasksRepository() {
        return KuMaoRepository.getInstance(KuMaoRemoteDataSource.getInstance(),
                KuMaoLocalDataSource.getInstance());
    }
}