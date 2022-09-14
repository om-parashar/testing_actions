package `in`.porter.support.domain.usecase.external

import `in`.porter.support.domain.entities.SupportContactInfoRequest
import `in`.porter.support.domain.entities.SupportContactInfoResponse
import `in`.porter.support.domain.usecase.internal.GetSupportContactEmail
import `in`.porter.support.domain.usecase.internal.GetSupportContactNumber
import javax.inject.Inject

class SupportContactInfo
@Inject
constructor(
    private val getSupportContactNumber: GetSupportContactNumber,
    private val getSupportContactEmail: GetSupportContactEmail
){
    suspend fun invoke(request: SupportContactInfoRequest): SupportContactInfoResponse {
        val response = SupportContactInfoResponse(null, null)

        if(request.get_phone){
            val number = getSupportContactNumber.invoke(request)
            response.number = number
        }
        if(request.get_email){
            val email = getSupportContactEmail.invoke(request)
            response.email = email
        }
        return response
    }
}
