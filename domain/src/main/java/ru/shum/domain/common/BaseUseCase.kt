package ru.shum.domain.common

interface BaseUseCase<In, Out> {
    fun execute(input: In): Out
}
