package ru.vs.control.servers.ui.edit_server


/**
 * @param name human-readable server name
 * @param host server host
 * @param port server port
 * @param isEditMode true means we edit existing server, false means we're creating new one
 * @param isSaving trie means we now run asynchronous operation to save server to the persistence storage
 */
internal data class EditServerViewState(
    val name: String,
    val host: String,
    val port: Int,
    val isEditMode: Boolean,
    val isSaving: Boolean,
)
