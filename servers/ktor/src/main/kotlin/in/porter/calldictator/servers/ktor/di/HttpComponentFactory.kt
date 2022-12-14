package `in`.porter.calldictator.servers.ktor.di

import `in`.porter.calldictator.servers.commons.di.factories.ComponentsFactory

object HttpComponentFactory {

  fun build(): HttpComponent =
    DaggerHttpComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .build()

}
