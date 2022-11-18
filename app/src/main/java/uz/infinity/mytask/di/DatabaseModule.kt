package uz.infinity.mytask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.infinity.mytask.data.resources.local.database.AppDatabase
import uz.infinity.mytask.data.resources.local.database.dao.UserDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "MyDatabase.db").build()
    }

    @Provides
    fun getUsersDao(database: AppDatabase): UserDao = database.getUsersDao()
}
