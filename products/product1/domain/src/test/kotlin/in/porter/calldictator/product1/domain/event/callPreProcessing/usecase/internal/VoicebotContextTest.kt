package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.clients.FeatureContextProviderClient
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.Constants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateOrderContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateRheoResponseContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.helper.FeatureRequestGenerator
import io.mockk.mockk
import org.junit.jupiter.api.Test
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateUserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateVoicebotResponseOutputContext
import io.mockk.clearAllMocks
import io.mockk.coEvery
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import kotlin.test.assertTrue

class VoicebotContextTest {
    private val featureContextProviderClientMock: FeatureContextProviderClient = mockk()
    private val featureRequestGenerator: FeatureRequestGenerator = FeatureRequestGenerator()
    private val voicebotContext = VoicebotContext(featureContextProviderClientMock, featureRequestGenerator)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    //test cases
    // 6d8179be-f3d9-4fc2-9438-7c5de9dca375 | partner | end_trip_vicinity -> { "channel": "inbound", "ivr": "vernacular" }
    // b8c19a9b-5bfc-43f6-a526-918e10a33f4d | partner | accept_vicinity -> { "channel": "inbound", "ivr": "rezo" }

    @Test
    fun `fetching voicebot context for a specific partner in end_trip_vicinity`() {
        runBlockingTest {
            val orderContext = generateOrderContext(orderStagVicinity = "end_trip_vicinity")
            val userCallContext = generateUserCallContext(uuid = "6d8179be-f3d9-4fc2-9438-7c5de9dca375", userType = "partner", order = orderContext)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(result = mapOf("ivr" to "vernacular", "channel" to Constants.INBOUND))

            val response = voicebotContext.fetch(userCallContext)
            val expected = generateVoicebotResponseOutputContext(ivr = "vernacular", channel = Constants.INBOUND)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching voicebot context for a specific partner in accept_vicinity`() {
        runBlockingTest {
            val orderContext = generateOrderContext(orderStagVicinity = "accept_vicinity")
            val userCallContext = generateUserCallContext(uuid = "b8c19a9b-5bfc-43f6-a526-918e10a33f4d", userType = "partner", order = orderContext)

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(result = mapOf("ivr" to "rezo", "channel" to Constants.INBOUND))

            val response = voicebotContext.fetch(userCallContext)
            val expected = generateVoicebotResponseOutputContext(ivr = "rezo", channel = Constants.INBOUND)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `fetching voicebot context for null feature context`() {
        runBlockingTest {
            val userCallContext = generateUserCallContext()

            coEvery {
                featureContextProviderClientMock.fetchFeatureContext(any())
            } returns generateRheoResponseContext(status = false)

            val response = voicebotContext.fetch(userCallContext)
            val expected = null

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }
}
