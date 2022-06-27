package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms

data class DriverCallContext(
  val id: Int,
  val uuid: String,
  val city: String,
  val language: String,
  val loginStatus: String,
  val vehicleInfo: String,
  val isSuspended: Boolean,
  val suspensionReason: String
)
