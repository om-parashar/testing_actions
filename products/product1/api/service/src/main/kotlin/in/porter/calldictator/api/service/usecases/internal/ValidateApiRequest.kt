package `in`.porter.calldictator.api.service.usecases.internal

import `in`.porter.calldictator.api.models.entities.CallDictateRequest
import javax.inject.Inject

class ValidateApiRequest
@Inject
constructor(){

  suspend fun invoke(request: CallDictateRequest): Boolean {
    // TODO: 16/06/22 logic to validate the api request

    return true
  }
}