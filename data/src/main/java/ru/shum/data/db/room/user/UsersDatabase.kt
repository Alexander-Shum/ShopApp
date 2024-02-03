package ru.shum.data.db.room.user

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.shum.data.db.room.user.UsersDao
import ru.shum.data.db.room.user.model.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 2
)
abstract class UsersDatabase: RoomDatabase() {

    abstract fun usersDao(): UsersDao
}