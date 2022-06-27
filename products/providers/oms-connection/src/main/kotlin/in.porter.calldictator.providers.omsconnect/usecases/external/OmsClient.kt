package `in`.porter.calldictator.providers.omsconnect.usecases.external

import `in`.porter.calldictator.product1.domain.clients.CallContextProviderClient
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.*
import `in`.porter.calldictator.providers.omsconnect.mappers.OMSResponseMapper
import `in`.porter.calldictator.providers.omsconnect.usecases.internal.FetchFromOMS
import javax.inject.Inject

class OmsClient
@Inject
constructor(
  private val fetchFromOMS: FetchFromOMS,
  private val omsResponseMapper: OMSResponseMapper
): CallContextProviderClient {

  override suspend fun fetchDriverContext(request: CallerContext): DriverCallContext? {
    return request
      .let { fetchFromOMS.processDriverRequest(phone = it.phone) }
      .let { it?.let { it1 -> omsResponseMapper.toDriverCallContext(it1.response) } }
  }

  override suspend fun fetchCustomerContext(request: CallerContext): CustomerCallContext? {
    return request
      .let { fetchFromOMS.processCustomerRequest(phone = it.phone) }
      .let { it?.let { it1 -> omsResponseMapper.toCustomerCallContext(it1.response) } }
  }

  override suspend fun fetchOrderContext(request: CallerOrderContext): OrderContext? {
    return request
      .let { fetchFromOMS.processOrderRequest(caller_id = it.caller_id, caller_type = it.caller_type) }
      .let { it?.let { it1 -> omsResponseMapper.toOrderCallContext(it1.response) } }
  }

  override suspend fun fetchCityContext(did: String): CityCallContext? {
    return did
      .let { fetchFromOMS.processCityRequest(did = it) }
      .let { it?.let { it1 -> omsResponseMapper.toCityCallContext(it1.response) } }
  }

}
