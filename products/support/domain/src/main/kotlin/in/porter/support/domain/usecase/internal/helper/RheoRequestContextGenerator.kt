package `in`.porter.support.domain.usecase.internal.helper

import `in`.porter.support.domain.entities.rheo.ContextAttribute
import `in`.porter.support.domain.entities.rheo.RheoRequestContext
import javax.inject.Inject

class RheoRequestContextGenerator
@Inject
constructor(){
    fun invoke(feature_name: String, user_id: String, context_attrs: List<ContextAttribute>): RheoRequestContext {
        return RheoRequestContext(
            featureName = feature_name,
            userId = user_id,
            contextAttrs = context_attrs
        )
    }
}
