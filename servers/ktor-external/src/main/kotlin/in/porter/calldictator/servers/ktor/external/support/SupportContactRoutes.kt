package `in`.porter.calldictator.servers.ktor.external.support

import `in`.porter.calldictator.servers.ktor.external.di.HttpComponent
import io.ktor.application.*
import io.ktor.routing.*

fun Route.supportContactRoutes(httpComponent: HttpComponent){
    get("/contact_info") { httpComponent.getSupportContactHttpService.invoke(call) }
}
