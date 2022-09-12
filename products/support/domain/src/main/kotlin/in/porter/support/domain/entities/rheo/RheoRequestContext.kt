package `in`.porter.support.domain.entities.rheo

data class RheoRequestContext(
    val featureName: String,
    val userId: String,
    val contextAttrs: List<ContextAttribute>
)
