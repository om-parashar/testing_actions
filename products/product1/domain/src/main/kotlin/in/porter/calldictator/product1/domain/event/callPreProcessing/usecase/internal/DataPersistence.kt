package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponse
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.repo.ConversationRepo
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper.RepoMapper
import javax.inject.Inject

class DataPersistence
@Inject
constructor(
  private val repoMapper: RepoMapper,
  private val conversationRepo: ConversationRepo
) {

  suspend fun invoke(callerResponse: CallerResponse) {

    val call = conversationRepo.createCallRecord(repoMapper.toCallDraft(callerResponse))
    conversationRepo.createCallContextRecord(repoMapper.toCallContextDraft(callerResponse, call.id))
    conversationRepo.createCallHandlingRecord(repoMapper.toCallHandlingDraft(callerResponse, call.id))
  }
}
