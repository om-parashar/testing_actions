package `in`.porter.support.api.models.entities

sealed class SupportContactServiceResponse {

    data class SuccessResponse(
        val number: String?,
        val email: String?
    ) : SupportContactServiceResponse()

    data class ErrorResponse(
        val responseMsg: String
    ) : SupportContactServiceResponse()
}
