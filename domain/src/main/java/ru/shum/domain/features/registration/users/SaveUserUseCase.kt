package ru.shum.domain.features.registration.users

import ru.shum.domain.features.registration.users.model.User

class SaveUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(input: User) {
        userRepository.saveUser(input)
    }
}