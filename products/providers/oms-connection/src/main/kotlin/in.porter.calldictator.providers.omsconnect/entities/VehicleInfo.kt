package `in`.porter.calldictator.providers.omsconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class VehicleInfo(
  @JsonProperty("type")
  val type: String
)
