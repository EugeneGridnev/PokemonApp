package ru.eugeneproj.pokemon.data.network.connection

import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {

    val isConnected: Flow<Boolean>
}