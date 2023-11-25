package com.sarikaya.pokedex.di

import com.sarikaya.pokedex.data.repository.PokedexRepositoryImpl
import com.sarikaya.pokedex.domain.repository.PokedexRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPokedexRepository(
        pokedexRepositoryImpl: PokedexRepositoryImpl
    ) : PokedexRepository
}