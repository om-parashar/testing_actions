package `in`.porter.calldictator.providers.omsconnect.entities

data class OrderResponseContext(
  val id: Int,
  val status: String,
  val driverInfo: UserInfo,
  val customerInfo: UserInfo,
  val vehicleInfo: OrderVehicleInfo,
  val cancellationInfo: Cancellation,
  val orderStageVicinity: Map<String, String>,
  val hasWaypoints: Boolean,
  val labour: Boolean,
  val labourHelper: Boolean,
  val isHelperOrder: Boolean,
  val isBusinessOrder: Boolean,
  val outstation: Boolean,
  val alternateNumber: Map<String, String>
)
