package codegene.femicodes.noustask.di

import android.content.Context
import codegene.femicodes.noustask.local.dao.UsersDao
import codegene.femicodes.noustask.repository.UsersRemoteDataSource
import codegene.femicodes.noustask.repository.UsersRepository
import codegene.femicodes.noustask.local.db.AppDatabase
import codegene.femicodes.noustask.remote.AppService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://cloud.nousdigital.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }



    @Singleton
    @Provides
    fun provideDb(context: Context) = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideAppService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }


    @Provides
    @Singleton
    fun provideUsersDao(appDatabase: AppDatabase): UsersDao {
        return appDatabase.userDao()
    }


    @Singleton
    @Provides
    fun provideUsersRemoteDataSource(service: AppService): UsersRemoteDataSource {
        return UsersRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideUsersRepository(
        usersDao: UsersDao,
        usersRemoteDataSource: UsersRemoteDataSource
    ): UsersRepository {
        return UsersRepository(usersDao, usersRemoteDataSource)
    }



}