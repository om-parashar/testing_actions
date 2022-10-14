package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.Constants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateIvrResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateOrderContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateRheoResponseContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateUserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.helper.FeatureRequestGenerator
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import kotlin.test.assertTrue

class IvrContextTest {
    private val featureContextProviderClientMock: FeatureContextProviderClient = mockk()
    private val featureRequestGenerator: FeatureRequestGenerator = FeatureRequestGenerator()
    private val ivrContext = IvrContext(featureContextProviderClientMock, featureRequestGenerator)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    //test cases
    // 364b8488-fe83-466a-bec4-252666af3faf -> { "channel": "inbound", "ivr": "start_trip_vicinity_not_cancelled" }
    // customer | Bangalore | nor -> { "channel": "inbound", "ivr": "customer_nor", "queue": "Kannada_NOR" }
    // partner | Surat | end_trip_vicinity -> { "channel": "inbound", "ivr": "drop_vicinity_partner_ivr" }
    // partner | Mumbai | accept_vicinity | status = cancelled | cancelled_reason = partner -> { "channel": "inbound", "ivr": "accept_vicinity_cancelled_order" }
    // partner | Delhi NCR | accept_start_vicinity | status = accepted | waypoints = false | union_city = true | helper_order = false -> { "channel": "inbound", "ivr": "accept_vicinity_no_waypoint_union_no_helper" }
    // partner | Chennal | nor | suspension_reason = doing_fraud_orders -> { "channel": "inbound", "ivr": "partner_suspension_ivr" }

    @Test
    fun `fetching ivr context for a specific caller`() {
        runBlockingTest {
            val userCallContext = generateUserCallContext(uuid = "364b8488-fe83-466a-bec4-252666af3faf")

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(result = mapOf("ivr" to "start_trip_vicinity_not_cancelled", "channel" to Constants.INBOUND))

            val response = ivrContext.fetch(userCallContext)
            val expected = generateIvrResponseOutputContext(ivr = "start_trip_vicinity_not_cancelled", channel = Constants.INBOUND)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching ivr context for a customer in Bangalore in NOR`() {
        runBlockingTest {
            val orderContext = generateOrderContext(orderStagVicinity = "nor")
            val userCallContext = generateUserCallContext(userType = "customer", city = "Bangalore")

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(result = mapOf("ivr" to "customer_nor", "channel" to Constants.INBOUND, "queue" to "Kannada_NOR"))

            val response = ivrContext.fetch(userCallContext)
            val expected = generateIvrResponseOutputContext(ivr = "customer_nor", channel = Constants.INBOUND, queueName = "Kannada_NOR")

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching ivr context for a partner in Surat in end_trip_vicinity`() {
        runBlockingTest {
            val orderContext = generateOrderContext(orderStagVicinity = "end_trip_vicinity")
            val userCallContext = generateUserCallContext(userType = "partner", city = "Surat", order = orderContext)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(result = mapOf("ivr" to "drop_vicinity_partner_ivr", "channel" to Constants.INBOUND))

            val response = ivrContext.fetch(userCallContext)
            val expected = generateIvrResponseOutputContext(ivr = "drop_vicinity_partner_ivr", channel = Constants.INBOUND)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching ivr context for a partner in Mumbai in accept_vicinity with cancelled order because of partner`() {
        runBlockingTest {
            val orderContext = generateOrderContext(orderStagVicinity = "accept_vicinity", status = "cancelled", cancellationReason = "partner")
            val userCallContext = generateUserCallContext(userType = "partner", city = "Mumbai", order = orderContext)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(result = mapOf("ivr" to "accept_vicinity_cancelled_order", "channel" to Constants.INBOUND))

            val response = ivrContext.fetch(userCallContext)
            val expected = generateIvrResponseOutputContext(ivr = "accept_vicinity_cancelled_order", channel = Constants.INBOUND)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching ivr context for a partner in Delhi NCR in accept_start_vicinity with order status as accepted, has no waypoints, is union city and not a helper order`() {
        runBlockingTest {
            val orderContext = generateOrderContext(orderStagVicinity = "accept_start_vicinity", status = "accepted", hasWaypoints = false, isHelperOrder = false)
            val userCallContext = generateUserCallContext(userType = "partner", city = "Delhi NCR", order = orderContext)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(result = mapOf("ivr" to "accept_vicinity_no_waypoint_union_no_helper", "channel" to Constants.INBOUND))

            val response = ivrContext.fetch(userCallContext)
            val expected = generateIvrResponseOutputContext(ivr = "accept_vicinity_no_waypoint_union_no_helper", channel = Constants.INBOUND)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching ivr context for a partner in Chennai in nor with suspension_reason as doing_fraud_orders`() {
        runBlockingTest {
            val userCallContext = generateUserCallContext(userType = "partner", city = "Chennai", isSuspended = true, suspensionReason = "doing_fraud_orders")

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(result = mapOf("ivr" to "partner_suspension_ivr", "channel" to Constants.INBOUND))

            val response = ivrContext.fetch(userCallContext)
            val expected = generateIvrResponseOutputContext(ivr = "partner_suspension_ivr", channel = Constants.INBOUND)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching ivr context for null feature context`() {
        runBlockingTest {
            val userCallContext = generateUserCallContext()

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(status = false)

            val response = ivrContext.fetch(userCallContext)
            val expected = null

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }
}
