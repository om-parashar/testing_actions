package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo

data class IvrResponseOutputContext(
  val ivr: String?,
  val channel: String?,
  val skillName: String?,
  val queueName: String?
)
