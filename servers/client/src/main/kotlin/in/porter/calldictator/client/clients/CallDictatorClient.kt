package `in`.porter.calldictator.client.clients

import `in`.porter.calldictator.api.models.async.AsyncJob


interface CallDictatorClient {
  suspend fun publishJob(job: AsyncJob)
}
