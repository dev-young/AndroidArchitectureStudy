package io.github.devy.architecture_study.data.model

import com.google.gson.annotations.SerializedName
import io.github.devy.architecture_study.domain.model.User

data class UserDto(
    val id: Int,
    @SerializedName("login") val name: String,
    @SerializedName("html_url") val description: String,
    @SerializedName("avatar_url") val photo: String,
)

fun UserDto.toUser(like: Boolean) = User(id, name, description, photo, like)
