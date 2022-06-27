package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.clients.CallContextProviderClient
import `in`.porter.calldictator.product1.domain.di.ProviderNames
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.*
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper.CallContextMapper
import `in`.porter.kotlinutils.instrumentation.opentracing.logger
import javax.inject.Inject
import javax.inject.Named

class ProcessCallContext
@Inject
constructor(
  @Named(ProviderNames.OMS_CONNECTION)
  private val callContextProviderClient: CallContextProviderClient,
  private val callContextMapper: CallContextMapper
){

  suspend fun invoke(request: CallerContext): UserCallContext {

    // fetch driver context for given caller number.
    val driver = callContextProviderClient.fetchDriverContext(request)
    if (driver != null) {
      logger.info("driver response: $driver")
      return getUserCallContextFromDriver(request, driver)
    }

    // fetch customer context for given number.
    val customer = callContextProviderClient.fetchCustomerContext(request)
    if (customer != null) {
      logger.info("customer response: $customer")
      return getUserCallContextFromCustomer(request, customer)
    }

    // fetch unknown context for a given number.
    val city = callContextProviderClient.fetchCityContext(request.did)
    return getUserCallContextForUnknownCaller(request, city)
  }

  private suspend fun getUserCallContextFromDriver(callerContext: CallerContext, driverCallContext: DriverCallContext): UserCallContext{
    return CallerOrderContext(caller_type = Constants.USER_TYPE_DRIVER, caller_id = driverCallContext.id)
      .let { callContextProviderClient.fetchOrderContext(it) }
      .let { callContextMapper.mapDriverContextToUserContext(callerContext, driverCallContext, it) }
  }

  private suspend fun getUserCallContextFromCustomer(callerContext: CallerContext, customerCallContext: CustomerCallContext): UserCallContext {
    return CallerOrderContext(caller_type = Constants.USER_TYPE_CUSTOMER, caller_id = customerCallContext.id)
      .let { callContextProviderClient.fetchOrderContext(it) }
      .let { callContextMapper.mapCustomerContextToUserContext(callerContext, customerCallContext, it) }
  }

  private fun getUserCallContextForUnknownCaller(callerContext: CallerContext, city: CityCallContext?): UserCallContext {
    var name = ""
    if(city != null) {
      name = city.name
    }
    return callContextMapper.mapUnknownContextToUserContext(callerContext, name, "")
  }
}
