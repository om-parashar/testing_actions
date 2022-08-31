package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.helper

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.GeoRegionConstants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.UserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.ContextAttribute
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoRequestContext
import javax.inject.Inject

class FeatureRequestGenerator
@Inject
constructor(){

  fun invoke(userCallContext: UserCallContext, featureName:String): RheoRequestContext {
    val contextAttrs = mutableListOf<ContextAttribute>()
    contextAttrs.add(buildString("did", userCallContext.did))
    contextAttrs.add(buildString("language", userCallContext.language))
    contextAttrs.add(buildString("caller_type", userCallContext.userType))
    contextAttrs.add(buildString("phone", userCallContext.mobile))
    contextAttrs.add(buildString("city", userCallContext.city))
    contextAttrs.add(buildString("caller_identifier", userCallContext.uuid.toString()))

    contextAttrs.add(buildString("order_stage_vicinity", if(userCallContext.order == null) "nor" else userCallContext.order.orderStagVicinity))
    contextAttrs.add(buildBoolean("is_union_city", GeoRegionConstants.UNION_CITY.contains(userCallContext.city)))

    userCallContext.vehicleInfo?.let { contextAttrs.add(buildString("partner_vehicle_type", it)) }

    if(userCallContext.isSuspended != null && userCallContext.isSuspended) {
      userCallContext.suspensionReason?.let { buildString("partner_suspension_reason", it) }?.let { contextAttrs.add(it) }
    }

    if(userCallContext.order != null) {
      contextAttrs.add(buildString("order_status", userCallContext.order.status))
      contextAttrs.add(buildString("order_vehicle_type", userCallContext.order.requestedVehicleName))
      contextAttrs.add(buildBoolean("has_waypoints", userCallContext.order.hasWaypoints))
      contextAttrs.add(buildBoolean("is_business_order", userCallContext.order.isBusinessOrder))
      contextAttrs.add(buildBoolean("is_helper_order", userCallContext.order.isHelperOrder))

      userCallContext.order.cancellationReasonAttribution?.let { buildString("cancel_reason_attribution", it) }
      userCallContext.order.cancellationReasonSource?.let { buildString("cancel_reason_source", it) }
    }

    return RheoRequestContext(
      featureName = featureName,
      userId = "call-dictator",
      contextAttrs = contextAttrs
    )
  }

  private fun buildString(key: String, value: String) = ContextAttribute.StringType(
    key = key,
    value = value
  )

  private fun buildBoolean(key: String, value: Boolean) = ContextAttribute.BooleanType(
    key = key,
    value = value
  )

  private fun buildNumber(key: String, value: Double) = ContextAttribute.NumberType(
    key = key,
    value = value
  )
}
