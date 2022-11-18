package uz.infinity.mytask.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.infinity.mytask.data.model.ScreenEnum
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity

interface AppRepository {
    fun getStartScreenEnum(): ScreenEnum
    fun logOutUser()

    fun checkHasUser(email: String, password: String): Flow<Result<Unit>>
    fun checkHasEmail(email: String): Flow<Result<Int>>

    fun insertNewUser(email: String, password: String): Flow<Result<Unit>>
    fun updateUserInfo(id: Int, email: String, password: String): Flow<Unit>
    fun updateUser(data: UserEntity): Flow<Result<Unit>>
    fun getAllUsers(): Flow<List<UserEntity>>
    fun deleteUser(data: UserEntity): Flow<Unit>
}