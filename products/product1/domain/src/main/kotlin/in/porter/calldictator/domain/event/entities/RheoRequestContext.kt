package `in`.porter.calldictator.domain.event.entities

data class RheoRequestContext(
  val featureName: String,
  val userId: String,
  val contextAttrs: List<ContextAttribute>?
)
