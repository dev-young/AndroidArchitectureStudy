package io.github.devy.architecture_study.domain

import io.github.devy.architecture_study.domain.repositoty.UserRepository
import io.github.devy.architecture_study.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {

    private lateinit var getUsers: GetUsersUseCase
    private lateinit var repo: UserRepository

    @Before
    fun setUp() {
        repo = FakeUserRepository()
        getUsers = GetUsersUseCase(repo)
    }

    @Test
    fun `a search`() = runBlocking {
        val result = getUsers("a")
        assertEquals(result.size, 4)
    }

    @Test
    fun `b검색 결과 불러오기`() {
        runBlocking {
            val result = getUsers("b")
            assertEquals(result.size, 4)
        }

    }

    @Test
    fun `c검색 결과 불러오기`() {
        runBlocking {
            val result = getUsers("c")
            assertEquals(result.size, 4)
        }

    }
}