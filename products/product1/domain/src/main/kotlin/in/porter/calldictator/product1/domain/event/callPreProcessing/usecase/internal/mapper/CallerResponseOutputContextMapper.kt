package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.*
import javax.inject.Inject

class CallerResponseOutputContextMapper
@Inject
constructor(){

  fun fromVoicebot(voicebotResponseOutputContext: VoicebotResponseOutputContext)= CallerResponseOutputContext (
    ivr = voicebotResponseOutputContext.ivr,
    channel = voicebotResponseOutputContext.channel,
    queueName = null,
    skillName = null
  )

  fun fromIvr(ivrResponse: IvrResponseOutputContext)= CallerResponseOutputContext (
    ivr = ivrResponse.ivr,
    channel = ivrResponse.channel,
    skillName = ivrResponse.skillName,
    queueName = ivrResponse.queueName
  )

  fun fromInbound(inboundResponse: InboundResponseOutputContext) = CallerResponseOutputContext(
    ivr = null,
    channel = Constants.INBOUND,
    skillName = inboundResponse.skillName,
    queueName = inboundResponse.queueName
  )
}
