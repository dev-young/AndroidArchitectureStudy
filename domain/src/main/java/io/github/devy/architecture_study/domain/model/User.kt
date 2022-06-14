package io.github.devy.architecture_study.domain.model

data class User(
    val id: Int,
    val name: String,
    val description: String,
    val photo: String,
    var like: Boolean = false,
)
