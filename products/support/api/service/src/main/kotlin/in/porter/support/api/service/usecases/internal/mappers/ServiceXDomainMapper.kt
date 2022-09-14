package `in`.porter.support.api.service.usecases.internal.mappers

import `in`.porter.support.api.models.entities.SupportContactServiceRequest
import `in`.porter.support.api.models.entities.SupportContactServiceResponse
import `in`.porter.support.domain.entities.SupportContactInfoRequest
import `in`.porter.support.domain.entities.SupportContactInfoResponse
import javax.inject.Inject

class ServiceXDomainMapper
@Inject
constructor(){
    fun mapServiceToDomainRequest(request: SupportContactServiceRequest): SupportContactInfoRequest{
        return SupportContactInfoRequest(
            request.caller_type,
            request.geo_region,
            request.get_phone,
            request.get_email,
            request.user_id,
            request.vertical)
    }

    fun mapDomainToServiceResponse(response: SupportContactInfoResponse): SupportContactServiceResponse {
        return SupportContactServiceResponse.SuccessResponse(
            number = response.number,
            email = response.email
        )
    }
}
