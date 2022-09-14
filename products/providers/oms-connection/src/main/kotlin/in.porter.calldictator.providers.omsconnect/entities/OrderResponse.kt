package `in`.porter.calldictator.providers.omsconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderResponse(
  @JsonProperty("status")
  val status: Int,

  @JsonProperty("msg")
  val msg: String,

  @JsonProperty("response")
  val response: OrderResponseContext
)
