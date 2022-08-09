package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.external

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponse
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.CallerResponseMapper
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.ProcessCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.ProcessFeatureContext
import javax.inject.Inject

class CallDictationService
@Inject
constructor(
  private val processCallContext: ProcessCallContext,
  private val processFeatureContext: ProcessFeatureContext,
  private val callerResponseMapper: CallerResponseMapper
){

  suspend fun invoke(request: CallerContext): CallerResponse {

    val callContext = processCallContext.invoke(request)
    val featureContext = processFeatureContext.invoke(callContext)
    val callerResponse = callerResponseMapper.generate(request, callContext, featureContext)
    // TODO: 28/06/22 persist callerResponse in database.
    return callerResponse
  }
}