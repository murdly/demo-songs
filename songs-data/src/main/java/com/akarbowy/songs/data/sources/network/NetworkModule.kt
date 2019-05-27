package com.akarbowy.songs.data.sources.network

import com.akarbowy.songs.data.sources.RemoteDataSource
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
internal object NetworkModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @JvmStatic
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @JvmStatic
    @Provides
    @Singleton
    fun provideConverterFactory(moshi: Moshi): Converter.Factory = MoshiConverterFactory.create(moshi)

    @JvmStatic
    @Provides
    @Singleton
    fun provideApi(
        builder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder,
        converterFactory: Converter.Factory
    ): ITunesApi {
        val client = okHttpClientBuilder.build()
        return builder.client(client)
            .baseUrl(ITunesApi.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .build()
            .create(ITunesApi::class.java)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideITunesService(iTunesApi: ITunesApi) =
        ITunesService(iTunesApi)

    @Provides
    @JvmStatic
    @Singleton
    fun provideRemoteDataSource(iTunesService: ITunesService): RemoteDataSource = iTunesService

}