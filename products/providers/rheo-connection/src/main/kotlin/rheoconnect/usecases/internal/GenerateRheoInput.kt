package rheoconnect.usecases.internal

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.ContextAttribute
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoRequestContext
import `in`.porter.support.domain.entities.rheo.RheoRequestContext as SupportRheoRequestContext
import `in`.porter.support.domain.entities.rheo.ContextAttribute as SupportContextAttribute
import rheoconnect.entities.RheoConstants
import rheoconnect.entities.RheoContextAttribute
import rheoconnect.entities.RheoInput
import javax.inject.Inject

class GenerateRheoInput
@Inject
constructor(){

  fun execute(request: RheoRequestContext): RheoInput {
    return RheoInput(
      featureName = request.featureName,
      userId = request.userId,
      contextAttrs = getContextAttrs(request.contextAttrs)
    )
  }

  fun execute(request: SupportRheoRequestContext): RheoInput {
    return RheoInput(
      featureName = request.featureName,
      userId = request.userId,
      contextAttrs = getSupportContextAttrs(request.contextAttrs)
    )
  }

  private fun getContextAttrs(contextAttr: List<ContextAttribute>?): List<RheoContextAttribute> {
    val list = mutableListOf<RheoContextAttribute>()

    if (contextAttr == null) {
      return list
    }

    for (item in contextAttr) {
      when(item) {
        is ContextAttribute.StringType -> list.add(RheoContextAttribute(key = item.key, value = item.value, type = RheoConstants.STRING_TYPE))
        is ContextAttribute.BooleanType -> list.add(RheoContextAttribute(key = item.key, value = item.value, type = RheoConstants.BOOLEAN_TYPE))
        is ContextAttribute.NumberType -> list.add(RheoContextAttribute(key = item.key, value = item.value, type = RheoConstants.NUMBER_TYPE))
      }
    }
    return list
  }


  private fun getSupportContextAttrs(contextAttr: List<SupportContextAttribute>?): List<RheoContextAttribute> {
    val list = mutableListOf<RheoContextAttribute>()

    if (contextAttr == null) {
      return list
    }

    for (item in contextAttr) {
      when(item) {
        is SupportContextAttribute.StringType -> list.add(RheoContextAttribute(key = item.key, value = item.value, type = RheoConstants.STRING_TYPE))
        is SupportContextAttribute.BooleanType -> list.add(RheoContextAttribute(key = item.key, value = item.value, type = RheoConstants.BOOLEAN_TYPE))
        is SupportContextAttribute.NumberType -> list.add(RheoContextAttribute(key = item.key, value = item.value, type = RheoConstants.NUMBER_TYPE))
      }
    }
    return list
  }
}
