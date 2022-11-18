package uz.infinity.mytask.data.resources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.infinity.mytask.data.resources.local.database.dao.UserDao
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUsersDao(): UserDao
}

