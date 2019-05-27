package com.akarbowy.domain.usecases

import com.akarbowy.domain.repositories.SongsRepository


class GetAllSongsUseCase(
    private val songsRepository: SongsRepository
) {

    fun execute() = songsRepository.getAllSongs()

}