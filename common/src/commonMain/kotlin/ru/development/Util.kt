package ru.development

import ru.development.network.UnknownError


fun <T> Result<T>.exception(): Throwable {
    return exceptionOrNull() ?: UnknownError("Попытка взять исключение у успешного результат")
}

val <T> Result<T>.displayErrorMessage: String
    get() = exception().displayMessage

val Throwable.displayMessage: String
    get() = message ?: "Неизвестная ошибка: ${this::class.simpleName}"