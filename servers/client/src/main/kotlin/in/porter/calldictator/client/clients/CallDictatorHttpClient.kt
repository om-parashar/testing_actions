package `in`.porter.calldictator.client.clients

import `in`.porter.kotlinutils.sqs.coroutines.client.SQSClient
import `in`.porter.calldictator.api.models.async.AsyncJob
import javax.inject.Inject

class CallDictatorHttpClient
@Inject
constructor(
  private val client: SQSClient
) : CallDictatorClient {

  override suspend fun publishJob(job: AsyncJob) {
    TODO()
  }

}
