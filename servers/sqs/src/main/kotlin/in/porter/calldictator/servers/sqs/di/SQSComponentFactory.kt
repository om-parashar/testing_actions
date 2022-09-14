package `in`.porter.calldictator.servers.sqs.di

import `in`.porter.calldictator.servers.commons.di.factories.ComponentsFactory

object SQSComponentFactory {

  fun build(): SQSComponent =
    DaggerSQSComponent.builder()
      .rootComponent(ComponentsFactory.rootComponent)
      .psqlDataComponent(ComponentsFactory.psqlDataComponent)
      .build()
}
