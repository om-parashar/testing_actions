package rheoconnect.usecases.external

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoRequestContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoResponseContext
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import rheoconnect.usecases.internal.mapper.MapRheoResponse
import rheoconnect.usecases.internal.FetchFromRheo
import rheoconnect.usecases.internal.GenerateRheoInput
import javax.inject.Inject

class RheoClient
@Inject
constructor(
  private val fetchFromRheo: FetchFromRheo,
  private val mapRheoResponse: MapRheoResponse,
  private val generateRheoInput: GenerateRheoInput
): FeatureContextProviderClient, Traceable {

  override suspend fun fetchFeatureContext(request: RheoRequestContext): RheoResponseContext{
    val rheoInput = generateRheoInput.execute(request)
    val feature = fetchFromRheo.fetchFeature(rheoInput)
    return mapRheoResponse.invoke(feature)
    /*return request
      .let { generateRheoInput.execute(it) }
      .let { fetchFromRheo.fetchFeature(it) }
      .let { mapRheoResponse.invoke(it) }*/
  }
}
