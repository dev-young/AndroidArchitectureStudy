package io.github.devy.architecture_study.data.model

import com.google.gson.annotations.SerializedName

data class UserListRes(
    @SerializedName("total_count") val count: Int,
//    @SerializedName("incomplete_results") val incomplete: Boolean,
    @SerializedName("items") val list: List<UserDto>,
)
