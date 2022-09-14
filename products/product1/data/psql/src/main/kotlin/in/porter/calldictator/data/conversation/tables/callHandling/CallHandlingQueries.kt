package `in`.porter.calldictator.data.conversation.tables.callHandling

import `in`.porter.calldictator.data.conversation.tables.callHandling.records.CallHandlingRecord
import `in`.porter.calldictator.data.conversation.tables.callHandling.records.CallHandlingRecordData
import `in`.porter.kotlinutils.exposed.ExposedRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insertAndGetId
import javax.inject.Inject

class CallHandlingQueries
@Inject
constructor(
  override val db: Database,
  override val dispatcher: CoroutineDispatcher
): ExposedRepo {

  suspend fun create(data: CallHandlingRecordData): CallHandlingRecord =
    transact {
      val id = CallHandlingTable.insertAndGetId {
        it[callId] = data.callId
        it[responseType] = data.type
        it[responseValue] = data.value
        it[context] = data.context
        it[timestampCreated] = data.timestampCreated
        it[timestampUpdated] = data.timestampUpdated
      }.value
      CallHandlingRecord(id, data)
    }
}
