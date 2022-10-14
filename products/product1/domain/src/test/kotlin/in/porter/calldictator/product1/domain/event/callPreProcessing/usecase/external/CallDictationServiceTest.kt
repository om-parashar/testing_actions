package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.external

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.Constants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCall
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerResponse
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCustomerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateDriverContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateOrderContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateUserCallContextFromCustomer
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateUserCallContextFromDriver
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateUserCallContextFromUnknown
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.DataPersistence
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.ProcessCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.ProcessFeatureContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper.CallerResponseMapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import kotlin.test.assertTrue

class CallDictationServiceTest {

    private val callerResponseMapper: CallerResponseMapper = CallerResponseMapper()
    private val processCallContextMock: ProcessCallContext = mockk()
    private val processFeatureContextMock: ProcessFeatureContext = mockk()
    private val dataPersistenceMock: DataPersistence = mockk()
    private val callDictationService = CallDictationService(
                                            processCallContextMock,
                                            processFeatureContextMock,
                                            callerResponseMapper,
                                            dataPersistenceMock
                                        )

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

//    customer | voicebot
//    customer | ivr
//    partner | inbound
//    partner | voicebot
//    unknown | ivr
//    unknown | inbound


    @Test
    fun `testing for customer for voicebot`() {
        runBlockingTest {
            val callerContext = generateCallerContext()
            val customerContext = generateCustomerContext()
            val orderContext = generateOrderContext()
            val userCallContext = generateUserCallContextFromCustomer(callContext = callerContext, customerCallContext =  customerContext, orderContext =  orderContext)
            val callerResponseOutputContext = generateCallerResponseOutputContext(ivr = "vernacular", channel = Constants.INBOUND)

            coEvery {
                processCallContextMock.invoke(any())
            } returns userCallContext

            coEvery {
                processFeatureContextMock.invoke(any())
            } returns callerResponseOutputContext

            coEvery {
                dataPersistenceMock.persistCallData(any())
            } returns generateCall()

            coEvery {
                dataPersistenceMock.persistCallProcessedData(any(), any())
            } returns Unit

            val response = callDictationService.invoke(callerContext)
            val expected = generateCallerResponse(callerContext, userCallContext, callerResponseOutputContext, response.responseTS)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `testing for customer for ivr`() {
        runBlockingTest {
            val callerContext = generateCallerContext()
            val customerContext = generateCustomerContext()
            val orderContext = generateOrderContext()
            val userCallContext = generateUserCallContextFromCustomer(callerContext, customerContext, "english", orderContext)
            val callerResponseOutputContext = generateCallerResponseOutputContext(ivr = "start_trip_vicinity_not_cancelled", channel = Constants.INBOUND)
            coEvery {
                processCallContextMock.invoke(any())
            } returns userCallContext

            coEvery {
                processFeatureContextMock.invoke(any())
            } returns callerResponseOutputContext

            coEvery {
                dataPersistenceMock.persistCallData(any())
            } returns generateCall()

            coEvery {
                dataPersistenceMock.persistCallProcessedData(any(), any())
            } returns Unit

            val response = callDictationService.invoke(callerContext)
            val expected = generateCallerResponse(callerContext, userCallContext, callerResponseOutputContext, response.responseTS)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `testing for partner for inbound`() {
        runBlockingTest {
            val callerContext = generateCallerContext()
            val driverContext = generateDriverContext(language = "hindi")
            val orderContext = generateOrderContext()
            val userCallContext = generateUserCallContextFromDriver(callerContext, driverContext, orderContext)
            val callerResponseOutputContext = generateCallerResponseOutputContext(skillName = "Hindi_Priority2", queueName = "Hindi_accept_vicinity")
            coEvery {
                processCallContextMock.invoke(any())
            } returns userCallContext

            coEvery {
                processFeatureContextMock.invoke(any())
            } returns callerResponseOutputContext

            coEvery {
                dataPersistenceMock.persistCallData(any())
            } returns generateCall()

            coEvery {
                dataPersistenceMock.persistCallProcessedData(any(), any())
            } returns Unit

            val response = callDictationService.invoke(callerContext)
            val expected = generateCallerResponse(callerContext, userCallContext, callerResponseOutputContext, response.responseTS)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `testing for partner for voicebot`() {
        runBlockingTest {
            val callerContext = generateCallerContext()
            val driverContext = generateDriverContext(uuid = "b8c19a9b-5bfc-43f6-a526-918e10a33f4d")
            val orderContext = generateOrderContext(orderStagVicinity = "accept_vicinity")
            val userCallContext = generateUserCallContextFromDriver(callerContext, driverContext, orderContext)
            val callerResponseOutputContext = generateCallerResponseOutputContext(ivr = "rezo", channel = Constants.INBOUND)
            coEvery {
                processCallContextMock.invoke(any())
            } returns userCallContext

            coEvery {
                processFeatureContextMock.invoke(any())
            } returns callerResponseOutputContext

            coEvery {
                dataPersistenceMock.persistCallData(any())
            } returns generateCall()

            coEvery {
                dataPersistenceMock.persistCallProcessedData(any(), any())
            } returns Unit

            val response = callDictationService.invoke(callerContext)
            val expected = generateCallerResponse(callerContext, userCallContext, callerResponseOutputContext, response.responseTS)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `testing for unknown for ivr`() {
        runBlockingTest {
            val callerContext = generateCallerContext()
            val userCallContext = generateUserCallContextFromUnknown(callerContext, "Lucknow", "hindi")
            val callerResponseOutputContext = generateCallerResponseOutputContext(ivr = "unknown_user", channel = Constants.INBOUND, skillName = "Hindi_Priority3", queueName = "Default_Hindi")

            coEvery {
                processCallContextMock.invoke(any())
            } returns userCallContext

            coEvery {
                processFeatureContextMock.invoke(any())
            } returns callerResponseOutputContext

            coEvery {
                dataPersistenceMock.persistCallData(any())
            } returns generateCall()

            coEvery {
                dataPersistenceMock.persistCallProcessedData(any(), any())
            } returns Unit

            val response = callDictationService.invoke(callerContext)
            val expected = generateCallerResponse(callerContext, userCallContext, callerResponseOutputContext, response.responseTS)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

    @Test
    fun `testing for unknown for inbound`() {
        runBlockingTest {
            val callerContext = generateCallerContext()
            val userCallContext = generateUserCallContextFromUnknown(callerContext, "Hyderabad", "Telugu")
            val callerResponseOutputContext = generateCallerResponseOutputContext(ivr = "nor", channel = Constants.INBOUND, skillName = "Telugu_Priority3", queueName = "Default_Telugu")

            coEvery {
                processCallContextMock.invoke(any())
            } returns userCallContext

            coEvery {
                processFeatureContextMock.invoke(any())
            } returns callerResponseOutputContext

            coEvery {
                dataPersistenceMock.persistCallData(any())
            } returns generateCall()

            coEvery {
                dataPersistenceMock.persistCallProcessedData(any(), any())
            } returns Unit

            val response = callDictationService.invoke(callerContext)
            val expected = generateCallerResponse(callerContext, userCallContext, callerResponseOutputContext, response.responseTS)

            assertTrue(ReflectionEquals(expected).matches(response))
        }
    }

}
