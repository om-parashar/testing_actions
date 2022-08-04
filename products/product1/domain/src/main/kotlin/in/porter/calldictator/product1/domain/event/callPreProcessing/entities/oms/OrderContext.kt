package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms

data class OrderContext(
  val id: Int,
  val status: String,
  val driverId: Int?,
  val customerId: Int?,
  val requestedVehicleName: String,
  val cancellationReason: String?,
  val cancellationReasonSource: String?,
  val cancellationReasonAttribution: String?,
  val orderStagVicinity: String,
  val hasWaypoints: Boolean,
  val labour: Boolean,
  val labourHelper: Boolean,
  val isHelperOrder: Boolean,
  val isBusinessOrder: Boolean,
  val outstation: Boolean,
  val alternateNumber: String?
)
