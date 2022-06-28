package `in`.porter.calldictator.api.models.entities

data class CallDictateRequest(
  val did: String,
  val phone: String,
  val vertical: String,
  val customerCRTId: String
)
