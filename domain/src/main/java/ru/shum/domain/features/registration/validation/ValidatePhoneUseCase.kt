package ru.shum.domain.features.registration.validation

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import ru.shum.domain.common.BaseUseCase
import ru.shum.domain.features.registration.validation.model.ValidationResult

class ValidatePhoneUseCase : BaseUseCase<String, ValidationResult> {
    override fun execute(input: String): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Строка не должна быть пустой"
            )
        }

        if (!input.startsWith("+")) {
            return ValidationResult(successful = false, errorMessage = "Номер должен начинаться с '+'")
        }

        val phoneNumberUtil = PhoneNumberUtil.getInstance()

        try {
            val phoneNumber = phoneNumberUtil.parse(input, "RU") // Укажите код страны по ISO 3166-1 alpha-2 (например, "RU" для России)

            if (!phoneNumberUtil.isValidNumber(phoneNumber)) {
                return ValidationResult(successful = false, errorMessage = "Некорректный номер телефона")
            }
        } catch (e: NumberParseException) {
            return ValidationResult(successful = false, errorMessage = "Некорректный номер телефона")
        }

        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}