package com.yunuscagliyan.soccerapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yunuscagliyan.soccerapp.constants.AppConstant
import com.yunuscagliyan.soccerapp.data.api.SportDataApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder() = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRequestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", AppConstant.API_KEY)
                .build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(requestInterceptor: Interceptor) = OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(AppConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))

    @Singleton
    @Provides
    fun provideSportDataApi(retrofit: Retrofit.Builder) = retrofit.build()
        .create(SportDataApi::class.java)

}