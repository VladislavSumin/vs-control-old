package ru.vs.control.servers.domain

typealias ServerId = Long

/**
 * Remote Control server info
 */
data class Server(
    /**
     * Unique server id for store it in local database
     */
    val id: ServerId,

    /**
     * Human-readable server name visible by user
     */
    val name: String,

    /**
     * Connection url
     */
    val url: String,
)