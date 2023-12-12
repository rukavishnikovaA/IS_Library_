package ru.development.models

import kotlinx.serialization.Serializable

@Serializable
data class UserToCredentialRef(val userId: Int, val credential: LoginCredential)