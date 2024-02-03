package ru.shum.domain.features.registration.validation.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
