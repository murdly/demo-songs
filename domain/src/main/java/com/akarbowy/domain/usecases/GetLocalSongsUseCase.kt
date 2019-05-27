package com.akarbowy.domain.usecases

import com.akarbowy.domain.repositories.SongsRepository


class GetLocalSongsUseCase(
    private val songsRepository: SongsRepository
) {

    fun execute() = songsRepository.getLocalSongs()

}