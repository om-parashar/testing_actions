package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo

data class CallerResponseOutputContext(
  val queueName: String?,
  val ivr: String?,
  val channel: String?,
  val skillName: String?
)
