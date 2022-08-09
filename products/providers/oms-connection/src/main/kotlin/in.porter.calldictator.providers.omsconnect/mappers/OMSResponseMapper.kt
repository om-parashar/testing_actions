package `in`.porter.calldictator.providers.omsconnect.mappers

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.*
import `in`.porter.calldictator.providers.omsconnect.entities.CityResponseContext
import `in`.porter.calldictator.providers.omsconnect.entities.CustomerResponseContext
import `in`.porter.calldictator.providers.omsconnect.entities.DriverResponseContext
import `in`.porter.calldictator.providers.omsconnect.entities.OrderResponseContext
import arrow.core.none
import javax.inject.Inject

class OMSResponseMapper
@Inject
constructor() {

  fun toDriverCallContext(response: DriverResponseContext) = DriverCallContext (
    id = response.id,
    city = response.city.name,
    language = response.language,
    loginStatus = response.loginStatus,
    isSuspended = response.suspensionInfo.isSuspended,
    suspensionReason = response.suspensionInfo.reason["value"].toString(),
    uuid = response.uuid,
    vehicleInfo = response.vehicleInfo.type
    )


  fun toCustomerCallContext(response: CustomerResponseContext) = CustomerCallContext (
    id = response.id,
    uuid = response.uuid,
    city = response.city.name,
    language = response.language,
    subCategory = if (response.subCategory.isNotEmpty()) response.subCategory["value"].toString() else ""
    )

  fun toOrderCallContext(response: OrderResponseContext) = OrderContext (
    id = response.id,
    alternateNumber = response.alternateNumber["value"].toString(),
    customerId = response.customerInfo.id["value"]?.toInt(),
    isBusinessOrder = response.isBusinessOrder,
    isHelperOrder = response.isHelperOrder,
    driverId = response.driverInfo.id["value"]?.toInt(),
    hasWaypoints = response.hasWaypoints,
    labour = response.labour,
    orderStagVicinity = response.orderStageVicinity["value"].toString(),
    labourHelper = response.labourHelper,
    outstation = response.outstation,
    requestedVehicleName = response.vehicleInfo.typeRequested,
    status = response.status,
    cancellationReason = response.cancellationInfo.value?.cancelReason,
    cancellationReasonSource = response.cancellationInfo.value?.cancelReasonSource,
    cancellationReasonAttribution = response.cancellationInfo.value?.attribution?.get("value")
    )

  fun toCityCallContext(response: CityResponseContext) = CityCallContext (
    name = response.name,
    language = response.language
    )
}
