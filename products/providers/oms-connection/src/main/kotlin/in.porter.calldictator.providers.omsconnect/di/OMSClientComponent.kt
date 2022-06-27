package `in`.porter.calldictator.providers.omsconnect.di

import `in`.porter.calldictator.product1.domain.clients.CallContextProviderClient
import `in`.porter.calldictator.product1.domain.di.ProviderNames
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import dagger.BindsInstance
import dagger.Component
import io.ktor.client.*
import javax.inject.Named

@Component(modules = [OmsClientModule::class])
interface OMSClientComponent {

  @Named(ProviderNames.OMS_CONNECTION)
  fun provideOMSClient(): CallContextProviderClient

  @Component.Builder
  interface Builder {

    fun build(): OMSClientComponent

    @BindsInstance
    fun httpClient(client: HttpClient): Builder

    @BindsInstance
    fun mapper(mapper: SerdeMapper): Builder
  }
}
