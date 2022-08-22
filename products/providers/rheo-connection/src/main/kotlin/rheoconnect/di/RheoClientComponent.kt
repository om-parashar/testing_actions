package rheoconnect.di

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.di.ProviderNames
import `in`.porter.support.domain.di.ProviderNames as SupportProviderNames
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import dagger.BindsInstance
import dagger.Component
import `in`.porter.support.domain.clients.SupportContactProviderClient
import io.ktor.client.*
import rheoconnect.entities.RheoConfig
import javax.inject.Named

@Component(
  modules = [RheoClientModule::class]
)
interface RheoClientComponent {

  @Named(ProviderNames.RHEO_CONNECTION)
  fun rheoClient(): FeatureContextProviderClient

  @Named(SupportProviderNames.RHEO_CONNECTION)
  fun rheoSupportClient(): SupportContactProviderClient

  @Component.Builder
  interface Builder {

    fun build(): RheoClientComponent

    @BindsInstance
    fun httpClient(client: HttpClient): Builder

    @BindsInstance
    fun mapper(mapper: SerdeMapper): Builder

    @BindsInstance
    fun rheoConfig(rheoConfig: RheoConfig): Builder
  }
}
