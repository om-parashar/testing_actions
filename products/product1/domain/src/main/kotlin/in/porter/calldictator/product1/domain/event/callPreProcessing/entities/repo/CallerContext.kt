package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo

import com.fasterxml.jackson.databind.JsonNode

data class CallerContext(
  val id: Int,
  val callId: Int,
  val context: JsonNode,
  val timestampCreated: String,
  val timestampUpdated: String
)
