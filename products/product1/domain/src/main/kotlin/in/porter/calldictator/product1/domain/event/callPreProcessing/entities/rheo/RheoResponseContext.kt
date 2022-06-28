package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo

data class RheoResponseContext(
  val status: Boolean,
  val msg: String,
  val result: Map<String, String>
)
