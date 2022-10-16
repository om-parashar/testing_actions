package rheoconnect.usecases.external

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.ContextAttribute
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import rheoconnect.usecases.helpers.Generators.generateRheoMapResponseContext
import rheoconnect.usecases.helpers.Generators.generateRheoRequestContext
import rheoconnect.usecases.helpers.Generators.generateRheoResponse
import rheoconnect.usecases.helpers.Generators.generateRheoResponseContext
import rheoconnect.usecases.helpers.Generators.generateRheoStringResponseContext
import rheoconnect.usecases.helpers.Generators.generateSerdeMapper
import rheoconnect.usecases.helpers.Generators.generateSupportRheoRequestContext
import rheoconnect.usecases.helpers.Generators.getRawRheoIVRResponseString
import rheoconnect.usecases.helpers.Generators.getRawRheoNumberResponseString
import rheoconnect.usecases.internal.FetchFromRheo
import rheoconnect.usecases.internal.GenerateRheoInput
import rheoconnect.usecases.internal.mapper.MapRheoResponse
import rheoconnect.usecases.internal.mapper.MapSupportEmailRheoResponse
import rheoconnect.usecases.internal.mapper.MapSupportNumberRheoResponse
import kotlin.test.assertTrue

class RheoClientTest {
    private val fetchFromRheoMock: FetchFromRheo = mockk()
    private val mapper = generateSerdeMapper()
    private val mapRheoResponse: MapRheoResponse = MapRheoResponse(mapper)
    private val mapSupportEmailRheoResponse: MapSupportEmailRheoResponse = MapSupportEmailRheoResponse(mapper)
    private val mapSupportNumberRheoResponse: MapSupportNumberRheoResponse = MapSupportNumberRheoResponse(mapper)
    private val generateRheoInput: GenerateRheoInput = GenerateRheoInput()
    private val rheoClient = RheoClient(fetchFromRheoMock, mapRheoResponse, mapSupportEmailRheoResponse, mapSupportNumberRheoResponse, generateRheoInput)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `fetching feature context test with random context`() = runBlockingTest {
        val request = generateRheoRequestContext(
            featureName = "calls-ivr",
            contextAttrs = listOf(
                ContextAttribute.NumberType(
                    key = "did",
                    value = 4410.0
                ),
                ContextAttribute.StringType(
                    key = "caller_type",
                    value = "customer"
                ),
                ContextAttribute.StringType(
                    key = "caller_identifier",
                    value = "364b8488-fe83-466a-bec4-252666af3faf"
                )
            )
        )

        coEvery {
            fetchFromRheoMock.fetchFeature(any())
        } returns generateRheoResponse(context = getRawRheoIVRResponseString())

        val response = rheoClient.fetchFeatureContext(request)
        val expected = generateRheoResponseContext(result = mapOf("ivr" to "start_trip_vicinity_not_cancelled", "channel" to "inbound"))

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching feature context test with null context`() = runBlockingTest {
        val request = generateRheoRequestContext(
            featureName = "calls-ivr",
            contextAttrs = listOf(
                ContextAttribute.NumberType(
                    key = "did",
                    value = 4410.0
                ),
                ContextAttribute.StringType(
                    key = "caller_type",
                    value = "customer"
                ),
                ContextAttribute.StringType(
                    key = "caller_identifier",
                    value = "364b8488-fe83-466a-bec4-252666af3faf"
                )
            )
        )

        coEvery {
            fetchFromRheoMock.fetchFeature(any())
        } returns generateRheoResponse()

        val response = rheoClient.fetchFeatureContext(request)
        val expected = generateRheoResponseContext()

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }


    @Test
    fun `fetching feature context with no context attributes`() = runBlockingTest {
        val request = generateRheoRequestContext()

        coEvery {
            fetchFromRheoMock.fetchFeature(any())
        } returns generateRheoResponse()

        val response = rheoClient.fetchFeatureContext(request)
        val expected = generateRheoResponseContext()

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching support number context test`() = runBlockingTest {
        val request = generateSupportRheoRequestContext()

        coEvery {
            fetchFromRheoMock.fetchFeature(any())
        } returns generateRheoResponse()

        val response = rheoClient.getSupportNumberContext(request)
        val expected = generateRheoMapResponseContext()

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching support email context test`() = runBlockingTest {
        val request = generateSupportRheoRequestContext()

        coEvery {
            fetchFromRheoMock.fetchFeature(any())
        } returns generateRheoResponse()

        val response = rheoClient.getSupportEmailContext(request)
        val expected = generateRheoStringResponseContext()

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }
}