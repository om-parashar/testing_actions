package `in`.porter.calldictator.client.di

import `in`.porter.calldictator.client.clients.CallDictatorHttpClient
import `in`.porter.calldictator.client.clients.CallDictatorClient
import dagger.Binds
import dagger.Module

@Module
internal abstract class ClientModule {

  @Binds
  abstract fun provideAsyncJobsClient(client: CallDictatorHttpClient): CallDictatorClient
}
