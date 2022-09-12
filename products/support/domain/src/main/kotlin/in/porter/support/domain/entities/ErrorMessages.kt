package `in`.porter.support.domain.entities

object ErrorMessages {
    const val ERROR_FETCHING_DATA = "Error fetching data from Rheo"

    fun errorFetchingData(err: Any?): String{
        var errMsg = ERROR_FETCHING_DATA
        if(err != null) errMsg += ". Error: $err"
        return errMsg
    }

    fun returningDefaultSupportInfo(request: Any?, response: Any?): String{
        return "Returning Default Value for support contact.\n" +
                "Request: $request\n" +
                "Response: $response"
    }
}
