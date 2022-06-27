package `in`.porter.calldictator.product1.domain.clients

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoRequestContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoResponseContext

interface FeatureContextProviderClient {
  suspend fun fetchFeatureContext(request: RheoRequestContext): RheoResponseContext
}
