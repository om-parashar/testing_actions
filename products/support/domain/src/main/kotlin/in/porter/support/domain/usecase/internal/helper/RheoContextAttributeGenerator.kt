package `in`.porter.support.domain.usecase.internal.helper

import `in`.porter.support.domain.entities.SupportContactInfoRequest
import `in`.porter.support.domain.entities.rheo.ContextAttribute
import javax.inject.Inject

class RheoContextAttributeGenerator
@Inject
constructor(){
    fun invoke(supportContactRequest: SupportContactInfoRequest): List<ContextAttribute> {
        val contextAttrs =  mutableListOf<ContextAttribute>()
        contextAttrs.add(buildString("caller_type", supportContactRequest.caller_type))
        contextAttrs.add(buildString("city", supportContactRequest.geo_region))
        contextAttrs.add(buildString("vertical", supportContactRequest.vertical))

        return contextAttrs
    }

    private fun buildString(key: String, value: String) = ContextAttribute.StringType(
        key = key,
        value = value
    )

    private fun buildBoolean(key: String, value: Boolean) = ContextAttribute.BooleanType(
        key = key,
        value = value
    )

    private fun buildNumber(key: String, value: Double) = ContextAttribute.NumberType(
        key = key,
        value = value
    )
}
