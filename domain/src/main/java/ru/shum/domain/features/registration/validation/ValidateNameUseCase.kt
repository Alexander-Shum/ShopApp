package ru.shum.domain.features.registration.validation

import ru.shum.domain.common.BaseUseCase
import ru.shum.domain.features.registration.validation.model.ValidationResult

class ValidateNameUseCase: BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Строка не должна быть пустой"
            )
        }

        val cyrillicPattern = Regex("[а-яА-ЯёЁ]+")
        val noWhitespacePattern = Regex("\\S+")

        if (!cyrillicPattern.matches(input) || !noWhitespacePattern.matches(input)) {
            return ValidationResult(
                successful = false,
                errorMessage = "Строка должна содержать только кириллицу и не содержать пробелов"
            )
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}