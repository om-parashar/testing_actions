package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.external

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.ProcessCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.ProcessFeatureContext
import javax.inject.Inject

class CallDictationService
@Inject
constructor(
  private val processCallContext: ProcessCallContext,
  private val processFeatureContext: ProcessFeatureContext
){

  suspend fun invoke(request: CallerContext) {

    request
      .let { processCallContext.invoke(it) }    // processing caller context from oms.
      .let { processFeatureContext.invoke(it) }  // processing feature context from rheo.
  }
}
