package `in`.porter.support.api.models.entities

data class  SupportContactServiceRequest(
    val caller_type: String,
    val geo_region: String,
    val get_phone: Boolean,
    val get_email: Boolean,
    val user_id: String = Constants.SERVICE_USER_ID,
    val vertical: String = Constants.DEFAULT_VERTICAL
)
