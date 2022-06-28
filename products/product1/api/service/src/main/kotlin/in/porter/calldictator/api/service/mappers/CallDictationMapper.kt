package `in`.porter.calldictator.api.service.mappers

import `in`.porter.calldictator.api.models.entities.CallDictateRequest
import `in`.porter.calldictator.api.models.entities.CallDictationResponse
import `in`.porter.calldictator.api.models.entities.CallDictationResponseConfig
import `in`.porter.calldictator.api.service.entities.EchoResponseConstants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponse
import javax.inject.Inject

class CallDictationMapper
@Inject
constructor() {

  fun mapPreprocessingRequest(request: CallDictateRequest) = CallerContext(
    did = request.did,
    phone = request.phone,
    customerCRTId = request.customerCRTId
  )

  fun mapPreprocessingResponse(callerResponse: CallerResponse): CallDictationResponse {

    if(callerResponse == null) return CallDictationResponse.ErrorResponse(responseCode = 2000, responseMsg = "error happened while parsing.")

    val featureContext = callerResponse.callerResponseOutputContext
    if (featureContext != null) {
      var type: String
      var value: String
      var skill: String?

      if(featureContext.ivr != null) {
        type = EchoResponseConstants.TYPE_IVR
        value = featureContext.ivr!!
        skill = featureContext.skillName
      } else {
        type = EchoResponseConstants.TYPE_QUEUE
        value = if(featureContext.queueName != null) featureContext.queueName!! else ""
        skill = featureContext.skillName
      }

      return CallDictationResponse.SuccessResponse(
        responseCode = 1000,
        responseMsg = "success",
        type = type,
        value = value,
        config = CallDictationResponseConfig(
          language = callerResponse.callContext.language,
          skill = skill
        )
      )
    }
    return CallDictationResponse.ErrorResponse(responseCode = 2000, responseMsg = "content parsed, no content found")
  }
}
