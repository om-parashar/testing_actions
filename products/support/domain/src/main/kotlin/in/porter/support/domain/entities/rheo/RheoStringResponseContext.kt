package `in`.porter.support.domain.entities.rheo

data class RheoStringResponseContext(
    val status: Boolean,
    val msg: String,
    val result: Map<String, String>
)
