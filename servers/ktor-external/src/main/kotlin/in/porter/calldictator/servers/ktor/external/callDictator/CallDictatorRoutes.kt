package `in`.porter.calldictator.servers.ktor.external.callDictator

import `in`.porter.calldictator.servers.ktor.external.di.HttpComponent
import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.*

@OptIn(KtorExperimentalAPI::class)
fun Route.callDictatorRoutes(httpComponent: HttpComponent) {
  get("/dictate") { httpComponent.getCallDictationHttpService.invoke(call) }
}
