package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.UserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponse
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.CallerResponseOutputContext
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import java.util.*
import javax.inject.Inject

class CallerResponseMapper
@Inject
constructor(){

  fun generate(request: CallerContext, userCallContext: UserCallContext, callerResponseOutputContext: CallerResponseOutputContext?): CallerResponse {
    logger.info("parsed request: $request \nfetched user context: $userCallContext \nprocessed caller response: $callerResponseOutputContext")

    return CallerResponse(
      responseTS = Calendar.getInstance().time,
      customerCRTId = request.customerCRTId,
      callContext = userCallContext,
      callerResponseOutputContext = callerResponseOutputContext
    )
  }
}
