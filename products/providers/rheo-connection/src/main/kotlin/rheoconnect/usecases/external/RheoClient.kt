package rheoconnect.usecases.external

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoRequestContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoResponseContext
import `in`.porter.support.domain.entities.rheo.RheoRequestContext as SupportRheoRequestContext
import rheoconnect.usecases.internal.mapper.MapSupportEmailRheoResponse
import rheoconnect.usecases.internal.mapper.MapSupportNumberRheoResponse
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import `in`.porter.support.domain.clients.SupportContactProviderClient
import `in`.porter.support.domain.entities.rheo.RheoMapResponseContext
import `in`.porter.support.domain.entities.rheo.RheoStringResponseContext
import rheoconnect.usecases.internal.mapper.MapRheoResponse
import rheoconnect.usecases.internal.FetchFromRheo
import rheoconnect.usecases.internal.GenerateRheoInput
import javax.inject.Inject

class RheoClient
@Inject
constructor(
  private val fetchFromRheo: FetchFromRheo,
  private val mapRheoResponse: MapRheoResponse,
  private val mapSupportEmailRheoResponse: MapSupportEmailRheoResponse,
  private val mapSupportNumberRheoResponse: MapSupportNumberRheoResponse,
  private val generateRheoInput: GenerateRheoInput
): FeatureContextProviderClient, SupportContactProviderClient, Traceable {

  override suspend fun fetchFeatureContext(request: RheoRequestContext): RheoResponseContext{
    val rheoInput = generateRheoInput.execute(request)
    val feature = fetchFromRheo.fetchFeature(rheoInput)
    return mapRheoResponse.invoke(feature)
    /*return request
      .let { generateRheoInput.execute(it) }
      .let { fetchFromRheo.fetchFeature(it) }
      .let { mapRheoResponse.invoke(it) }*/
  }

  override suspend fun getSupportNumberContext(request: SupportRheoRequestContext): RheoMapResponseContext {
    val rheoInput = generateRheoInput.execute(request)
    val feature = fetchFromRheo.fetchFeature(rheoInput)
    return mapSupportNumberRheoResponse.invoke(feature)
  }

  override suspend fun getSupportEmailContext(request: SupportRheoRequestContext): RheoStringResponseContext {
    val rheoInput = generateRheoInput.execute(request)
    val feature = fetchFromRheo.fetchFeature(rheoInput)
    return mapSupportEmailRheoResponse.invoke(feature)
  }
}