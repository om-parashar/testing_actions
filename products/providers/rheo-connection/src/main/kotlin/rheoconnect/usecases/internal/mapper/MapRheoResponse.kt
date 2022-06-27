package rheoconnect.usecases.internal.mapper

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoResponseContext
import rheoconnect.entities.RheoResponse
import javax.inject.Inject

class MapRheoResponse
@Inject
constructor(){

  fun invoke(rheoResponse: RheoResponse): RheoResponseContext {
    return RheoResponseContext(
      status = true,
      result = ""
    )
  }
}
