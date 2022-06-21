package io.github.devy.architecture_study.domain

import io.github.devy.architecture_study.domain.model.User
import io.github.devy.architecture_study.domain.repositoty.UserRepository
import kotlinx.coroutines.delay

class FakeUserRepository : UserRepository {
    val list = mutableListOf<User>()

    override suspend fun getUsers(query: String): List<User> {
        delay(100)
        if (list.isEmpty()) {
            list.add(User(1, "a", "", "", false))
            list.add(User(2, "b", "", "", false))
            list.add(User(3, "c", "", "", false))
            list.add(User(4, "a1", "", "", false))
            list.add(User(5, "b1", "", "", false))
            list.add(User(6, "c1", "", "", false))
            list.add(User(7, "a2", "", "", false))
            list.add(User(8, "b2", "", "", false))
            list.add(User(9, "c2", "", "", false))
            list.add(User(10, "a3", "", "", false))
            list.add(User(11, "b3", "", "", false))
            list.add(User(12, "c3", "", "", false))
        }
        return list.filter { it.name.contains(query) }
    }

    override suspend fun updateLike(userId: Int, like: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUserFromCache(userId: Int): User? {
        TODO("Not yet implemented")
    }

    override fun getUsersFromCache(onlyLike: Boolean): List<User> {
        TODO("Not yet implemented")
    }
}