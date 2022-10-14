package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.*
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.Constants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.Call
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.*
import java.time.Instant
import java.util.*

object Generators {
    val TIMESTAMP = Instant.now()

    fun generateCallerContext(did: String = "1111", phone: String = "9999999999", customerCRTId: String = "test_id") = CallerContext(did, phone, customerCRTId)

    fun generateDriverContext(id: Int = 0, uuid: String = "test", city: String = "unknown", language: String = "english", loginStatus: String = "test", vehicleInfo: String = "test", isSuspended: Boolean = false, suspensionReason: String? = null) =
        DriverCallContext(id, uuid, city, language, loginStatus, vehicleInfo, isSuspended, suspensionReason)

    fun generateCustomerContext(id: Int = 0, uuid: String = "test", city: String = "unknown", language: String = "english", subCategory: String = "test") =
        CustomerCallContext(id, uuid, city, language, subCategory)

    fun generateOrderContext(id: Int = 0, status: String = "test", driverId: Int? = null, customerId: Int? = null, requestedVehicleName: String = "test", cancellationReason: String? = null, cancellationReasonSource: String? = null, cancellationReasonAttribution: String? = null, orderStagVicinity: String = "test", hasWaypoints: Boolean = false, labour: Boolean = false, labourHelper: Boolean = false, isHelperOrder: Boolean = false, isBusinessOrder: Boolean = false, outstation: Boolean = false, alternateNumber: String? = null) =
        OrderContext(id, status, driverId, customerId, requestedVehicleName, cancellationReason, cancellationReasonSource, cancellationReasonAttribution, orderStagVicinity, hasWaypoints, labour, labourHelper, isHelperOrder, isBusinessOrder, outstation, alternateNumber)

    fun generateCityContext(name: String = "unknown", language: String = "english") = CityCallContext(name, language)

    fun generateUserCallContext(did: String = "1111", mobile: String = "9999999999", userType: String = "unknown", id: Int? = null, uuid: String? = null, city: String = "unknown", language: String = "english", loginStatus: String? = null, vehicleInfo: String? = null, isSuspended: Boolean? = null, suspensionReason: String? = null, order: OrderContext? = null) =
        UserCallContext (did, mobile, userType, id, uuid, city, language, loginStatus, vehicleInfo, isSuspended, suspensionReason, order)

    fun generateUserCallContextFromDriver(callerContext: CallerContext, driverCallContext: DriverCallContext, orderContext: OrderContext? = null) =
        UserCallContext (
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

    fun generateUserCallContextFromCustomer(callContext: CallerContext, customerCallContext: CustomerCallContext, language: String = "english", orderContext: OrderContext? = null) =
        UserCallContext (
            userType = Constants.USER_TYPE_CUSTOMER,
            did = callContext.did,
            mobile = callContext.phone,
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

    fun generateUserCallContextFromUnknown(callerContext: CallerContext, city: String, language: String) = UserCallContext (
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

    fun generateCallerResponse(request: CallerContext, userCallContext: UserCallContext, callerResponseOutputContext: CallerResponseOutputContext, responseTS: Date = Date.from(TIMESTAMP)) = CallerResponse(
            responseTS = responseTS,
            customerCRTId = request.customerCRTId,
            callContext = userCallContext,
            callerResponseOutputContext = callerResponseOutputContext
        )

    fun generateVoicebotResponseOutputContext(ivr: String? = null, channel: String? = null) = VoicebotResponseOutputContext(ivr, channel)

    fun generateIvrResponseOutputContext(ivr: String? = null, channel: String? = null, skillName: String? = null, queueName: String? = null) =
        IvrResponseOutputContext(ivr, channel, skillName, queueName)

    fun generateInboundResponseOutputContext(ivr: String? = null, channel: String? = null, skillName: String? = null, queueName: String? = null) =
        InboundResponseOutputContext(skillName, queueName)

    fun generateCallerResponseOutputContext(ivr: String? = null, channel: String? = null, skillName: String? = null, queueName: String? = null) =
        CallerResponseOutputContext (queueName, ivr, channel, skillName)

    fun generateCall(id: Int = 0, phone: String = "9999999999", did: String = "1111", customerCRTId: String = "test_id", timestampCreated: Instant = TIMESTAMP, timestampUpdated: Instant = TIMESTAMP) =
        Call(id, phone, did, customerCRTId, timestampCreated, timestampUpdated)

    fun generateRheoResponseContext(status: Boolean = true, msg: String = "test", result: Map<String, String> = mapOf()) = RheoResponseContext(status, msg, result)
}
