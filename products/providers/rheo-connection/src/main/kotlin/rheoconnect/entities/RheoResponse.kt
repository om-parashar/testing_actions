package rheoconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class RheoResponse(

  @JsonProperty("flag_status")
  private val flagStatus: Boolean,

  @JsonProperty("msg")
  private val msg: RheoMessage?,

  @JsonProperty("context")
  private val context: String?
)
