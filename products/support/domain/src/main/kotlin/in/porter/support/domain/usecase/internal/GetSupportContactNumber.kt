package `in`.porter.support.domain.usecase.internal

import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import `in`.porter.support.domain.clients.SupportContactProviderClient
import `in`.porter.support.domain.di.ProviderNames
import `in`.porter.support.domain.entities.ErrorMessages
import `in`.porter.support.domain.entities.SupportContactInfoRequest
import `in`.porter.support.domain.entities.rheo.Constants
import `in`.porter.support.domain.entities.rheo.RheoMapResponseContext
import `in`.porter.support.domain.usecase.internal.mappers.DomainXRheoMapper
import javax.inject.Inject
import javax.inject.Named

class GetSupportContactNumber
@Inject
constructor(
    private val domainXRheoMapper: DomainXRheoMapper,
    @Named(ProviderNames.RHEO_CONNECTION)
    private val supportContactProviderClient: SupportContactProviderClient
){
    suspend fun invoke(request: SupportContactInfoRequest): String {
        val rheoRequest = domainXRheoMapper.mapDomainToRheoRequest(request, Constants.SUPPORT_NUMBER_FLAG)

        val response = try{
            supportContactProviderClient.getSupportNumberContext(rheoRequest)
        } catch (e: Exception){
            logger.error(ErrorMessages.errorFetchingData(e))
            null
        }
        return fetchNumberFromContext(request, response)
    }

    private fun fetchNumberFromContext(request: SupportContactInfoRequest, response: RheoMapResponseContext?): String {
        if(response == null) return getDefaultSupportNumber(request, null)
        val numberObj = response.result[request.geo_region] ?:
                        response.result[Constants.GEO_REGION_UNKNOWN] ?:
                        return getDefaultSupportNumber(request, response)
        return formatSupportNumber(numberObj)
    }

    private fun formatSupportNumber(obj: Map<String, String>): String{
        return "${obj["country_code"]}${obj["number"]}"
    }

    private fun getDefaultSupportNumber(request: SupportContactInfoRequest, response: RheoMapResponseContext?): String{
        logger.error(ErrorMessages.returningDefaultSupportInfo(request, response))
        return "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
    }
}
