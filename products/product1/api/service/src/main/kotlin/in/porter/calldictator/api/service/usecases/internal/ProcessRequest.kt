package `in`.porter.calldictator.api.service.usecases.internal

import `in`.porter.calldictator.api.models.entities.CallDictateRequest
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.oms.CallerContext
import javax.inject.Inject

class ProcessRequest
@Inject
constructor(){

  private val INDIA_ISD_CODE = "91"

  fun invoke(request: CallDictateRequest) = CallerContext(
    did = request.did,
    phone = cleanPhone(request.phone),
    customerCRTId = request.customerCRTId
  )

  private fun cleanPhone(phone: String): String {
    if(phone.isBlank())
      return phone
    val regex = Regex("[^0-9]")
    return removeIsdCode(regex.replace(phone, ""))
  }

  private fun removeIsdCode(phone: String): String {
    if (phone.length == 10 || phone.substring(0,2) != INDIA_ISD_CODE) {
      return phone
    }
    return phone.substring(2)
  }
}
