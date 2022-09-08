package `in`.porter.calldictator.data.conversation.tables.callHandling.mappers

import `in`.porter.calldictator.data.conversation.tables.callHandling.records.CallHandlingRecordData
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallHandlingDraft
import java.time.Instant
import javax.inject.Inject

class CallHandlingRecordMapper
@Inject
constructor(){

  fun fromDraft(draft: CallHandlingDraft): CallHandlingRecordData =
    CallHandlingRecordData(
      callId = draft.callId,
      type = draft.type,
      value = draft.value,
      context = draft.context,
      timestampCreated = Instant.now(),
      timestampUpdated = Instant.now()
    )
}
