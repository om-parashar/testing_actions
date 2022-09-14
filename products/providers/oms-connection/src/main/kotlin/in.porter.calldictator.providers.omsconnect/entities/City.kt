package `in`.porter.calldictator.providers.omsconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty

data class City(

  @JsonProperty("name")
  val name: String
)
