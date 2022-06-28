package rheoconnect.entities

data class RheoInput
//@JsonCreator
constructor(
//  @JsonProperty("feature_name")
  val featureName: String,

//  @JsonProperty("user_id")
  val userId: String,

//  @JsonProperty("context_attrs")
  val contextAttrs: List<RheoContextAttribute>
)
