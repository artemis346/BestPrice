package com.bestsales.network.di


import com.bestsales.network.api.search.SearchApi
import com.bestsales.network.BuildConfig.DEBUG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        const val baseUrl = "https://bdk0sta2n0.execute-api.eu-west-1.amazonaws.com/"
        const val HTTP_TIMEOUT = 30000
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(if (DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(HTTP_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit) : SearchApi {
        return retrofit.create(SearchApi::class.java)
    }
}