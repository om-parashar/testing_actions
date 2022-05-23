package rheoconnect.usecases.internal

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.apache.logging.log4j.kotlin.Logging
import rheoconnect.entities.RheoConnectionConstants
import rheoconnect.entities.RheoInput
import javax.inject.Inject

class RheoConnect
@Inject
constructor(
  private val httpClient: HttpClient
) {

  companion object : Logging

  suspend fun invoke(input: RheoInput) {
    request(input)
  }

  private suspend fun request(input: RheoInput) {

    logger.info {"request to rheo for context = $input"}
    val res = httpClient.post<HttpResponse> {
      url("${RheoConnectionConstants.RHEO_HOST}/${RheoConnectionConstants.FEATURE_TOGGLING}")
      header("Content-Type", "application/json")
      body = input
    }

    val resText = res.readText()
    logger.info {"response from rheo = $resText"}
  }
}
