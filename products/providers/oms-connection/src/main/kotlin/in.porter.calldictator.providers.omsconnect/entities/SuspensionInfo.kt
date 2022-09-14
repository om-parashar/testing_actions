package `in`.porter.calldictator.providers.omsconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class SuspensionInfo(

  @JsonProperty("is_suspended")
  val isSuspended: Boolean,

  @JsonProperty("reason")
  val reason: Map<String, String>
)
