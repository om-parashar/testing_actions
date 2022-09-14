package `in`.porter.calldictator.data.conversation.tables.call

import `in`.porter.calldictator.data.conversation.tables.call.records.CallRecord
import `in`.porter.calldictator.data.conversation.tables.call.records.CallRecordData
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import javax.inject.Inject

class CallsQueries
@Inject
constructor(
  override val db: Database,
  override val dispatcher: CoroutineDispatcher
): ExposedRepo {

  suspend fun create(data: CallRecordData): CallRecord =
    transact {
      val id = CallsTable.insertAndGetId {
        it[phone] = data.phone
        it[did] = data.did
        it[crtId] = data.customerCRTId
        it[timestampCreated] = data.timestampCreated
        it[timestampUpdated] = data.timestampUpdated
      }.value
      CallRecord(id, data)
    }
}
