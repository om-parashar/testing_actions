package `in`.porter.calldictator.providers.omsconnect.di

import `in`.porter.calldictator.product1.domain.clients.CallContextProviderClient
import `in`.porter.calldictator.product1.domain.di.ProviderNames
import `in`.porter.calldictator.providers.omsconnect.usecases.external.OmsClient
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class OmsClientModule {

  @Binds
  @Named(ProviderNames.OMS_CONNECTION)
  abstract fun provideOMSClient(request: OmsClient): CallContextProviderClient
}
