package `in`.porter.calldictator.providers.omsconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class DriverResponseContext(
  @JsonProperty("id")
  val id: Int,

  @JsonProperty("uuid")
  val uuid: String,

  @JsonProperty("city")
  val city: City,

  @JsonProperty("language")
  val language: String,

  @JsonProperty("login_status")
  val loginStatus: String,

  @JsonProperty("vehicle_info")
  val vehicleInfo: VehicleInfo,

  @JsonProperty("suspension_info")
  val suspensionInfo: SuspensionInfo
)
