package me.gserv.dumblin.config

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Config(
    val token: String = System.getenv("TOKEN") ?: "",

    @SerialName("owner_id")
    val ownerId: Long = System.getenv("OWNER_ID")?.toLong() ?: 0
)