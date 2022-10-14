package `in`.porter.calldictator.providers.omsconnect.usecases.helpers

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.*
import `in`.porter.calldictator.providers.omsconnect.entities.*
import `in`.porter.calldictator.providers.omsconnect.entities.OrderCancellationInfo
import `in`.porter.calldictator.providers.omsconnect.entities.OrderVehicleInfo
import `in`.porter.calldictator.providers.omsconnect.entities.UserInfo
import `in`.porter.kotlinutils.serde.jackson.custom.*
import `in`.porter.kotlinutils.serde.jackson.json.JacksonSerdeMapperFactory

object Generators {

    fun generateSerdeMapper() = JacksonSerdeMapperFactory().build().apply {
        this.configure {
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            registerModules(
                KotlinModule(),
                InstantSerde.millisModule,
                MoneySerde.moneyModule,
                LocalDateSerde.localDateModule,
                LocalTimeSerde.localTimeSerde,
                DurationSerde.millisModule)
        }
    }
    fun generateOMSConfig() = OMSConfig("test", "test", "test", "test", "test")

    fun generateCity(name: String = "unknown") = City(name)

    fun generateVehicleInfo(type: String = "test") = VehicleInfo(type)

    fun generateSuspensionInfo(isSuspended: Boolean = false, reason: Map<String, String> = mapOf()) = SuspensionInfo(isSuspended, reason)

    fun generateCallerContext(did: String = getDefaultDid(), phone: String = "9999999999", customerCRTId: String = "test_id") =
        CallerContext(did, phone, customerCRTId)

    fun generateDriverResponseContext(id: Int = 0, uuid: String = "test", city: City = generateCity(), language: String = getDefaultLanguage(), loginStatus: String = "test", vehicleInfo: VehicleInfo = generateVehicleInfo(), suspensionInfo: SuspensionInfo = generateSuspensionInfo()) =
        DriverResponseContext(id, uuid, city, language, loginStatus, vehicleInfo, suspensionInfo)

    fun generateDriverResponse(status: Int = 200, msg: String = getDefaultMessage(), response: DriverResponseContext = generateDriverResponseContext()) =
        DriverResponse(status, msg, response)

    fun generateDriverCallContext(id: Int = 0, uuid: String = "test", city: String = "unknown", language: String = getDefaultLanguage(), loginStatus: String = "test", vehicleInfo: String = "test", isSuspended: Boolean = false, suspensionReason: String? = null) =
        DriverCallContext(id, uuid, city, language, loginStatus, vehicleInfo, isSuspended, null)

    fun generateCustomerResponseContext(id: Int = 0, uuid: String = "test", city: City = generateCity(), language: String = getDefaultLanguage(), subCategory: Map<String, String> = mapOf()) =
        CustomerResponseContext(id, uuid, city, language, subCategory)

    fun generateCustomerResponse(status: Int = 200, msg: String = "success", response: CustomerResponseContext = generateCustomerResponseContext()) =
        CustomerResponse(status, msg, response)
    fun generateCustomerCallContext(id: Int = 0, uuid: String = "test", city: String = "unknown", language: String = getDefaultLanguage(), subCategory: String = getDefaultSubCategory()) =
        CustomerCallContext(id, uuid, city, language, subCategory)

    fun getDefaultSubCategory() = ""
    fun getDefaultIdInt() = 0
    fun getDefaultIdMap() = mapOf<String, String>()
    fun getDefaultStatusString() = "test"
    fun getDefaultStatusInt() = 200
    fun getDefaultMessage() = "success"
    fun getDefaultCityName() = "unknown"
    fun getDefaultDid() = "test"
    fun getDefaultLanguage() = "hindi"
    fun getDefaultOrderVehicleTypeRequested() = "test"

    fun generateUserInfo(id: Map<String, String> = getDefaultIdMap()) =
        UserInfo(id)

    fun generateOrderVehicleInfo(typeRequested: String = getDefaultOrderVehicleTypeRequested()) =
        OrderVehicleInfo(typeRequested)

    fun getDefaultCancelReason() = null

    fun getDefaultCancelReasonSource() = null
    fun getDefaultOrderStagevicinity() = "null"

    fun generateOrderCancellationInfo(cancelReason: String? = getDefaultCancelReason(), cancelReasonSource: String? = getDefaultCancelReasonSource(), attribution: Map<String, String> = mapOf()) =
        OrderCancellationInfo(cancelReason, cancelReasonSource, attribution)

    fun generateCancellationInfo(value: OrderCancellationInfo? = generateOrderCancellationInfo()) =
        Cancellation(value)

    fun generateOrderResponseContext(id: Int = getDefaultIdInt(),
                                     status: String = getDefaultStatusString(),
                                     driverInfo: UserInfo = generateUserInfo(),
                                     customerInfo: UserInfo = generateUserInfo(),
                                     vehicleInfo: OrderVehicleInfo = generateOrderVehicleInfo(),
                                     cancellationInfo: Cancellation = generateCancellationInfo(),
                                     orderStageVicinity: Map<String, String> = mapOf(),
                                     hasWaypoints: Boolean = false,
                                     labour: Boolean = false,
                                     labourHelper: Boolean = false,
                                     isHelperOrder: Boolean = false,
                                     isBusinessOrder: Boolean = false,
                                     outstation: Boolean = false,
                                     alternateNumber: Map<String, String> = mapOf()
    ) =
        OrderResponseContext(id, status, driverInfo, customerInfo, vehicleInfo, cancellationInfo, orderStageVicinity, hasWaypoints, labour, labourHelper, isHelperOrder, isBusinessOrder, outstation, alternateNumber)


    fun generateOrderResponse(status: Int = 200, msg: String = "success", response: OrderResponseContext = generateOrderResponseContext()) =
        OrderResponse(status, msg, response)

    fun generateCallerOrderContext( caller_id: Int = 0, caller_type: String = "unknown") =
        CallerOrderContext(caller_id, caller_type)

    fun generateOrderContext(id: Int = getDefaultIdInt(),
                             status: String = getDefaultStatusString(),
                             driverId: Int? = null,
                             customerId: Int? = null,
                             requestedVehicleName: String = getDefaultOrderVehicleTypeRequested(),
                             cancellationReason: String? = getDefaultCancelReason(),
                             cancellationReasonSource: String? = getDefaultCancelReasonSource(),
                             cancellationReasonAttribution: String? = null,
                             orderStagVicinity: String = getDefaultOrderStagevicinity(),
                             hasWaypoints: Boolean = false,
                             labour: Boolean = false,
                             labourHelper: Boolean = false,
                             isHelperOrder: Boolean = false,
                             isBusinessOrder: Boolean = false,
                             outstation: Boolean = false,
                             alternateNumber: String? = null) =
        OrderContext(id, status, driverId, customerId, requestedVehicleName, cancellationReason, cancellationReasonSource, cancellationReasonAttribution, orderStagVicinity, hasWaypoints, labour, labourHelper, isHelperOrder, isBusinessOrder, outstation, alternateNumber)

    fun generateCityResponseContext(name: String = getDefaultCityName(), language: String = getDefaultLanguage()) =
        CityResponseContext(name, language)

    fun generateCityResponse(status: Int = getDefaultStatusInt(), msg: String = getDefaultMessage(), response: CityResponseContext = generateCityResponseContext()) =
        CityResponse(status, msg, response)

    fun generateCityCallContext(name: String = getDefaultCityName(), language: String = getDefaultLanguage()) =
        CityCallContext(name, language)
}