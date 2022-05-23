package rheoconnect.di

import `in`.porter.calldictator.domain.clients.ContextProviderClient
import `in`.porter.calldictator.domain.di.ProviderNames
import dagger.Binds
import dagger.Module
import rheoconnect.clients.RheoClient
import javax.inject.Named

@Module
abstract class RheoClientModule {

  @Binds
  @Named(ProviderNames.RHEO_CONNECTION)
  abstract fun provideRheoClient(request: RheoClient): ContextProviderClient
}
