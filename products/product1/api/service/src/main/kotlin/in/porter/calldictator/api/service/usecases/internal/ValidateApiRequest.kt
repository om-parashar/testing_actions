package `in`.porter.calldictator.api.service.usecases.internal

import `in`.porter.calldictator.api.models.entities.CallDictateRequest
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import javax.inject.Inject

class ValidateApiRequest
@Inject
constructor(){

  fun invoke(request: CallDictateRequest): Boolean {
    if(request.did.isNullOrEmpty() || request.phone.isNullOrEmpty() || request.customerCRTId.isNullOrEmpty() || request.vertical.isNullOrEmpty()) {
      logger.error("request param doesn't have value.")
      return false
    }
    logger.debug("request: $request passed validation.")
    return true
  }
}
