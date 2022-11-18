package uz.infinity.mytask.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.infinity.mytask.domain.repository.AppRepository
import uz.infinity.mytask.domain.repository.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun getAppRepository(impl: AppRepositoryImpl): AppRepository
}

