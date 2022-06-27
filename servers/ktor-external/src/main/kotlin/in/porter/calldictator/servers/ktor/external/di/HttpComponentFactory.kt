package `in`.porter.calldictator.servers.ktor.external.di

import `in`.porter.calldictator.servers.commons.di.factories.ComponentsFactory

object HttpComponentFactory {

  fun build(): HttpComponent =
    DaggerHttpComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .oMSClientComponent(ComponentsFactory.omsClientComponent)
      .build()

}
