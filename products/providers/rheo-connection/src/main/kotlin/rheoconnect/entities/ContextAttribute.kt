package rheoconnect.entities

sealed class RheoContextAttribute {
  abstract val key: String
  abstract val type: String
  abstract val value: Any

  data class StringType(override val key: String, override val value: String) : RheoContextAttribute() {
    override val type: String
      get() = "string_type"
  }
  data class NumberType(override val key: String, override val value: Double) : RheoContextAttribute() {
    override val type: String
      get() = "boolean_type"
  }
  data class BooleanType(override val key: String, override val value: Boolean) : RheoContextAttribute() {
    override val type: String
      get() = "number_type"
  }
}
