package ru.development.network

import io.ktor.client.engine.*
import io.ktor.client.engine.java.*

actual fun createEngine(): HttpClientEngine {
    return Java.create()
}