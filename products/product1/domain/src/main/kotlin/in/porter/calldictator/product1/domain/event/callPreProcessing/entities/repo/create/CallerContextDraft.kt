package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create

import com.fasterxml.jackson.databind.JsonNode

data class CallerContextDraft(
  val callId: Int,
  val context: JsonNode
)
