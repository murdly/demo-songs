package com.akarbowy.domain.usecases

import com.akarbowy.domain.repositories.SongsRepository


class GetRemoteSongsUseCase(
    private val songsRepository: SongsRepository
) {

    fun execute() = songsRepository.getRemoteSongs()

}