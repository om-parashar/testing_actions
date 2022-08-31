package `in`.porter.calldictator.data.conversation.tables.callercontext.records

import com.fasterxml.jackson.databind.JsonNode
import java.time.Instant

data class CallContextRecordData(
  val callId: Int,
  val context: JsonNode,
  val timestampCreated: Instant,
  val timestampUpdated: Instant
)