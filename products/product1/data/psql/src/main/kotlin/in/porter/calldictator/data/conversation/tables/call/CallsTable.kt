package `in`.porter.calldictator.data.conversation.tables.call

import `in`.porter.kotlinutils.exposed.columns.datetime.timestampWithoutTZAsInstant
import org.jetbrains.exposed.dao.id.IntIdTable

object CallsTable : IntIdTable("conversation_calls") {

  val phone = varchar("phone", 256)
  val did = varchar("did", 256)
  val crtId = varchar("crt_id", 256)
  val timestampCreated = timestampWithoutTZAsInstant("timestamp_created")
  val timestampUpdated = timestampWithoutTZAsInstant("timestamp_updated")
}
