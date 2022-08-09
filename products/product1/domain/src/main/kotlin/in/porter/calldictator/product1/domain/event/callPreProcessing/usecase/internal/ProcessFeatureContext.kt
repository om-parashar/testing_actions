package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.UserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper.CallerResponseOutputContextMapper
import javax.inject.Inject

class ProcessFeatureContext
@Inject
constructor(
  private val voicebotContext: VoicebotContext,
  private val ivrContext: IvrContext,
  private val inboundContext: InboundContext,
  private val callerResponseOutputContextMapper: CallerResponseOutputContextMapper
){
  suspend fun invoke(userCallContext: UserCallContext): CallerResponseOutputContext? {
    var voicebotResponse = voicebotContext.fetch(userCallContext)
    if(voicebotResponse != null) {
      return callerResponseOutputContextMapper.fromVoicebot(voicebotResponse)
    }

    val ivrResponse = ivrContext.fetch(userCallContext)
    if(ivrResponse != null) {
      return callerResponseOutputContextMapper.fromIvr(ivrResponse)
    }

    val inboundResponse = inboundContext.fetch(userCallContext)
    if(inboundResponse != null) {
      return callerResponseOutputContextMapper.fromInbound(inboundResponse)
    }

    return null
  }
}
