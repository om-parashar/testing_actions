package `in`.porter.support.domain.entities.rheo

data class RheoMapResponseContext(
    val status: Boolean,
    val msg: String,
    val result: Map<String, Map<String, String>>
)
