package `in`.porter.calldictator.servers.ktor.external.support.usecase

import `in`.porter.support.api.models.entities.SupportContactServiceRequest
import `in`.porter.support.api.service.usecases.external.SupportContactApiService
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.util.*
import javax.inject.Inject

class GetSupportContactHttpService
@Inject
constructor(
    private val supportContactApiService: SupportContactApiService
){
    suspend fun invoke(call: ApplicationCall){
        // Get request params
        val params = call.request.queryParameters
        val callerType = params.getOrFail("caller_type")
        val geoRegion = params.getOrFail("geo_region")
        val getPhone = params["get_phone"]?.toBoolean() ?: true
        val getEmail = params["get_email"]?.toBoolean() ?: true

        // generate request
        val request = SupportContactServiceRequest(
            caller_type = callerType,
            geo_region = geoRegion,
            get_phone = getPhone,
            get_email = getEmail
        )

        // invoke service layer
        val response: Any = supportContactApiService.invoke(request)
        call.respond(response)
    }
}
