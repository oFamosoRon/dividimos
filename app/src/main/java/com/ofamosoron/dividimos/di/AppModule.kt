package com.ofamosoron.dividimos.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ofamosoron.dividimos.data.database.DividimosDao
import com.ofamosoron.dividimos.data.database.SplitzDatabase
import com.ofamosoron.dividimos.data.delegate.DialogDelegateImpl
import com.ofamosoron.dividimos.data.repository.LocalDatabaseRepositoryImpl
import com.ofamosoron.dividimos.data.usecase.ClearDatabaseUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.CalculateCheckUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.GetAllDishesUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.GetAllGuestsUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.GetDishByIdUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.GetStoredCheckByIdUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.GetGuestByIdUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.StoreDishUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.StoreGuestUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.StoreCheckUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.UpdateStoredDishUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.SharedPreferencesUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.UpdateStoredCheckUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.UpdateGuestUseCaseImpl
import com.ofamosoron.dividimos.data.usecase.RemoveGuestsFromDishUseCaseImpl
import com.ofamosoron.dividimos.domain.delegate.DialogDelegate
import com.ofamosoron.dividimos.domain.repository.LocalDatabaseRepository
import com.ofamosoron.dividimos.domain.usecase.ClearDatabaseUseCase
import com.ofamosoron.dividimos.domain.usecase.CalculateCheckUseCase
import com.ofamosoron.dividimos.domain.usecase.GetGuestByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetAllGuestsUseCase
import com.ofamosoron.dividimos.domain.usecase.GetAllDishesUseCase
import com.ofamosoron.dividimos.domain.usecase.GetDishByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.GetStoredCheckByIdUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredCheckUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateGuestUseCase
import com.ofamosoron.dividimos.domain.usecase.UpdateStoredDishUseCase
import com.ofamosoron.dividimos.domain.usecase.StoreDishUseCase
import com.ofamosoron.dividimos.domain.usecase.StoreGuestUseCase
import com.ofamosoron.dividimos.domain.usecase.StoreCheckUseCase
import com.ofamosoron.dividimos.domain.usecase.RemoveGuestsFromDishUseCase
import com.ofamosoron.dividimos.domain.usecase.SharedPreferencesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@SuppressWarnings("TooManyFunctions")
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, SplitzDatabase::class.java, "splitz_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSplitzDao(database: SplitzDatabase) =
        database.databaseDao

    @Provides
    fun provideGetAllDishesUseCase(repository: LocalDatabaseRepository): GetAllDishesUseCase =
        GetAllDishesUseCaseImpl(localDatabaseRepository = repository)

    @Provides
    fun provideGetAllGuestsUseCase(repository: LocalDatabaseRepository): GetAllGuestsUseCase =
        GetAllGuestsUseCaseImpl(localDatabaseRepository = repository)

    @Provides
    fun provideStoreDishUseCase(repository: LocalDatabaseRepository): StoreDishUseCase =
        StoreDishUseCaseImpl(localDatabaseRepository = repository)

    @Provides
    fun provideStoreGuestUseCase(repository: LocalDatabaseRepository): StoreGuestUseCase =
        StoreGuestUseCaseImpl(localDatabaseRepository = repository)

    @Provides
    fun provideUpdateDishGuestsCase(repository: LocalDatabaseRepository): UpdateStoredDishUseCase =
        UpdateStoredDishUseCaseImpl(localDatabaseRepository = repository)

    @Provides
    fun provideCalculateCheckUseCase(): CalculateCheckUseCase =
        CalculateCheckUseCaseImpl()

    @Provides
    fun provideStoreCheckUseCase(localDatabaseRepository: LocalDatabaseRepository): StoreCheckUseCase =
        StoreCheckUseCaseImpl(localDatabaseRepository = localDatabaseRepository)

    @Provides
    fun provideGetStoredCheckUseCase(localDatabaseRepository: LocalDatabaseRepository): GetStoredCheckByIdUseCase =
        GetStoredCheckByIdUseCaseImpl(localDatabaseRepository = localDatabaseRepository)

    @Provides
    fun provideUpdateCheckUseCase(localDatabaseRepository: LocalDatabaseRepository): UpdateStoredCheckUseCase =
        UpdateStoredCheckUseCaseImpl(localDatabaseRepository = localDatabaseRepository)

    @Provides
    fun provideUpdateGuestUseCase(localDatabaseRepository: LocalDatabaseRepository): UpdateGuestUseCase =
        UpdateGuestUseCaseImpl(localDatabaseRepository = localDatabaseRepository)

    @Provides
    fun provideGetGuestByIdUseCase(localDatabaseRepository: LocalDatabaseRepository): GetGuestByIdUseCase =
        GetGuestByIdUseCaseImpl(localDatabaseRepository = localDatabaseRepository)

    @Provides
    fun provideGetDishByIdUseCase(localDatabaseRepository: LocalDatabaseRepository): GetDishByIdUseCase =
        GetDishByIdUseCaseImpl(localDatabaseRepository = localDatabaseRepository)

    @Provides
    fun provideClearDatabaseUseCase(localDatabaseRepository: LocalDatabaseRepository): ClearDatabaseUseCase =
        ClearDatabaseUseCaseImpl(localDatabaseRepository = localDatabaseRepository)

    @Provides
    fun provideRemoveGuestsFromDishUseCase(localDatabaseRepository: LocalDatabaseRepository): RemoveGuestsFromDishUseCase =
        RemoveGuestsFromDishUseCaseImpl(localDatabaseRepository = localDatabaseRepository)

    @Provides
    fun provideDialogDelegate(): DialogDelegate = DialogDelegateImpl()

    @Provides
    fun provideSharedPreferencesUseCase(@ApplicationContext context: Context): SharedPreferencesUseCase =
        SharedPreferencesUseCaseImpl(context = context)

    @Provides
    @Singleton
    fun provideRepository(dao: DividimosDao): LocalDatabaseRepository =
        LocalDatabaseRepositoryImpl(dao = dao)

}
