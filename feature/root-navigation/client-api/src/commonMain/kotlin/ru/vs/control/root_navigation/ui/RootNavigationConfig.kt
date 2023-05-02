package ru.vs.control.root_navigation.ui

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

@Parcelize
sealed interface RootNavigationConfig : Parcelable {
    object MainScreen : RootNavigationConfig
    data class EditServer(val serverId: Long?) : RootNavigationConfig
}
