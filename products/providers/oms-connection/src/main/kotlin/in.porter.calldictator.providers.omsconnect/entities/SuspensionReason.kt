package `in`.porter.calldictator.providers.omsconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.Nullable

data class SuspensionReason (
  @Nullable
  @JsonProperty("value")
  val value: String
  )
