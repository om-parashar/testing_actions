package `in`.porter.calldictator.servers.sqs.consumers

import `in`.porter.calldictator.api.models.async.AsyncJob
import javax.inject.Inject

class AsyncJobService
@Inject
constructor(
) {

  suspend fun invoke(job: AsyncJob) {
     TODO()
  }
}
