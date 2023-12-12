package ru.development.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginCredential(val login: String, val password: String)

@Serializable
data class RegisterCredential(
    val login: LoginCredential,
    val user: User
)