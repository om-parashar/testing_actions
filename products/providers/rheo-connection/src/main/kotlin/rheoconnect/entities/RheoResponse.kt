package rheoconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class RheoResponse(

  @JsonProperty("flag_status")
  val flagStatus: Boolean,

  @JsonProperty("msg")
  val msg: RheoMessage,

  @JsonProperty("context")
  val context: String?
)
