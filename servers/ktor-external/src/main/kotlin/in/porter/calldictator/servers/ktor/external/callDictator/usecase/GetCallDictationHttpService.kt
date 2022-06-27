package `in`.porter.calldictator.servers.ktor.external.callDictator.usecase

import `in`.porter.calldictator.api.models.entities.CallDictateRequest
import `in`.porter.calldictator.api.service.usecases.external.CallDictationApiService
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.util.*
import javax.inject.Inject

@KtorExperimentalAPI
class GetCallDictationHttpService
@Inject
constructor(
  private val callDictationApiService: CallDictationApiService
) : Traceable {

  suspend fun invoke(call: ApplicationCall) = trace {

    try{
      val params = call.request.queryParameters

      logger.info("request to conversation dictator: $params")
      val did = params.getOrFail<String>("did")
      val phone = params.getOrFail<String>("phone")
      val vertical = params.getOrFail<String>("vertical")

      val callDictateRequest = CallDictateRequest(
        did = did,
        phone = phone,
        vertical = vertical
      )

      val response = callDictationApiService.invoke(callDictateRequest)
      if (response.responseCode == 1004) {
        call.respond(HttpStatusCode.BadRequest)
      }
      call.respond(HttpStatusCode.OK, response)
    } catch (e: Exception) {
      logger.error("error encountered while processing the request. $e")
      call.respond(HttpStatusCode.UnprocessableEntity, e)
    }
  }
}
