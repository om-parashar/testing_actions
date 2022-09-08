package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo

data class CallHandling(
  val id: Int,
  val callId: Int,
  val type: String,
  val value: String,
  val timestampCreated: String,
  val timestampUpdated: String
)
