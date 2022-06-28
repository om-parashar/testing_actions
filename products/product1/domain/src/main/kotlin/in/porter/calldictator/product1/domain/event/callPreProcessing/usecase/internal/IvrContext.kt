package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.di.ProviderNames
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.UserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.Constants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.IvrResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoResponseContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.helper.FeatureRequestGenerator
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import javax.inject.Inject
import javax.inject.Named

class IvrContext
@Inject
constructor(
  @Named(ProviderNames.RHEO_CONNECTION)
  private val featureContextProviderClient: FeatureContextProviderClient,
  private val featureRequestGenerator: FeatureRequestGenerator
) {
  suspend fun fetch(userCallContext: UserCallContext): IvrResponseOutputContext? {
    val context = featureRequestGenerator.invoke(userCallContext, Constants.IVR_FLAG)
    val response = featureContextProviderClient.fetchFeatureContext(context)
    return processOutput(response)
  }

  private fun processOutput(rheoResponseContext: RheoResponseContext): IvrResponseOutputContext? {
    logger.info("voicebot response: $rheoResponseContext")
    return if(!rheoResponseContext.status) null
    else IvrResponseOutputContext(
      ivr = rheoResponseContext.result["key"],
      channel = rheoResponseContext.result["channel"],
      skillName = rheoResponseContext.result["skill"],
      queueName = rheoResponseContext.result["queue"]
    )
  }
}
