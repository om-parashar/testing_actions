package `in`.porter.support.domain.usecase.internal

import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import `in`.porter.support.domain.clients.SupportContactProviderClient
import `in`.porter.support.domain.di.ProviderNames
import `in`.porter.support.domain.entities.ErrorMessages
import `in`.porter.support.domain.entities.SupportContactInfoRequest
import `in`.porter.support.domain.entities.rheo.Constants
import `in`.porter.support.domain.entities.rheo.RheoStringResponseContext
import `in`.porter.support.domain.usecase.internal.mappers.DomainXRheoMapper
import javax.inject.Inject
import javax.inject.Named

class GetSupportContactEmail
@Inject
constructor(
    private val domainXRheoMapper: DomainXRheoMapper,
    @Named(ProviderNames.RHEO_CONNECTION)
    private val supportContactProviderClient: SupportContactProviderClient
){
    suspend fun invoke(request: SupportContactInfoRequest): String {
        val rheoRequest = domainXRheoMapper.mapDomainToRheoRequest(request, Constants.SUPPORT_EMAIL_FLAG)

        val response = try{
            supportContactProviderClient.getSupportEmailContext(rheoRequest)
        } catch (e: Exception){
            logger.error(ErrorMessages.errorFetchingData(e))
            null
        }
        return fetchEmailFromContext(request, response)
    }

    private fun fetchEmailFromContext(request: SupportContactInfoRequest, response: RheoStringResponseContext?): String {
        if(response == null) return getDefaultSupportEmail(request, null)
        return response.result["email"] ?: getDefaultSupportEmail(request, response)
    }

    private fun getDefaultSupportEmail(request: SupportContactInfoRequest, response: RheoStringResponseContext?): String{
        logger.error(ErrorMessages.returningDefaultSupportInfo(request, response))
        return Constants.DEFAULT_SUPPORT_EMAIL
    }
}
