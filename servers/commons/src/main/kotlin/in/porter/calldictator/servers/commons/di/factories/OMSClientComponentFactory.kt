package `in`.porter.calldictator.servers.commons.di.factories

import `in`.porter.calldictator.providers.omsconnect.di.DaggerOMSClientComponent
import `in`.porter.calldictator.providers.omsconnect.di.OMSClientComponent
import `in`.porter.calldictator.servers.commons.di.components.RootComponent

object OMSClientComponentFactory {

  fun build(rootComponent: RootComponent): OMSClientComponent =
    DaggerOMSClientComponent.builder()
      .httpClient(rootComponent.httpClient)
      .mapper(rootComponent.serdeMapper)
      .omsConfig(rootComponent.omsConfig)
      .build()
}
