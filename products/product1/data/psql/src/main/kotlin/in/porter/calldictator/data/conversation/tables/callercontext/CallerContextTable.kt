package `in`.porter.calldictator.data.conversation.tables.callercontext

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import `in`.porter.kotlinutils.exposed.columns.json.jsonb
import `in`.porter.kotlinutils.serde.jackson.json.JsonMapper
import com.fasterxml.jackson.databind.JsonNode
import org.jetbrains.exposed.dao.id.IntIdTable

object CallerContextTable : IntIdTable("conversation_caller_context") {

  val callId = integer("call_id")
  val context = jsonb("context", JsonNode::class.java, JsonMapper)
  val timestampCreated = timestampWithoutTZAsInstant("timestamp_created")
  val timestampUpdated = timestampWithoutTZAsInstant("timestamp_updated")
}