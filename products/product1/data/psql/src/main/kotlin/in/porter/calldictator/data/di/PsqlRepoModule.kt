package `in`.porter.calldictator.data.di

import `in`.porter.calldictator.data.conversation.repos.PsqlConversationRepo
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.repo.ConversationRepo
import dagger.Binds
import dagger.Module

@Module
abstract class PsqlRepoModule {

  @Binds
  @PsqlDataScope
  abstract fun providePsqlConversationRepo(request: PsqlConversationRepo): ConversationRepo
}
