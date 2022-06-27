package `in`.porter.calldictator.product1.domain.clients

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.*

interface CallContextProviderClient {
  suspend fun fetchDriverContext(request: CallerContext): DriverCallContext?
  suspend fun fetchCustomerContext(request: CallerContext): CustomerCallContext?
  suspend fun fetchOrderContext(request: CallerOrderContext): OrderContext?
  suspend fun fetchCityContext(did: String): CityCallContext?
}
