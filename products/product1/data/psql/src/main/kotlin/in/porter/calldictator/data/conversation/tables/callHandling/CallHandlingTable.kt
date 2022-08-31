package `in`.porter.calldictator.data.conversation.tables.callHandling

import `in`.porter.calldictator.data.conversation.tables.callercontext.CallerContextTable
import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import `in`.porter.kotlinutils.exposed.columns.json.jsonb
import `in`.porter.kotlinutils.serde.jackson.json.JsonMapper
import com.fasterxml.jackson.databind.JsonNode
import org.jetbrains.exposed.dao.id.IntIdTable

object CallHandlingTable: IntIdTable("conversation_call_handling") {

  val callId = integer("call_id")
  val responseType = varchar("response_type", 256)
  val responseValue = varchar("response_value", 256)
  val context = jsonb("context", JsonNode::class.java, JsonMapper)
  val timestampCreated = timestampWithoutTZAsInstant("timestamp_created")
  val timestampUpdated = timestampWithoutTZAsInstant("timestamp_updated")
}
