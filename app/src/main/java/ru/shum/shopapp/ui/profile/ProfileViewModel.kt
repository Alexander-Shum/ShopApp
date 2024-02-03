package ru.shum.shopapp.ui.profile

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shum.domain.features.catalog.DeleteAllProductsUseCase
import ru.shum.domain.features.favourite.GetFavouritesProductsUseCase
import ru.shum.domain.features.registration.users.DeleteAllUserUseCase
import ru.shum.domain.features.registration.users.GetUserUseCase
import ru.shum.domain.features.registration.users.model.User

class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getFavouritesProductsUseCase: GetFavouritesProductsUseCase,
    private val deleteAllProductsUseCase: DeleteAllProductsUseCase,
    private val deleteAllUserUseCase: DeleteAllUserUseCase
) : ViewModel() {


    private val _user = mutableStateOf(User("", "", ""))
    val user: State<User> = _user

    private val _sizeFavourites = mutableIntStateOf(0)
    val sizeFavourites = _sizeFavourites

    private val mainHandler = Handler(Looper.getMainLooper())

    init {
        initContent()
    }

    fun deleteAllUsers(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                deleteAllUserUseCase.execute()
                deleteAllProductsUseCase.execute()
            }
        }
    }

    fun initContent(){
        viewModelScope.launch(Dispatchers.IO) {
            val isUserRegistered = getUserUseCase.execute()[0]

            val list = getFavouritesProductsUseCase.execute()

            mainHandler.post {
                _user.value = isUserRegistered
                _sizeFavourites.intValue = list.size
            }
        }
    }

    fun formatRussianPhoneNumber(phoneNumber: String): String {
        val cleanedNumber = phoneNumber.replace(Regex("[^\\d]"), "")

        if (cleanedNumber.length == 11) {
            return "+7 ${cleanedNumber.substring(1, 4)} ${cleanedNumber.substring(4, 7)} " +
                    "${cleanedNumber.substring(7, 9)} ${cleanedNumber.substring(9, 11)}"
        } else {
            return phoneNumber
        }
    }
}

