package `in`.porter.calldictator.servers.commons.di.factories

import `in`.porter.calldictator.servers.commons.di.components.RootComponent
import rheoconnect.di.DaggerRheoClientComponent
import rheoconnect.di.RheoClientComponent

object RheoClientComponentFactory {

  fun build(rootComponent: RootComponent): RheoClientComponent =
    DaggerRheoClientComponent.builder()
      .httpClient(rootComponent.httpClient)
      .mapper(rootComponent.serdeMapper)
      .build()
}
