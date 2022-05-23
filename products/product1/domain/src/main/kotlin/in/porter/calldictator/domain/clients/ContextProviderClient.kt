package `in`.porter.calldictator.domain.clients

import `in`.porter.calldictator.domain.event.entities.RheoRequestContext

interface ContextProviderClient {
  suspend fun fetchFeatureContext(request: RheoRequestContext)
}
