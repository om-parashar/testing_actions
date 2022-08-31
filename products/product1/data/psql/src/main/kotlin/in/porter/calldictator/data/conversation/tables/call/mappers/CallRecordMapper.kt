package `in`.porter.calldictator.data.conversation.tables.call.mappers

import `in`.porter.calldictator.data.conversation.tables.call.records.CallRecord
import `in`.porter.calldictator.data.conversation.tables.call.records.CallRecordData
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.Call
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create.CallDraft
import java.time.Instant
import javax.inject.Inject

class CallRecordMapper
@Inject
constructor(){

  fun fromDraft(draft: CallDraft): CallRecordData =
    CallRecordData(
      phone = draft.phone,
      did = draft.did,
      customerCRTId = draft.customerCRTId,
      timestampCreated = Instant.now(),
      timestampUpdated = Instant.now()
    )

  fun fromRecord(record: CallRecord): Call =
    Call(
      id = record.id,
      phone = record.data.phone,
      did = record.data.did,
      customerCRTId = record.data.customerCRTId,
      timestampCreated = record.data.timestampCreated,
      timestampUpdated = record.data.timestampUpdated
    )
}
