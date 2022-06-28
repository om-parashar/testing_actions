package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.UserCallContext
import java.util.*

data class CallerResponse(
  val responseTS: Date,
  val customerCRTId: String,
  val callContext: UserCallContext,
  val callerResponseOutputContext: CallerResponseOutputContext?
)
