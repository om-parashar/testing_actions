package `in`.porter.calldictator.product1.domain.event.callPreProcessing.repo

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.Call
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallDraft
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallHandlingDraft
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallerContextDraft

interface ConversationRepo {

  suspend fun createCallRecord(draft: CallDraft): Call
  suspend fun createCallContextRecord(draft: CallerContextDraft)
  suspend fun createCallHandlingRecord(draft: CallHandlingDraft)
}
