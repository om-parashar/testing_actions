package `in`.porter.calldictator.api.service.mappers

import `in`.porter.calldictator.api.models.entities.CallDictateRequest
import `in`.porter.calldictator.api.models.entities.CallDictationResponse
import `in`.porter.calldictator.api.models.entities.CallDictationResponseConfig
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import javax.inject.Inject

class CallDictationMapper
@Inject
constructor() {

  fun mapPreprocessingRequest(request: CallDictateRequest) = CallerContext(
    did = request.did,
    phone = request.phone
  )

  fun mapPreprocessingResponse(temp: String): CallDictationResponse {
    if (temp != null) {
      CallDictationResponse.SuccessResponse(
        responseCode = 1001,
        responseMsg = "success",
        type = "",
        value = "",
        config = CallDictationResponseConfig(
          language = "",
          skill = ""
        )
      )
    }
    return CallDictationResponse.ErrorResponse(responseCode = 1002, responseMsg = "content parsed, no content found")
  }
}
