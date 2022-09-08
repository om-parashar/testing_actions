package `in`.porter.calldictator.data.conversation.repos

import `in`.porter.calldictator.data.conversation.tables.call.CallsQueries
import `in`.porter.calldictator.data.conversation.tables.call.mappers.CallRecordMapper
import `in`.porter.calldictator.data.conversation.tables.callHandling.CallHandlingQueries
import `in`.porter.calldictator.data.conversation.tables.callHandling.mappers.CallHandlingRecordMapper
import `in`.porter.calldictator.data.conversation.tables.callercontext.CallerContextQueries
import `in`.porter.calldictator.data.conversation.tables.callercontext.mappers.CallContextRecordMapper
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.Call
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallDraft
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallHandlingDraft
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallerContextDraft
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.repo.ConversationRepo
import javax.inject.Inject

class PsqlConversationRepo
@Inject
constructor(
  private val callRecordMapper: CallRecordMapper,
  private val callQueries: CallsQueries,
  private val callerContextQueries: CallerContextQueries,
  private val callerContextMapper: CallContextRecordMapper,
  private val callHandlingQueries: CallHandlingQueries,
  private val callHandlingMapper: CallHandlingRecordMapper
): ConversationRepo {

  override suspend fun createCallRecord(draft: CallDraft): Call {
    return callRecordMapper.fromDraft(draft)
      .let { callQueries.create(it) }
      .let { callRecordMapper.fromRecord(it) }
  }

  override suspend fun createCallContextRecord(draft: CallerContextDraft) {
    callerContextMapper.fromDraft(draft)
      .let { callerContextQueries.create(it) }
  }

  override suspend fun createCallHandlingRecord(draft: CallHandlingDraft) {
    callHandlingMapper.fromDraft(draft)
      .let { callHandlingQueries.create(it) }
  }
}
