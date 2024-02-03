package ru.shum.domain.features.registration.users

import ru.shum.domain.features.catalog.ProductsRepository

class DeleteAllUserUseCase(
    private val repository: UserRepository
) {

    suspend fun execute(){
        repository.deleteAll()
    }
}