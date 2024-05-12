package ru.eugeneproj.pokemon.data.model.pokemoninfomodel

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String
)