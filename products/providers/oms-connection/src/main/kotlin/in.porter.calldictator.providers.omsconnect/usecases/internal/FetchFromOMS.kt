package `in`.porter.calldictator.providers.omsconnect.usecases.internal

import `in`.porter.calldictator.providers.omsconnect.entities.*
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class FetchFromOMS
@Inject
constructor(
  private val mapper: SerdeMapper,
  private val httpClient: HttpClient
) {

  suspend fun processDriverRequest(phone: String): DriverResponse? {
    return mapOf("phone" to phone)
      .let { fetchURL(OMSConnectionConstants.FETCH_DRIVER_API, it) }
      .let { performGetRequest(it) }
      .let { logResponse(it) }
      .let { processDriverResponse(it) }
  }


  suspend fun processCustomerRequest(phone: String): CustomerResponse? {
    return mapOf("phone" to phone)
      .let { fetchURL(OMSConnectionConstants.FETCH_CUSTOMER_API, it) }
      .let { performGetRequest(it) }
      .let { logResponse(it) }
      .let { processCustomerResponse(it) }
  }


  suspend fun processOrderRequest(caller_id: Int, caller_type: String): OrderResponse? {
    return mapOf("caller_id" to caller_id, "caller_type" to caller_type)
      .let { fetchURL(OMSConnectionConstants.FETCH_ORDER_API, it) }
      .let { performGetRequest(it) }
      .let { logResponse(it) }
      .let { processOrderResponse(it) }
  }


  suspend fun processCityRequest(did: String): CityResponse? {
    return mapOf("did" to did)
      .let { fetchURL(OMSConnectionConstants.FETCH_CITY_API, it) }
      .let { performGetRequest(it) }
      .let { logResponse(it) }
      .let { processCityResponse(it) }

  }


  private fun fetchURL(urlString: String, requestParams: Map<String, Any>): String {
    var url = "${OMSConnectionConstants.OMS_HOST_INDIA}$urlString"

    if(!requestParams.isEmpty()) {
      val urlParams = requestParams.map {(k, v) -> "${(k)}=${v}"}.joinToString("&")
      return "$url?$urlParams"
    }
    return url
  }

  private suspend fun performGetRequest(
    url: String
  ): HttpResponse {

    val res =  httpClient.get<HttpResponse> {
      url(url)
      header("content-Type", "application/json")
    }
    return res
  }

  private suspend fun logResponse(response: HttpResponse): String {
    val responseStr = response.readText()
    logger.info("response status: ${response.status}")
    logger.info("response value: $responseStr")
    return responseStr
  }

  private fun processDriverResponse(response: String): DriverResponse? {
    // return response.isNotBlank().let { mapper.fromString(response, DriverResponse::class.java) }
    if(response.isBlank()) {
      return null
    }
    return mapper.fromString(response, DriverResponse::class.java)
  }

  private fun processCustomerResponse(response: String): CustomerResponse? {
    if(response.isBlank()) {
      return null
    }
    return mapper.fromString(response, CustomerResponse::class.java)
  }

  private fun processOrderResponse(response: String): OrderResponse? {
    if(response.isBlank()) {
      return null
    }
    return mapper.fromString(response, OrderResponse::class.java)
  }

  private fun processCityResponse(response: String): CityResponse? {
    if(response.isBlank()) {
      return null
    }
    return mapper.fromString(response, CityResponse::class.java)
  }
}
