package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.di.ProviderNames
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.UserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.Constants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoResponseContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.helper.FeatureRequestGenerator
import javax.inject.Inject
import javax.inject.Named

class VoicebotContext
@Inject
constructor(
  @Named(ProviderNames.RHEO_CONNECTION)
  private val featureContextProviderClient: FeatureContextProviderClient,
  private val featureRequestGenerator: FeatureRequestGenerator
){

  suspend fun fetch(userCallContext: UserCallContext) {
    val context = featureRequestGenerator.invoke(userCallContext, Constants.VOICEBOT_FLAG)
    val response = featureContextProviderClient.fetchFeatureContext(context)
    processOutput(response)
  }

  private fun processOutput(rheoResponseContext: RheoResponseContext) {
    print("rheo response $rheoResponseContext")
  }
}
