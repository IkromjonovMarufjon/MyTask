package uz.infinity.mytask.data.resources.local.database.dao

import androidx.room.*
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getALlUsers(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE UserEntity.email == :email AND UserEntity.password == :password LIMIT 1")
    fun hasUser(email: String, password: String): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE UserEntity.email == :email LIMIT 1")
    fun hasEmail(email: String): UserEntity?

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateUserInfo(data: UserEntity): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(data: UserEntity)

    @Delete
    fun deleteUser(data: UserEntity): Int
}

