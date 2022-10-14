package `in`.porter.calldictator.providers.omsconnect.usecases.external

import `in`.porter.calldictator.providers.omsconnect.mappers.OMSResponseMapper
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCallerContext
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCallerOrderContext
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCityCallContext
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCityResponse
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCustomerCallContext
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCustomerResponse
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateDriverCallContext
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateDriverResponse
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateOrderContext
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateOrderResponse
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.getDefaultDid
import `in`.porter.calldictator.providers.omsconnect.usecases.internal.FetchFromOMS
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import kotlin.test.assertTrue

class OmsClientTest {
    private val fetchFromOMSMock: FetchFromOMS = mockk()
    private val omsResponseMapper: OMSResponseMapper = OMSResponseMapper()
    private val omsClient = OmsClient(fetchFromOMSMock, omsResponseMapper)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `fetching driver context test`() = runBlockingTest {
        val callerContext = generateCallerContext()

        coEvery {
            fetchFromOMSMock.processDriverRequest(any())
        } returns generateDriverResponse()

        val response = omsClient.fetchDriverContext(callerContext)
        val expected= generateDriverCallContext()
        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")


        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching null driver context test`() = runBlockingTest {
        val callerContext = generateCallerContext()

        coEvery {
            fetchFromOMSMock.processDriverRequest(any())
        } returns null

        val response = omsClient.fetchDriverContext(callerContext)
        val expected= null

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching customer context test`() = runBlockingTest {
        val callerContext = generateCallerContext()

        coEvery {
            fetchFromOMSMock.processCustomerRequest(any())
        } returns generateCustomerResponse()

        val response = omsClient.fetchCustomerContext(callerContext)
        val expected= generateCustomerCallContext()
        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")


        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching null customer context test`() = runBlockingTest {
        val callerContext = generateCallerContext()

        coEvery {
            fetchFromOMSMock.processCustomerRequest(any())
        } returns null

        val response = omsClient.fetchCustomerContext(callerContext)
        val expected= null

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching order context test`() = runBlockingTest {
        val callerContext = generateCallerOrderContext()

        coEvery {
            fetchFromOMSMock.processOrderRequest(any(), any())
        } returns generateOrderResponse()

        val response = omsClient.fetchOrderContext(callerContext)
        val expected = generateOrderContext()

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching null order context test`() = runBlockingTest {
        val callerContext = generateCallerOrderContext()

        coEvery {
            fetchFromOMSMock.processOrderRequest(any(), any())
        } returns null

        val response = omsClient.fetchOrderContext(callerContext)
        val expected = null

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching city context test`() = runBlockingTest {
        val did = getDefaultDid()

        coEvery {
            fetchFromOMSMock.processCityRequest(any())
        } returns generateCityResponse()

        val response = omsClient.fetchCityContext(did)
        val expected = generateCityCallContext()

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching null city context test`() = runBlockingTest {
        val did = getDefaultDid()

        coEvery {
            fetchFromOMSMock.processCityRequest(any())
        } returns null

        val response = omsClient.fetchCityContext(did)
        val expected = null

        assertTrue(ReflectionEquals(expected).matches(response))
    }

}
