package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms

data class CustomerCallContext(
  val id: Int,
  val uuid: String,
  val city: String,
  val language: String,
  val subCategory: String
)
