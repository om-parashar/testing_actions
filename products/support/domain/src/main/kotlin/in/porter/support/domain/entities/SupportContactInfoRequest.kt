package `in`.porter.support.domain.entities

data class SupportContactInfoRequest(
    val caller_type: String,
    val geo_region: String,
    val get_phone: Boolean,
    val get_email: Boolean,
    val user_id: String,
    val vertical: String
)
