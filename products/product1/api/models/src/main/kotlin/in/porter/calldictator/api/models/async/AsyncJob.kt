package `in`.porter.calldictator.api.models.async

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "job",
  visible = true
)
@JsonSubTypes(
  value = [
  ]
)
open class AsyncJob
