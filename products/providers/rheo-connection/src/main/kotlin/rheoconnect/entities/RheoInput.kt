package rheoconnect.entities

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class RheoInput
@JsonCreator
constructor(
  @JsonProperty("feature_name")
  val feature_name: String,

  @JsonProperty("user_id")
  val user_id: String,

  @JsonProperty("context_attrs")
  val context_attrs: List<ContextAttribute>
)
