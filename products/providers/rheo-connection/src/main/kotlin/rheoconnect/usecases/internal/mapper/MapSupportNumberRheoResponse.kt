package rheoconnect.usecases.internal.mapper

import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import `in`.porter.support.domain.entities.rheo.RheoMapResponseContext
import rheoconnect.entities.RheoResponse
import javax.inject.Inject

class MapSupportNumberRheoResponse
@Inject
constructor(
    private val mapper: SerdeMapper
){

    fun invoke(rheoResponse: RheoResponse)= RheoMapResponseContext (
        status = rheoResponse.flagStatus,
        msg = rheoResponse.msg.name,
        result = if(rheoResponse.flagStatus) parseJsonResponse(rheoResponse.context) else mutableMapOf()
    )

    private fun parseJsonResponse(response: String?): Map<String, Map<String, String>> {
        return if (response == null) mutableMapOf() else mapper.fromString(response, mutableMapOf<String,Map<String, String>>()::class.java)
    }
}
