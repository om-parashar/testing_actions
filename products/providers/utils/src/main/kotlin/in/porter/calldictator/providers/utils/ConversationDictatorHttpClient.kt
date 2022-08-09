package `in`.porter.calldictator.providers.utils

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class ConversationDictatorHttpClient
@Inject
constructor(
  private val httpClient: HttpClient
){

  suspend fun performPostRequest(
    url: String,
    data: Any,
    authToken: String
  ): HttpResponse {

    return httpClient.post<HttpResponse> {
      url(url)
      header("Content-Type", "application/json")
      header("authorization", "Bearer $authToken")
      header("cache-control", "Bearer $authToken")
      body = data
      timeout { requestTimeoutMillis = 15000 }
    }
  }

  suspend fun performGetRequest(
    url: String,
    requestParamMap: Map<String, String>
  ): HttpResponse {

    val res =  httpClient.get<HttpResponse> {
      url(url)
      header("content-Type", "application/json")
    }

    return res
  }
}
