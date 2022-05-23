package rheoconnect.di

import `in`.porter.calldictator.domain.clients.ContextProviderClient
import `in`.porter.calldictator.domain.di.ProviderNames
import dagger.Component
import io.ktor.client.*
import javax.inject.Named

@Component(
  modules = [RheoClientModule::class]
)
interface RheoClientComponent {

  @Named(ProviderNames.RHEO_CONNECTION)
  fun rheoClient(): ContextProviderClient

  interface Builder {

    fun httpClient(client: HttpClient): Builder
  }
}