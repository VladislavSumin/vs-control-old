package ru.vs.control

import org.kodein.di.DI
import org.kodein.di.bindSingleton
import ru.vs.control.domain.AboutServerInteractor
import ru.vs.control.domain.AboutServerInteractorImpl

val Di = DI {
    bindSingleton<AboutServerInteractor> { AboutServerInteractorImpl() }
}