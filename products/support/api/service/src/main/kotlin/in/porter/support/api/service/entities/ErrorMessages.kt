package `in`.porter.support.api.service.entities

object ErrorMessages {
    private const val REQUEST_VALIDATION_FAILURE = "Request validation Failed"

    const val INVALID_CALLER_TYPE = "Invalid Caller Type"
    const val INVALID_GEO_REGION = "Invalid Geo Region"

    fun requestValidationFailure(err: Any?): String{
        var errMsg = REQUEST_VALIDATION_FAILURE
        if(err != null) errMsg += ". Error: $err"
        return errMsg
    }
}
