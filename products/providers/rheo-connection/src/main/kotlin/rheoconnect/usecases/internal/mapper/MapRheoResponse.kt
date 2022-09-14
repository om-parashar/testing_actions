package rheoconnect.usecases.internal.mapper

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoResponseContext
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import rheoconnect.entities.RheoResponse
import javax.inject.Inject

class MapRheoResponse
@Inject
constructor(
  private val mapper: SerdeMapper
){

  fun invoke(rheoResponse: RheoResponse)= RheoResponseContext (
    status = rheoResponse.flagStatus,
    msg = rheoResponse.msg.name,
    result = if(rheoResponse.flagStatus) parseJsonResponse(rheoResponse.context) else mutableMapOf()
  )

  private fun parseJsonResponse(response: String?): Map<String, String> {
    return if (response == null) mutableMapOf() else mapper.fromString(response, mutableMapOf<String,String>()::class.java)
  }
}
