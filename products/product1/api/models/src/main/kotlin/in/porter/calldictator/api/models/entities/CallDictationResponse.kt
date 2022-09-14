package `in`.porter.calldictator.api.models.entities

sealed class CallDictationResponse {
  abstract val responseCode: Int
  abstract val responseMsg: String

  data class SuccessResponse (
    override val responseMsg: String,
    override val responseCode: Int,
    val type: String,
    val value: String,
    val config: CallDictationResponseConfig?
    ): CallDictationResponse()

  data class ErrorResponse (
    override val responseMsg: String,
    override val responseCode: Int
    ): CallDictationResponse()
}
