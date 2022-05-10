package `in`.porter.calldictator.servers.commons.di.factories

import `in`.porter.calldictator.data.di.DaggerPsqlDataComponent
import `in`.porter.calldictator.servers.commons.di.components.RootComponent
import `in`.porter.calldictator.data.di.PsqlDataComponent

object PsqlDataComponentFactory {

  fun build(rootComponent: RootComponent): PsqlDataComponent =
    DaggerPsqlDataComponent.builder()
      .database(rootComponent.database)
      .meterRegistry(rootComponent.meterRegistry)
      .build()

}
