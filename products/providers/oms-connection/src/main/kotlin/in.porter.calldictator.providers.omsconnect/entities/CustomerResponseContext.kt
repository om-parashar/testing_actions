package `in`.porter.calldictator.providers.omsconnect.entities

data class CustomerResponseContext(
  val id: Int,
  val uuid: String,
  val city: City,
  val language: String,
  val subCategory: Map<String, String>
)
