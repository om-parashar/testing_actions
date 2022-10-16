package rheoconnect.usecases.internal

import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import io.ktor.client.*
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import rheoconnect.entities.RheoConfig
import rheoconnect.entities.RheoConstants
import rheoconnect.entities.RheoContextAttribute
import rheoconnect.entities.RheoMessage
import rheoconnect.usecases.helpers.Generators.generateRheoConfig
import rheoconnect.usecases.helpers.Generators.generateRheoInput
import rheoconnect.usecases.helpers.Generators.generateRheoResponse
import rheoconnect.usecases.helpers.Generators.generateSerdeMapper
import rheoconnect.usecases.helpers.Generators.getRawRheoResponseContextString
import rheoconnect.usecases.helpers.Generators.getRawRheoNumberResponseString
import kotlin.test.assertTrue

class FetchFromRheoTest {
    private val rheoConfig: RheoConfig = generateRheoConfig()
    private val mapper: SerdeMapper = generateSerdeMapper()
    private val httpClient: HttpClient = mockk()
    private val fetchFromRheo = FetchFromRheo(rheoConfig, mapper, httpClient)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `testing fetch feature`() = runBlockingTest {
        val rheoInput = generateRheoInput(
            featureName = "support-contact-number",
            contextAttrs = listOf(
                RheoContextAttribute(
                    key = "caller_type",
                    type = RheoConstants.STRING_TYPE,
                    value = "partner"
                ),
                RheoContextAttribute(
                    key = "vertical",
                    type = RheoConstants.STRING_TYPE,
                    value = "spot"
                )
            )
        )
        val fetchFromRheoMock = spyk(fetchFromRheo, recordPrivateCalls = true)

        coEvery {
            fetchFromRheoMock["performPostRequest"](allAny<String>(), allAny<String>())
        } returns getRawRheoNumberResponseString()

        val res = generateRheoResponse(context = getRawRheoResponseContextString())

        val response = fetchFromRheoMock.fetchFeature(rheoInput)
        val expected = res //generateRheoResponse(context = getRawRheoResponseContextString())

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `testing fetch feature with no response`() = runBlockingTest {
        val rheoInput = generateRheoInput(
            featureName = "support-contact-number-config",
            contextAttrs = listOf(
                RheoContextAttribute(
                    key = "caller_type",
                    type = RheoConstants.STRING_TYPE,
                    value = "partner"
                ),
                RheoContextAttribute(
                    key = "vertical",
                    type = RheoConstants.STRING_TYPE,
                    value = "spot"
                )
            )
        )
        val fetchFromRheoMock = spyk(fetchFromRheo, recordPrivateCalls = true)

        coEvery {
            fetchFromRheoMock["performPostRequest"](allAny<String>(), allAny<String>())
        } returns ""

        val response = fetchFromRheoMock.fetchFeature(rheoInput)
        val expected = generateRheoResponse(flagStatus = false, msg = RheoMessage.UNKNOWN_ERROR)

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

}