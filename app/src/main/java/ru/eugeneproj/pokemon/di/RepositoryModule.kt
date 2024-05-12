package ru.eugeneproj.pokemon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.eugeneproj.pokemon.data.network.connection.ConnectivityRepository
import ru.eugeneproj.pokemon.data.network.connection.ConnectivityRepositoryIMPL
import ru.eugeneproj.pokemon.data.network.repository.PokemonsRepository
import ru.eugeneproj.pokemon.data.network.repository.PokemonsRepositoryIMPL

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesConnectivityRepository(repositoryIMPL: ConnectivityRepositoryIMPL): ConnectivityRepository

    @Binds
    abstract fun providesPokemonsRepository(repositoryIMPL: PokemonsRepositoryIMPL): PokemonsRepository
}