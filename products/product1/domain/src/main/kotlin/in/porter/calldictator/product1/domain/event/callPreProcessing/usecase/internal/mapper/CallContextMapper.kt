package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.*
import javax.inject.Inject

class CallContextMapper
@Inject
constructor(){

  fun mapDriverContextToUserContext(callerContext: CallerContext, driverCallContext: DriverCallContext, orderContext: OrderContext?) = UserCallContext (
    userType = Constants.USER_TYPE_DRIVER,
    did = callerContext.did,
    mobile = callerContext.phone,
    city = driverCallContext.city,
    id = driverCallContext.id,
    uuid = driverCallContext.uuid,
    language = driverCallContext.language,
    loginStatus = driverCallContext.loginStatus,
    vehicleInfo = driverCallContext.vehicleInfo,
    isSuspended = driverCallContext.isSuspended,
    suspensionReason = driverCallContext.suspensionReason,
    order = orderContext
    )

  fun mapCustomerContextToUserContext(callerContext: CallerContext, language: String, customerCallContext: CustomerCallContext, orderContext: OrderContext?) = UserCallContext (
    userType = Constants.USER_TYPE_CUSTOMER,
    did = callerContext.did,
    mobile = callerContext.phone,
    city = customerCallContext.city,
    id = customerCallContext.id,
    uuid = customerCallContext.uuid,
    language = language,
    loginStatus = null,
    vehicleInfo = null,
    isSuspended = null,
    suspensionReason = null,
    order = orderContext
  )

  fun mapUnknownContextToUserContext(callerContext: CallerContext, city: String, language: String) = UserCallContext (
    userType = Constants.USER_TYPE_UNKNOWN,
    did = callerContext.did,
    mobile = callerContext.phone,
    city = city,
    id = null,
    uuid = null,
    language = language,
    loginStatus = null,
    vehicleInfo = null,
    isSuspended = null,
    suspensionReason = null,
    order = null
    )
}
