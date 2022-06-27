package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.UserCallContext
import javax.inject.Inject

class ProcessFeatureContext
@Inject
constructor(
  private val voicebotContext: VoicebotContext,
  private val ivrContext: IvrContext,
  private val inboundContext: InboundContext
){
  suspend fun invoke(userCallContext: UserCallContext) {
    voicebotContext.fetch(userCallContext)
    ivrContext.fetch(userCallContext)
    inboundContext.fetch(userCallContext)
  }
}
