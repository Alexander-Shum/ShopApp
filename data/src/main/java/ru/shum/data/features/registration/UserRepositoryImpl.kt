package ru.shum.data.features.registration

import ru.shum.data.db.room.user.UsersDao
import ru.shum.data.mappers.UserMapper
import ru.shum.domain.features.registration.users.UserRepository
import ru.shum.domain.features.registration.users.model.User

class UserRepositoryImpl(private val usersDao: UsersDao, private val mapper: UserMapper): UserRepository {
    override suspend fun saveUser(user: User) {
        usersDao.insertUser(mapper.mapUser(user))
    }

    override suspend fun getUser(): List<User> {
        return usersDao.getAllUsers().map { mapper.mapUserEntity(it) }
    }

    override suspend fun deleteAll() {
        usersDao.deleteAll()
    }
}