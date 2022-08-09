package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo

data class RheoRequestContext(
  val featureName: String,
  val userId: String,
  val contextAttrs: List<ContextAttribute>?
)
