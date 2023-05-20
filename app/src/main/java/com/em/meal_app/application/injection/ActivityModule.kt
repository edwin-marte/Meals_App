package com.em.meal_app.application.injection

import com.em.meal_app.data.remote.IDataSource
import com.em.meal_app.data.remote.DataSource
import com.em.meal_app.domain.IRepository
import com.em.meal_app.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {
    @Binds
    abstract fun bindIRepository(iRepository: IRepository): Repository

    @Binds
    abstract fun bindIDatasource(iDataSource: IDataSource): DataSource
}