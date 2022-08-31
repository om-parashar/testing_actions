package `in`.porter.calldictator.data.conversation.tables.call.records

import java.time.Instant

data class CallRecordData(
  val phone: String,
  val did: String,
  val customerCRTId: String,
  val timestampCreated: Instant,
  val timestampUpdated: Instant
)
