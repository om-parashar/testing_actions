package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallDraft
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallHandlingDraft
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallerContextDraft
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponse
import com.fasterxml.jackson.databind.ObjectMapper
import javax.inject.Inject

class RepoMapper
@Inject
constructor(
  private val objectMapper: ObjectMapper
){

  fun toCallDraft(request: CallerContext): CallDraft =
    CallDraft(
      phone = request.phone,
      did = request.did,
      customerCRTId = request.customerCRTId
    )

  fun toCallContextDraft(callerResponse: CallerResponse, callId: Int): CallerContextDraft =
    CallerContextDraft(
      callId = callId,
      context = objectMapper.valueToTree(callerResponse.callContext)
    )

  fun toCallHandlingDraft(callerResponse: CallerResponse, callId: Int): CallHandlingDraft =
    CallHandlingDraft(
      callId = callId,
      context = objectMapper.valueToTree(callerResponse.callerResponseOutputContext),
      type = if (callerResponse.callerResponseOutputContext?.ivr != null) "ivr" else "queue",
      value = if (callerResponse.callerResponseOutputContext?.ivr != null) callerResponse.callerResponseOutputContext.ivr else callerResponse.callerResponseOutputContext?.queueName.toString()
    )
}
