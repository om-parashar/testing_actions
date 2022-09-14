package `in`.porter.calldictator.providers.omsconnect.usecases.internal

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import org.apache.logging.log4j.kotlin.Logging
import javax.inject.Inject

class HttpOMSConnect
@Inject
constructor(
  private val httpClient: HttpClient
) {
  companion object : Logging

  suspend fun invoke(url: String) {
    makeGetRequest(url)
  }

  private suspend fun makeGetRequest(url: String) {

    val res = httpClient.get<HttpResponse> {
      url(url)
      header("Content-Type", "application/json")
    }

    val resText = res.readText()
    logger.info {"response from rheo = $resText"}
  }
}
