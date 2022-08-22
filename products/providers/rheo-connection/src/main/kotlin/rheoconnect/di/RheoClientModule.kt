package rheoconnect.di

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.di.ProviderNames
import `in`.porter.support.domain.di.ProviderNames as SupportProviderNames
import dagger.Binds
import dagger.Module
import `in`.porter.support.domain.clients.SupportContactProviderClient
import rheoconnect.usecases.external.RheoClient
import javax.inject.Named

@Module
abstract class RheoClientModule {

  @Binds
  @Named(ProviderNames.RHEO_CONNECTION)
  abstract fun provideRheoClient(request: RheoClient): FeatureContextProviderClient

  @Binds
  @Named(SupportProviderNames.RHEO_CONNECTION)
  abstract fun provideSupportRheoClient(request: RheoClient): SupportContactProviderClient
}
