package ru.shum.domain.features.registration.users

import ru.shum.domain.features.registration.users.model.User

class GetUserUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(): List<User> {
//        userRepository.deleteAll()
        return userRepository.getUser()
    }
}