package `in`.porter.support.domain.clients

import `in`.porter.support.domain.entities.rheo.RheoMapResponseContext
import `in`.porter.support.domain.entities.rheo.RheoRequestContext
import `in`.porter.support.domain.entities.rheo.RheoStringResponseContext


interface SupportContactProviderClient {
    suspend fun getSupportNumberContext(request: RheoRequestContext): RheoMapResponseContext
    suspend fun getSupportEmailContext(request: RheoRequestContext): RheoStringResponseContext
}
