package ru.vs.control.platform

import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.Route

/**
 * Sets up [Routing] to serve resources as static content.
 * All resources inside [basePackage] will be accessible recursively at "[remotePath]/path/to/resource".
 * If requested resource doesn't exist and [index] is not `null`,
 * then response will be [index] resource in the requested package.
 *
 * If requested resource doesn't exist and no [index] specified, response will be 404 Not Found.
 */
actual fun Route.platformStaticResources(
    remotePath: String,
    basePackage: String?,
    index: String?
): Route = staticResources(remotePath, basePackage, index)
