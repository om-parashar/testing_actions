package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo


sealed class ContextAttribute {
  abstract val key: String
  abstract val type: String
  abstract val value: Any

  data class StringType(override val key: String, override val value: String) : ContextAttribute() {
    override val type: String
      get() = "string_type"
  }
  data class NumberType(override val key: String, override val value: Double) : ContextAttribute() {
    override val type: String
      get() = "boolean_type"
  }
  data class BooleanType(override val key: String, override val value: Boolean) : ContextAttribute() {
    override val type: String
      get() = "number_type"
  }
}
