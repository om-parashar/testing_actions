package `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.repo

import java.time.Instant

data class Call(
  val id: Int,
  val phone: String,
  val did: String,
  val customerCRTId: String,
  val timestampCreated: Instant,
  val timestampUpdated: Instant
)
