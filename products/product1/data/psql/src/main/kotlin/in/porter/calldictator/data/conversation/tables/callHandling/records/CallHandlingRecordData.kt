package `in`.porter.calldictator.data.conversation.tables.callHandling.records

import com.fasterxml.jackson.databind.JsonNode
import java.time.Instant

data class CallHandlingRecordData(
  val callId: Int,
  val type: String,
  val value: String,
  val context: JsonNode,
  val timestampCreated: Instant,
  val timestampUpdated: Instant
)