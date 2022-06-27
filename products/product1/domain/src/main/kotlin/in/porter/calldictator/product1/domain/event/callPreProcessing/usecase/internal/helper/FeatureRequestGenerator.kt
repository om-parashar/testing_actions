package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.helper

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
