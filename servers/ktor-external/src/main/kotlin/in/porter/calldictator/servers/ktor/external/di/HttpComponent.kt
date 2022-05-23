package `in`.porter.calldictator.servers.ktor.external.di

import `in`.porter.calldictator.servers.commons.di.components.RootComponent
import `in`.porter.calldictator.servers.commons.usecases.external.Run
import `in`.porter.calldictator.data.di.PsqlDataComponent
import `in`.porter.calldictator.servers.ktor.di.HttpScope
import `in`.porter.calldictator.servers.ktor.external.callDictator.usecase.GetCallDictationHttpService

import dagger.Component

@HttpScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class
  ]
)
interface HttpComponent {
  val run: Run
  val getCallDictationHttpService: GetCallDictationHttpService
}
