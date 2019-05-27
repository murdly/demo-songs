package com.akarbowy.domain.usecases

import com.akarbowy.domain.repositories.SongsRepository
import com.akarbowy.domain.rules.InstantRxSchedulerRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`


internal class GetLocalSongsUseCaseTest {

    @get:Rule
    var instantRxSchedulerRule = InstantRxSchedulerRule()

    private val mockRepository: SongsRepository = mock()

    private val underTest = GetLocalSongsUseCase(mockRepository)

    @Test
    fun `gets local songs`() {
        `when`(mockRepository.getLocalSongs()).thenReturn(Single.just(emptyList()))

        underTest.execute().subscribe()

        verify(mockRepository).getLocalSongs()
    }
}