package com.sarikaya.pokedex.di

import com.sarikaya.pokedex.common.Constants.BASE_URL
import com.sarikaya.pokedex.data.api.PokedexApiService
import com.sarikaya.pokedex.data.repository.PokedexRepositoryImpl
import com.sarikaya.pokedex.domain.repository.PokedexRepository
import com.sarikaya.pokedex.domain.usecase.GetPokemonDescriptionUseCase
import com.sarikaya.pokedex.domain.usecase.GetPokemonDetailUseCase
import com.sarikaya.pokedex.domain.usecase.GetPokemonListUseCase
import com.sarikaya.pokedex.domain.usecase.SearchPokemonUseCase
import com.sarikaya.pokedex.domain.usecase.UseCaseWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokedexServiceInstance(retrofit: Retrofit) : PokedexApiService {
        return retrofit.create(PokedexApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUseCaseWrapper(pokedexRepository: PokedexRepository) : UseCaseWrapper {
        return UseCaseWrapper(
            getPokemonListUseCase = GetPokemonListUseCase(pokedexRepository),
            searchPokemonUseCase = SearchPokemonUseCase(pokedexRepository),
            getPokemonDetailUseCase = GetPokemonDetailUseCase(pokedexRepository),
            getPokemonDescriptionUseCase = GetPokemonDescriptionUseCase(pokedexRepository)
        )
    }
}