package rheoconnect.usecases.internal

import `in`.porter.calldictator.providers.utils.annotations.IgnoreInUnitTestCoverage
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import rheoconnect.entities.*
import javax.inject.Inject

class FetchFromRheo
@Inject
constructor(
  private val rheoConfig: RheoConfig,
  private val mapper: SerdeMapper,
  private val httpClient: HttpClient
){
  suspend fun fetchFeature(input: RheoInput): RheoResponse {
    val url = fetchURL(rheoConfig.feature_toggling, mapOf())
    val response = performPostRequest(url = url, payload = input)
    return processResponse(response)
  }

  private fun fetchURL(urlString: String, requestParams: Map<String, Any>): String {
    val url = "${rheoConfig.host}$urlString"

    if(!requestParams.isEmpty()) {
      val urlParams = requestParams.map {(k, v) -> "${(k)}=${v}"}.joinToString("&")
      return "$url?$urlParams"
    }
    return url
  }

  @IgnoreInUnitTestCoverage
  private suspend fun performPostRequest(
    url: String,
    payload: Any
  ): String {

    val res =  httpClient.post<HttpResponse> {
      url(url)
      header("content-Type", "application/json")
      body = payload
      timeout { requestTimeoutMillis = 30*15000 }
    }
    val responseStr = res.readText()
    logger.info("response status: ${res.status}")
    logger.info("response value: $responseStr")
    return responseStr
  }

  @IgnoreInUnitTestCoverage
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
        msg = RheoMessage.UNKNOWN_ERROR,
        context = null
      )
    }
    return mapper.fromString(response, RheoResponse::class.java)
  }
}
