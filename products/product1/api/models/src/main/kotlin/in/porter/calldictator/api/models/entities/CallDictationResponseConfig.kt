package `in`.porter.calldictator.api.models.entities

data class CallDictationResponseConfig(
  val preferred_language: String?,
  val skill: String?,
  val caller_type: String,
  val suspension_reason: String?
)
