package `in`.porter.calldictator.servers.sqs.di

import `in`.porter.kotlinutils.sqs.coroutines.drain.stream.StreamQueueDrainer
import `in`.porter.calldictator.api.models.async.AsyncJob
import `in`.porter.calldictator.data.di.PsqlDataComponent
import `in`.porter.calldictator.servers.commons.di.components.RootComponent
import `in`.porter.calldictator.servers.commons.usecases.external.Run
import dagger.Component

@SQSScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ],
  modules = [
    SQSModule::class
  ]
)
interface SQSComponent {
  val run: Run
  val drainer: StreamQueueDrainer<AsyncJob>
}
