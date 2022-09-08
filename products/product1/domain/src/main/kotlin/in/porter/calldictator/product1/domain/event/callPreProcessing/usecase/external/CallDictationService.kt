package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.external

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponse
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper.CallerResponseMapper
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.DataPersistence
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.ProcessCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.ProcessFeatureContext
import javax.inject.Inject

class CallDictationService
@Inject
constructor(
  private val processCallContext: ProcessCallContext,
  private val processFeatureContext: ProcessFeatureContext,
  private val callerResponseMapper: CallerResponseMapper,
  private val dataPersistence: DataPersistence
){

  suspend fun invoke(request: CallerContext): CallerResponse {

    // Persisting the call request data.
    val call = dataPersistence.persistCallData(request)

    // Processing the call request
    val callContext = processCallContext.invoke(request)
    val featureContext = processFeatureContext.invoke(callContext)

    // Generating the response
    val callerResponse = callerResponseMapper.generate(request, callContext, featureContext)

    //persisting call response
    dataPersistence.persistCallProcessedData(callerResponse, call)
    return callerResponse
  }
}
