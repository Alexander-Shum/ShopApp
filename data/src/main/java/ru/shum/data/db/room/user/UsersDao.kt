package ru.shum.data.db.room.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.shum.data.db.room.user.model.UserEntity

@Dao
abstract class UsersDao {

    @Insert
    abstract fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM users")
    abstract fun getAllUsers(): List<UserEntity>

    @Query("DELETE FROM users")
    abstract fun deleteAll()
}