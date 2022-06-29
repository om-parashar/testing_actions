package `in`.porter.calldictator.providers.omsconnect.usecases.internal

import `in`.porter.calldictator.providers.omsconnect.entities.*
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Inject

class FetchFromOMS
@Inject
constructor(
  private val mapper: SerdeMapper,
  private val httpClient: HttpClient
) {

  suspend fun processDriverRequest(phone: String): DriverResponse? {
    val url = fetchURL(OMSConnectionConstants.FETCH_DRIVER_API, mapOf("phone" to phone))
    val response = performGetRequest(url = url)
    return processDriverResponse(response)
  }


  suspend fun processCustomerRequest(phone: String): CustomerResponse? {
    val url = fetchURL(OMSConnectionConstants.FETCH_CUSTOMER_API, mapOf("phone" to phone))
    val response = performGetRequest(url = url)
    return processCustomerResponse(response)

    /*return mapOf("phone" to phone)
      .let { fetchURL(OMSConnectionConstants.FETCH_CUSTOMER_API, it) }
      .let { performGetRequest(it) }
      .let { logResponse(it) }
      .let { processCustomerResponse(it) }*/
  }


  suspend fun processOrderRequest(caller_id: Int, caller_type: String): OrderResponse? {
    val url = fetchURL(OMSConnectionConstants.FETCH_ORDER_API, mapOf("caller_id" to caller_id, "caller_type" to caller_type))
    val response = performGetRequest(url = url)
    return processOrderResponse(response)

    /*return mapOf("caller_id" to caller_id, "caller_type" to caller_type)
      .let { fetchURL(OMSConnectionConstants.FETCH_ORDER_API, it) }
      .let { performGetRequest(it) }
      .let { logResponse(it) }
      .let { processOrderResponse(it) }*/
  }


  suspend fun processCityRequest(did: String): CityResponse? {
    val url = fetchURL(OMSConnectionConstants.FETCH_CITY_API, mapOf("did" to did))
    val response = performGetRequest(url = url)
    return processCityResponse(response)

    /*return mapOf("did" to did)
      .let { fetchURL(OMSConnectionConstants.FETCH_CITY_API, it) }
      .let { performGetRequest(it) }
      .let { logResponse(it) }
      .let { processCityResponse(it) }*/

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
  ): String {

    val res =  httpClient.get<HttpResponse> {
      url(url)
      header("content-Type", "application/json")
      timeout { requestTimeoutMillis = 30*15000 }
    }
    return if(res.status.isSuccess()) {
      val responseStr = res.readText()
      logger.info("success response: $responseStr")
      responseStr
    } else {
      logger.info("error response: ")
      ""
    }
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
