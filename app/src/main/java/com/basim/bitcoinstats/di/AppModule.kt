package com.basim.bitcoinstats.di

import android.content.Context
import com.basim.bitcoinstats.data.local.AppDatabase
import com.basim.bitcoinstats.data.local.ChartDao
import com.basim.bitcoinstats.data.local.LocalDataSource
import com.basim.bitcoinstats.data.remote.RemoteDataSource
import com.basim.bitcoinstats.data.remote.Service
import com.basim.bitcoinstats.utils.Constants
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Module class used for Hilt dependency injection configuration
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .client(OkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    fun provideService(retrofit: Retrofit): Service =
        retrofit.create(Service::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        service: Service
    ) = RemoteDataSource(service)

    @Singleton
    @Provides
    fun provideChartDao(db: AppDatabase) = db.chartDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(
        chartDao: ChartDao
    ) = LocalDataSource(chartDao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)


}