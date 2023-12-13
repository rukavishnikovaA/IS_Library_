package ru.development.network

import io.ktor.client.engine.*
import io.ktor.client.engine.js.*

actual fun createEngine(): HttpClientEngine {
    return Js.create()
}