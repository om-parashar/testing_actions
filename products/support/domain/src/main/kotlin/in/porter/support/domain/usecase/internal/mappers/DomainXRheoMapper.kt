package `in`.porter.support.domain.usecase.internal.mappers

import `in`.porter.support.domain.usecase.internal.helper.RheoContextAttributeGenerator
import `in`.porter.support.domain.usecase.internal.helper.RheoRequestContextGenerator
import `in`.porter.support.domain.entities.SupportContactInfoRequest
import `in`.porter.support.domain.entities.rheo.RheoRequestContext
import javax.inject.Inject

class DomainXRheoMapper
@Inject
constructor(
    private val rheoContextAttributeGenerator: RheoContextAttributeGenerator,
    private val rheoRequestContextGenerator: RheoRequestContextGenerator
){
    fun mapDomainToRheoRequest(request: SupportContactInfoRequest, flag: String): RheoRequestContext{
        val contextAttr = rheoContextAttributeGenerator.invoke(request)
        return rheoRequestContextGenerator.invoke(flag, request.user_id, contextAttr)
    }
}
