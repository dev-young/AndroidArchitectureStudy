package io.github.devy.architecture_study.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikeUserEntity(
    @PrimaryKey val userId: Int,
)
