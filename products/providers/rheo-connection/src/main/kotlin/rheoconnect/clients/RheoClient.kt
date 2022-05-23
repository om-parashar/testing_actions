package rheoconnect.clients

import `in`.porter.calldictator.domain.clients.ContextProviderClient
import `in`.porter.calldictator.domain.event.entities.RheoRequestContext
import `in`.porter.kotlinutils.instrumentation.opentracing.Traceable
import javax.inject.Inject

class RheoClient
@Inject
constructor(): ContextProviderClient, Traceable {

  override suspend fun fetchFeatureContext(request: RheoRequestContext) = trace {
    TODO("Not yet implemented")
  }
}