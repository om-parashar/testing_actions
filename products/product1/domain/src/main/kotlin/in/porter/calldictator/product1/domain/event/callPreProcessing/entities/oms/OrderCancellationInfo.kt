package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms

data class OrderCancellationInfo(
  val cancelReason: String,
  val cancelReasonSource: String,
  val attribution: String
)
