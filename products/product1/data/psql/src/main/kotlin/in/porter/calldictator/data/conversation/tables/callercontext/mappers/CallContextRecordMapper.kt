package `in`.porter.calldictator.data.conversation.tables.callercontext.mappers

import `in`.porter.calldictator.data.conversation.tables.callercontext.records.CallContextRecordData
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallerContextDraft
import java.time.Instant
import javax.inject.Inject

class CallContextRecordMapper
@Inject
constructor(){

  fun fromDraft(draft: CallerContextDraft): CallContextRecordData =
    CallContextRecordData(
      callId = draft.callId,
      context = draft.context,
      timestampCreated = Instant.now(),
      timestampUpdated = Instant.now()
    )
}
