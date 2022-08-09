package rheoconnect.di

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.di.ProviderNames
import dagger.Binds
import dagger.Module
import rheoconnect.usecases.external.RheoClient
import javax.inject.Named

@Module
abstract class RheoClientModule {

  @Binds
  @Named(ProviderNames.RHEO_CONNECTION)
  abstract fun provideRheoClient(request: RheoClient): FeatureContextProviderClient
}
