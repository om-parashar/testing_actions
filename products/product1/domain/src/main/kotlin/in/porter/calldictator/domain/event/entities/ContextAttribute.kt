package `in`.porter.calldictator.domain.event.entities

import java.time.Instant

sealed class ContextAttribute {
  abstract val key: String
  abstract val value: Any

  data class StringType(override val key: String, override val value: String) : ContextAttribute()
  data class NumberType(override val key: String, override val value: Double) : ContextAttribute()
  data class BooleanType(override val key: String, override val value: Boolean) : ContextAttribute()
  data class TimeStampType(override val key: String, override val value: Instant) : ContextAttribute()
}
