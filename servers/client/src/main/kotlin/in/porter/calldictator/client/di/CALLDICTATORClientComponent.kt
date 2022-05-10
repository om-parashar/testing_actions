package `in`.porter.calldictator.client.di

import `in`.porter.calldictator.client.clients.CallDictatorClient
import `in`.porter.calldictator.client.config.ClientConfig
import dagger.BindsInstance
import dagger.Component

@ClientScope
@Component(
  modules = [
    ClientModule::class,
    UtilsModule::class
  ]
)
interface CALLDICTATORClientComponent {
  val calldictatorClient: CallDictatorClient

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun config(config: ClientConfig): Builder

    fun build(): CALLDICTATORClientComponent
  }
}
