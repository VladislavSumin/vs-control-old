package ru.vs.control.services.domain

import ru.vs.control.id.Id

abstract class BaseService(override val id: Id.SimpleId) : Service
