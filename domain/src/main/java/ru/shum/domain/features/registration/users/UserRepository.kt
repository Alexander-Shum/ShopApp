package ru.shum.domain.features.registration.users

import ru.shum.domain.features.registration.users.model.User

interface UserRepository {
    suspend fun saveUser(user: User)
    suspend fun getUser(): List<User>
    suspend fun deleteAll()
}