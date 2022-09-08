package `in`.porter.calldictator.data.conversation.tables.callercontext

import `in`.porter.calldictator.data.conversation.tables.callercontext.records.CallContextRecord
import `in`.porter.calldictator.data.conversation.tables.callercontext.records.CallContextRecordData
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import javax.inject.Inject

class CallerContextQueries
@Inject
constructor(
  override val db: Database,
  override val dispatcher: CoroutineDispatcher
): ExposedRepo {

  suspend fun create(data: CallContextRecordData): CallContextRecord =
    transact {
      val id = CallerContextTable.insertAndGetId {
        it[callId] = data.callId
        it[context] = data.context
        it[timestampCreated] = data.timestampCreated
        it[timestampUpdated] = data.timestampUpdated
      }.value
      CallContextRecord(id, data)
    }
}
