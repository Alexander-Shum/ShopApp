package ru.shum.data.mappers

import ru.shum.data.db.room.user.model.UserEntity
import ru.shum.domain.features.registration.users.model.User

class UserMapper{
    fun mapUser(user: User): UserEntity {
        return UserEntity(
            name = user.name,
            surname = user.surname,
            phone = user.phone
        )
    }

    fun mapUserEntity(user: UserEntity): User {
        return User(
            name = user.name,
            surname = user.surname,
            phone = user.phone
        )
    }
}
