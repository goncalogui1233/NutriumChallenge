package com.goncalo.nutriumchallenge.professionals.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Professional(
    @PrimaryKey(autoGenerate = true) var uniqueId: Int,
    val expertise: List<String>,
    val id: Int,
    val languages: List<String>,
    val name: String,
    val rating: Int,

    @ColumnInfo("profile_picture_url")
    @SerializedName("profile_picture_url")
    val profilePictureUrl: String,

    @ColumnInfo("rating_count")
    @SerializedName("rating_count")
    val ratingCount: Int,

    @ColumnInfo("about_me")
    @SerializedName("about_me")
    val aboutMe: String? = null,

    val detailTimestamp: Long? = null
)
