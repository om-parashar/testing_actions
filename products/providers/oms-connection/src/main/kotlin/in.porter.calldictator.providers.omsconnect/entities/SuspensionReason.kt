package `in`.porter.calldictator.providers.omsconnect.entities

import com.fasterxml.jackson.annotation.JsonProperty
import `in`.porter.calldictator.providers.utils.annotations.IgnoreInUnitTestCoverage
import org.jetbrains.annotations.Nullable

@IgnoreInUnitTestCoverage
data class SuspensionReason (
  @Nullable
  @JsonProperty("value")
  val value: String
  )
