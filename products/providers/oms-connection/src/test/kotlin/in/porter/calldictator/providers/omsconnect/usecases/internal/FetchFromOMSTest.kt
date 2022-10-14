package `in`.porter.calldictator.providers.omsconnect.usecases.internal

import `in`.porter.calldictator.providers.omsconnect.entities.OMSConfig
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCallerOrderContext
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCityResponse
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateCustomerResponse
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateDriverResponse
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateOMSConfig
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateOrderResponse
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.generateSerdeMapper
import `in`.porter.calldictator.providers.omsconnect.usecases.helpers.Generators.getDefaultDid
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
import kotlin.test.assertTrue

class FetchFromOMSTest {
    private val omsConfig: OMSConfig = generateOMSConfig()
    private val mapper: SerdeMapper = generateSerdeMapper()
    private val httpClient: HttpClient = mockk()
    val fetchFromOMS = FetchFromOMS(omsConfig, mapper, httpClient)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }
    @Test
    fun `processing driver request returns DriverResponse`() = runBlockingTest {
        val phone = "9999999999"

        val fetchFromOmsMock = spyk(fetchFromOMS, recordPrivateCalls = true)

        coEvery {
            fetchFromOmsMock["performGetRequest"](allAny<String>())
        } returns "{\"status\":200,\"msg\":\"success\",\"response\":{\"id\":0,\"uuid\":\"test\",\"city\":{\"name\":\"unknown\"},\"language\":\"hindi\",\"login_status\":\"test\",\"vehicle_info\":{\"type\":\"test\"},\"suspension_info\":{\"is_suspended\":false,\"reason\":{}}}}"

        val response = fetchFromOmsMock.processDriverRequest(phone)
        val expected = generateDriverResponse()

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }
    @Test
    fun `processing driver request returns null`() = runBlockingTest {
        val phone = "9999999999"

        val fetchFromOmsMock = spyk(fetchFromOMS, recordPrivateCalls = true)

        coEvery {
            fetchFromOmsMock["performGetRequest"](allAny<String>())
        } returns ""

        val response = fetchFromOmsMock.processDriverRequest(phone)
        val expected = null

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `processing customer request returns CustomerResponse`() = runBlockingTest {
        val phone = "9999999999"

        val fetchFromOmsMock = spyk(fetchFromOMS, recordPrivateCalls = true)

        coEvery {
            fetchFromOmsMock["performGetRequest"](allAny<String>())
        } returns "{\"status\":200,\"msg\":\"success\",\"response\":{\"id\":0,\"uuid\":\"test\",\"city\":{\"name\":\"unknown\"},\"language\":\"hindi\",\"sub_category\":{}}}"

        val response = fetchFromOmsMock.processCustomerRequest(phone)
        val expected = generateCustomerResponse()

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `processing customer request returns null`() = runBlockingTest {
        val phone = "9999999999"

        val fetchFromOmsMock = spyk(fetchFromOMS, recordPrivateCalls = true)

        coEvery {
            fetchFromOmsMock["performGetRequest"](allAny<String>())
        } returns ""

        val response = fetchFromOmsMock.processCustomerRequest(phone)
        val expected = null

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `processing order request returns OrderResponse`() = runBlockingTest {
        val callerOrderContext = generateCallerOrderContext()

        val fetchFromOmsMock = spyk(fetchFromOMS, recordPrivateCalls = true)

        coEvery {
            fetchFromOmsMock["performGetRequest"](allAny<String>())
        } returns "{\"status\":200,\"msg\":\"success\",\"response\":{\"id\":0,\"status\":\"test\",\"driver_info\":{\"id\":{}},\"customer_info\":{\"id\":{}},\"vehicle_info\":{\"type_requested\":\"test\"},\"cancellation_info\":{\"value\":{\"cancel_reason\":null,\"cancel_reason_source\":null,\"attribution\":{}}},\"order_stage_vicinity\":{},\"has_waypoints\":false,\"labour\":false,\"labour_helper\":false,\"is_helper_order\":false,\"is_business_order\":false,\"outstation\":false,\"alternate_number\":{}}}"

        val response = fetchFromOmsMock.processOrderRequest(callerOrderContext.caller_id, callerOrderContext.caller_type)
        val expected = generateOrderResponse()

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `processing order request returns null`() = runBlockingTest {
        val callerOrderContext = generateCallerOrderContext()

        val fetchFromOmsMock = spyk(fetchFromOMS, recordPrivateCalls = true)

        coEvery {
            fetchFromOmsMock["performGetRequest"](allAny<String>())
        } returns ""

        val response = fetchFromOmsMock.processOrderRequest(callerOrderContext.caller_id, callerOrderContext.caller_type)
        val expected = null

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }
    @Test
    fun `processing city request returns CityResponse`() = runBlockingTest {
        val did = getDefaultDid()

        val fetchFromOmsMock = spyk(fetchFromOMS, recordPrivateCalls = true)

        coEvery {
            fetchFromOmsMock["performGetRequest"](allAny<String>())
        } returns "{\"status\":200,\"msg\":\"success\",\"response\":{\"name\":\"unknown\",\"language\":\"hindi\"}}"

        val response = fetchFromOmsMock.processCityRequest(did)
        val expected = generateCityResponse()

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }
    @Test
    fun `processing city request returns null`() = runBlockingTest {
        val did = getDefaultDid()
        val fetchFromOmsMock = spyk(fetchFromOMS, recordPrivateCalls = true)

        coEvery {
            fetchFromOmsMock["performGetRequest"](allAny<String>())
        } returns ""

        val response = fetchFromOmsMock.processCityRequest(did)
        val expected = null

        println("expected -> $expected\nhash ->${expected.hashCode()}\nactual -> $response\nhash -> ${response.hashCode()}")

        assertTrue(ReflectionEquals(expected).matches(response))
    }

}