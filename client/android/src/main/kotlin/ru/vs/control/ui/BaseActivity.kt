package ru.vs.control.ui

import androidx.activity.ComponentActivity
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

/**
 * Base activity for all activities in module
 * implementing [DIAware] support
 */
abstract class BaseActivity : ComponentActivity(), DIAware {
    override val di: DI by closestDI()
}
