package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create

data class CallDraft(
  val phone: String,
  val did: String,
  val customerCRTId: String
)
