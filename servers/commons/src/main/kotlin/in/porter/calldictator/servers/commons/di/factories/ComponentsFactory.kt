package `in`.porter.calldictator.servers.commons.di.factories

import  `in`.porter.calldictator.servers.commons.di.components.DaggerRootComponent

object ComponentsFactory {

  val rootComponent = DaggerRootComponent.create()
  val psqlDataComponent = PsqlDataComponentFactory.build(rootComponent)
  val omsClientComponent = OMSClientComponentFactory.build(rootComponent)
  val rheoClientComponent = RheoClientComponentFactory.build(rootComponent)

}
