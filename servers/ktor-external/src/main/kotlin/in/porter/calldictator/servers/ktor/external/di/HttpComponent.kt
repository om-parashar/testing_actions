package `in`.porter.calldictator.servers.ktor.external.di

import `in`.porter.calldictator.servers.commons.di.components.RootComponent
import `in`.porter.calldictator.servers.commons.usecases.external.Run
import `in`.porter.calldictator.data.di.PsqlDataComponent
import `in`.porter.calldictator.providers.omsconnect.di.OMSClientComponent
import `in`.porter.calldictator.servers.ktor.di.HttpScope
import `in`.porter.calldictator.servers.ktor.external.callDictator.usecase.GetCallDictationHttpService

import dagger.Component
import `in`.porter.calldictator.servers.ktor.external.support.usecase.GetSupportContactHttpService
import rheoconnect.di.RheoClientComponent

@HttpScope
@Component(
  dependencies = [
    RootComponent::class,
    PsqlDataComponent::class,
    OMSClientComponent::class,
    RheoClientComponent::class
  ]
)
interface HttpComponent {
  val run: Run
  val getCallDictationHttpService: GetCallDictationHttpService
  val getSupportContactHttpService: GetSupportContactHttpService
}
