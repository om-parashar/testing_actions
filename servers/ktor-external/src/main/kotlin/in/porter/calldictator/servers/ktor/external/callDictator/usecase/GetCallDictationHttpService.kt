package `in`.porter.calldictator.servers.ktor.external.callDictator.usecase

import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import io.ktor.application.*
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class GetCallDictationHttpService
@Inject
constructor() : Traceable {

  suspend fun invoke(call: ApplicationCall) = trace {
    val params = call.request.queryParameters

    logger.info("request to conversation dictator: $params")
    val did = params["did"]
    val phone = params["phone"]
    val vertical = params["vertical"]

  }
}