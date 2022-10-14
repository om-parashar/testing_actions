package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.clients.CallContextProviderClient
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCityContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCustomerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateDriverContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateOrderContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateUserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper.CallContextMapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import kotlin.test.assertTrue

class ProcessCallContextTest {
    private val callContextProviderClientMock: CallContextProviderClient = mockk()
    private val callContextMapper = CallContextMapper()
    private val processCallContext = ProcessCallContext(callContextProviderClientMock, callContextMapper)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    // test cases
    // partner
    // customer
    // unknown

    @Test
    fun `fetching feature context for partner`() = runBlockingTest {
        val callerContext = generateCallerContext()
        val orderContext = generateOrderContext()
        coEvery {
            callContextProviderClientMock.fetchDriverContext(any())
        } returns generateDriverContext()

        coEvery {
            callContextProviderClientMock.fetchOrderContext(any())
        } returns generateOrderContext()

        val response = processCallContext.invoke(callerContext)
        val expected = generateUserCallContext(id = 0, uuid = "test", loginStatus = "test", vehicleInfo = "test", isSuspended = false, userType = "partner", order = orderContext)

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching feature context for customer`() = runBlockingTest {
        val callerContext = generateCallerContext()
        val orderContext = generateOrderContext()
        coEvery {
            callContextProviderClientMock.fetchDriverContext(any())
        } returns null

        coEvery {
            callContextProviderClientMock.fetchCustomerContext(any())
        } returns generateCustomerContext()

        coEvery {
            callContextProviderClientMock.fetchCityContext(any())
        } returns generateCityContext()

        coEvery {
            callContextProviderClientMock.fetchOrderContext(any())
        } returns generateOrderContext()

        val response = processCallContext.invoke(callerContext)
        val expected = generateUserCallContext(id = 0, uuid = "test", userType = "customer", order = orderContext)

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `fetching feature context for unknown caller`() = runBlockingTest {
        val callerContext = generateCallerContext()
        val orderContext = generateOrderContext()
        coEvery {
            callContextProviderClientMock.fetchDriverContext(any())
        } returns null

        coEvery {
            callContextProviderClientMock.fetchCustomerContext(any())
        } returns null

        coEvery {
            callContextProviderClientMock.fetchCityContext(any())
        } returns generateCityContext()

        val response = processCallContext.invoke(callerContext)
        val expected = generateUserCallContext(userType = "unknown")

        assertTrue(ReflectionEquals(expected).matches(response))
    }
}
