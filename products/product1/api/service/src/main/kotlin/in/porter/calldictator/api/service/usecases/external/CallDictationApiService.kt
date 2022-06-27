package `in`.porter.calldictator.api.service.usecases.external

import `in`.porter.calldictator.api.models.entities.CallDictateRequest
import `in`.porter.calldictator.api.models.entities.CallDictationResponse
import `in`.porter.calldictator.api.service.mappers.CallDictationMapper
import `in`.porter.calldictator.api.service.usecases.internal.ValidateApiRequest
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.external.CallDictationService
import javax.inject.Inject

class CallDictationApiService
@Inject
constructor(
  private val validateApiRequest: ValidateApiRequest,
  private val callDictationMapper: CallDictationMapper,
  private val callDictationService: CallDictationService
){

  suspend fun invoke(request: CallDictateRequest): CallDictationResponse {

    if (!validateApiRequest.invoke(request)) {
      CallDictationResponse.ErrorResponse(responseCode = 1004, responseMsg = "request context not proper.")
    }
    return request
      .let { callDictationMapper.mapPreprocessingRequest(it) }
      .let { callDictationService.invoke(it) }
      .let { callDictationMapper.mapPreprocessingResponse("temp") }
  }
}
