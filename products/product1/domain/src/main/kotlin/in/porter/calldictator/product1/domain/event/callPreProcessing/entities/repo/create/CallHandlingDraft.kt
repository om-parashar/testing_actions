package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo.create

import com.fasterxml.jackson.databind.JsonNode

data class CallHandlingDraft(
  val callId: Int,
  val type: String,
  val value: String,
  val context: JsonNode
)
