package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.Constants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.helper.FeatureRequestGenerator
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import kotlin.test.assertTrue

class InboundContextTest {
    private val featureContextProviderClientMock: FeatureContextProviderClient = mockk()
    private val featureRequestGenerator: FeatureRequestGenerator = FeatureRequestGenerator()
    private val inboundContext = InboundContext(featureContextProviderClientMock, featureRequestGenerator)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    //test cases
    // partner | Chandigarh | accept_vicinity -> { "queue": "Hindi_accept_vicinity", "skill": "Hindi_Priority2" }
    // partner | Pune | end_job | Pickup / 8ft-Outstation -> { "queue": "Marathi_end_job", "skill": "Marathi_Priority1" }
    // Delhi NCR | unknown ->  { "channel": "inbound", "ivr": "nor", "queue": "Default_Hindi", "skill": "Hindi_Priority3" }
    // Customer | Kochi | end_trip -> { "queue": "FPOR_Malayalam", "skill": "Malayalam_FPOR" }

    @Test
    fun `fetching inbound context for a partner in Chandigarh in accept_vicinity`() {
        runBlockingTest {
            val orderContext = Generators.generateOrderContext(orderStagVicinity = "accept_vicinity")
            val userCallContext = Generators.generateUserCallContext(userType = "partner", city = "Chandigarh", order = orderContext)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns Generators.generateRheoResponseContext(result = mapOf("skill" to "Hindi_Priority2", "queue" to "Hindi_accept_vicinity"))

            val response = inboundContext.fetch(userCallContext)
            val expected = Generators.generateInboundResponseOutputContext(skillName = "Hindi_Priority2", queueName = "Hindi_accept_vicinity")

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching inbound context for a partner in Pune in end_job with Pickup 8ft-Outstation vehicle`() {
        runBlockingTest {
            val orderContext = Generators.generateOrderContext(orderStagVicinity = "end_job")
            val userCallContext = Generators.generateUserCallContext(userType = "partner", city = "Pune", order = orderContext)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns Generators.generateRheoResponseContext(result = mapOf("skill" to "Marathi_Priority1", "queue" to "Marathi_end_job"))

            val response = inboundContext.fetch(userCallContext)
            val expected = Generators.generateInboundResponseOutputContext(skillName = "Marathi_Priority1", queueName = "Marathi_end_job")

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching inbound context for a unknown caller in Delhi NCR`() {
        runBlockingTest {
            val userCallContext = Generators.generateUserCallContext(userType = "unknown", order = null)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns Generators.generateRheoResponseContext(result = mapOf("ivr" to "nor", "channel" to Constants.INBOUND , "skill" to "Hindi_Priority3", "queue" to "Default_Hindi"))

            val response = inboundContext.fetch(userCallContext)
            val expected = Generators.generateInboundResponseOutputContext(ivr = "nor", channel = Constants.INBOUND, skillName = "Hindi_Priority3", queueName = "Default_Hindi")

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching inbound context for a Customer in Kochi in end_trip vicinity`() {
        runBlockingTest {
            val orderContext = Generators.generateOrderContext(orderStagVicinity = "end_trip")
            val userCallContext = Generators.generateUserCallContext(userType = "customer", city = "Kochi", order = orderContext)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns Generators.generateRheoResponseContext(result = mapOf("skill" to "Malayalam_FPOR", "queue" to "FPOR_Malayalam"))

            val response = inboundContext.fetch(userCallContext)
            val expected = Generators.generateInboundResponseOutputContext(skillName = "Malayalam_FPOR", queueName = "FPOR_Malayalam")

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching inbound context for null feature context`() {
        runBlockingTest {
            val userCallContext = Generators.generateUserCallContext()

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns Generators.generateRheoResponseContext(status = false)

            val response = inboundContext.fetch(userCallContext)
            val expected = null

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }
}
