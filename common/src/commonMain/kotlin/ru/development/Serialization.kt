package ru.development

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object Serialization {
    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }
}