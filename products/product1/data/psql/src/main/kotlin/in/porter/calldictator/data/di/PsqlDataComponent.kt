package `in`.porter.calldictator.data.di

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.repo.ConversationRepo
import dagger.BindsInstance
import dagger.Component
import io.micrometer.core.instrument.MeterRegistry
import org.jetbrains.exposed.sql.Database

@PsqlDataScope
@Component(
  modules =
  [
    UtilsModule::class,
    PsqlRepoModule::class
  ]
)
interface PsqlDataComponent {

  val conversationRepo: ConversationRepo

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun database(db: Database): Builder

    @BindsInstance
    fun meterRegistry(meterRegistry: MeterRegistry): Builder

    fun build(): PsqlDataComponent
  }

}
