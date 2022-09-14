package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.Call
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

  suspend fun persistCallData(request: CallerContext): Call {
    return conversationRepo.createCallRecord(repoMapper.toCallDraft(request))
  }

  suspend fun persistCallProcessedData(callerResponse: CallerResponse, call:Call) {
    conversationRepo.createCallContextRecord(repoMapper.toCallContextDraft(callerResponse, call.id))
    conversationRepo.createCallHandlingRecord(repoMapper.toCallHandlingDraft(callerResponse, call.id))
  }
}
