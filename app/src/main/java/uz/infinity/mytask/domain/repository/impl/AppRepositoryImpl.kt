package uz.infinity.mytask.domain.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.infinity.mytask.data.model.ScreenEnum
import uz.infinity.mytask.data.resources.local.database.dao.UserDao
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity
import uz.infinity.mytask.data.resources.local.pref.AppPreferences
import uz.infinity.mytask.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val pref: AppPreferences,
    private val userDao: UserDao
) : AppRepository {

    override fun getStartScreenEnum(): ScreenEnum = pref.currentScreen
    override fun logOutUser() {
        pref.currentScreen = ScreenEnum.LOGIN
    }

    override fun checkHasUser(email: String, password: String): Flow<Result<Unit>> = flow {
        if (userDao.hasUser(email, password) == null) emit(Result.failure(Exception()))
        else {
            pref.currentScreen = ScreenEnum.HOME
            emit(Result.success(Unit))
        }
    }.flowOn(Dispatchers.IO)

    override fun checkHasEmail(email: String): Flow<Result<Int>> = flow {
        val result = userDao.hasEmail(email)
        if (result == null) emit(Result.failure(Exception()))
        else emit(Result.success(result.id))
    }.flowOn(Dispatchers.IO)

    override fun insertNewUser(email: String, password: String): Flow<Result<Unit>> = flow {
        if (userDao.hasEmail(email) == null) {
            userDao.insertUser(UserEntity(0, email, password))
            pref.currentScreen = ScreenEnum.HOME
            emit(Result.success(Unit))
        } else emit(Result.failure(Exception()))
    }.flowOn(Dispatchers.IO)

    override fun updateUserInfo(id: Int, email: String, password: String): Flow<Unit> = flow {
        userDao.updateUserInfo(UserEntity(id, email, password))
        emit(Unit)
    }.flowOn(Dispatchers.IO)

    override fun updateUser(data: UserEntity): Flow<Result<Unit>> = flow {
        if (userDao.hasEmail(data.email) == null) {
            userDao.updateUserInfo(data)
            emit(Result.success(Unit))
        } else emit(Result.failure(Exception()))
    }.flowOn(Dispatchers.IO)

    override fun getAllUsers(): Flow<List<UserEntity>> = flow {
        emit(userDao.getALlUsers())
    }.flowOn(Dispatchers.IO)

    override fun deleteUser(data: UserEntity): Flow<Unit> = flow {
        userDao.deleteUser(data)
        emit(Unit)
    }.flowOn(Dispatchers.IO)
}
