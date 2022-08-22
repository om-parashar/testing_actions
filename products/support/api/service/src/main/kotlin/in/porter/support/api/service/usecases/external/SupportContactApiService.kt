package `in`.porter.support.api.service.usecases.external

import `in`.porter.support.api.models.entities.SupportContactServiceRequest
import `in`.porter.support.api.models.entities.SupportContactServiceResponse
import `in`.porter.support.api.service.usecases.internal.mappers.ServiceXDomainMapper
import `in`.porter.support.domain.usecase.external.SupportContactInfo
import javax.inject.Inject

class SupportContactApiService
@Inject
constructor(
    private val serviceXDomainMapper: ServiceXDomainMapper,
    private val supportContactInfo: SupportContactInfo
){
    suspend fun invoke(request: SupportContactServiceRequest): SupportContactServiceResponse {
        val domainRequest = serviceXDomainMapper.mapServiceToDomainRequest(request)
        val response = supportContactInfo.invoke(domainRequest)
        return serviceXDomainMapper.mapDomainToServiceResponse(response)
    }
}
