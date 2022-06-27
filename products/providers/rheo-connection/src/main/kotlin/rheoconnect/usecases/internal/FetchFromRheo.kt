package rheoconnect.usecases.internal

import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import rheoconnect.entities.RheoConnectionConstants
import rheoconnect.entities.RheoInput
import rheoconnect.entities.RheoResponse
import javax.inject.Inject

class FetchFromRheo
@Inject
constructor(
  private val mapper: SerdeMapper,
  private val httpClient: HttpClient
){
  suspend fun fetchFeature(input: RheoInput): RheoResponse {
    val url = fetchURL(RheoConnectionConstants.FEATURE_TOGGLING, mapOf())
    return input
      .let { performPostRequest(url = url, payload = input) }
      .let { logResponse(it) }
      .let { processResponse(it) }
  }

  private fun fetchURL(urlString: String, requestParams: Map<String, Any>): String {
    var url = "${RheoConnectionConstants.RHEO_HOST}$urlString"

    if(!requestParams.isEmpty()) {
      val urlParams = requestParams.map {(k, v) -> "${(k)}=${v}"}.joinToString("&")
      return "$url?$urlParams"
    }
    return url
  }

  private suspend fun performPostRequest(
    url: String,
    payload: Any
  ): HttpResponse {

    val res =  httpClient.post<HttpResponse> {
      url(url)
      header("content-Type", "application/json")
      body = payload
    }
    return res
  }

  private suspend fun logResponse(response: HttpResponse): String {
    val responseStr = response.readText()
    logger.info("response status: ${response.status}")
    logger.info("response value: $responseStr")
    return responseStr
  }

  private fun processResponse(response: String): RheoResponse {
    if(response.isBlank()) {
      return RheoResponse(
        flagStatus = false,
        msg = null,
        context = null
      )
    }
    return mapper.fromString(response, RheoResponse::class.java)
  }
}
