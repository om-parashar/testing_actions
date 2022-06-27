package `in`.porter.calldictator.providers.omsconnect.entities

data class OrderCancellationInfo(
  val cancelReason: String,
  val cancelReasonSource: String,
  val attribution: String
)
