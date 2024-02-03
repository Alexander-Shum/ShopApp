package ru.shum.shopapp.ui.registration

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.shum.domain.features.registration.users.GetUserUseCase
import ru.shum.domain.features.registration.users.SaveUserUseCase
import ru.shum.domain.features.registration.users.model.User
import ru.shum.domain.features.registration.validation.ValidateNameUseCase
import ru.shum.domain.features.registration.validation.ValidatePhoneUseCase

class RegistrationViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validatePhoneUseCase: ValidatePhoneUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    var formState by mutableStateOf(MainState())

    private val _isUserRegistered = mutableStateOf(false)
    val isUserRegistered: State<Boolean> = _isUserRegistered

    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val isUserRegistered = getUserUseCase.execute().isNotEmpty()

            mainHandler.post {
                _isUserRegistered.value = isUserRegistered
            }
        }
    }

    fun saveUser() {
        if (formState.name != "" && formState.surname != "" && formState.phone != "") {
            viewModelScope.launch(Dispatchers.IO) {
                saveUserUseCase.execute(
                    User(
                        formState.name, formState.surname, formState.phone
                    )
                )

            }
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.NameChanged -> {
                formState = formState.copy(name = event.name)
                validateName()
            }

            is MainEvent.SurnameChanged -> {
                formState = formState.copy(surname = event.surname)
                validateSurname()
            }

            is MainEvent.PhoneChanged -> {
                formState = formState.copy(phone = event.phone)
                validatePhone()
            }

            is MainEvent.Submit -> {
                if (validateName()) {
                }
            }
        }
    }

    private fun validatePhone(): Boolean {
        val phoneResult = validatePhoneUseCase.execute(formState.phone)
        formState = formState.copy(phoneError = phoneResult.errorMessage)
        return phoneResult.successful
    }

    private fun validateName(): Boolean {
        val nameResult = validateNameUseCase.execute(formState.name)
        formState = formState.copy(nameError = nameResult.errorMessage)
        return nameResult.successful
    }

    private fun validateSurname(): Boolean {
        val surnameResult = validateNameUseCase.execute(formState.surname)
        formState = formState.copy(surnameError = surnameResult.errorMessage)
        return surnameResult.successful
    }
}

sealed class MainEvent {
    data class NameChanged(val name: String) : MainEvent()
    data class SurnameChanged(val surname: String) : MainEvent()
    data class PhoneChanged(val phone: String) : MainEvent()
    object Submit : MainEvent()
}

data class MainState(
    val name: String = "",
    val nameError: String? = null,

    val surname: String = "",
    val surnameError: String? = null,

    val phone: String = "",
    val phoneError: String? = null
)