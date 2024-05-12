package ru.eugeneproj.pokemon.data.model.pokemoninfomodel

import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat")
    val statValue: Int,
    val stat: StatName
)