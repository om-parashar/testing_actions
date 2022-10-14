package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.Constants
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateInboundResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateIvrResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper.CallerResponseOutputContextMapper
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateUserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateVoicebotResponseOutputContext
import io.mockk.clearAllMocks
import org.junit.jupiter.api.BeforeEach
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import kotlin.reflect.jvm.internal.impl.load.java.Constant
import kotlin.test.assertTrue

class ProcessFeatureContextTest {
    private val voicebotContextMock: VoicebotContext = mockk()
    private val ivrContextMock: IvrContext = mockk()
    private val inboundContextMock: InboundContext = mockk()
    private val callerResponseOutputContextMapper = CallerResponseOutputContextMapper()

    private val processFeatureContext = ProcessFeatureContext(voicebotContextMock, ivrContextMock, inboundContextMock, callerResponseOutputContextMapper)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    // test cases
    // voicebot
    // ivr
    // inbound

    @Test
    fun `testing feature context test for voicebot`() = runBlockingTest {
        val orderContext = Generators.generateOrderContext()
        val userCallContext = generateUserCallContext(order = orderContext)
        coEvery {
            voicebotContextMock.fetch(userCallContext)
        } returns generateVoicebotResponseOutputContext()

        val response = processFeatureContext.invoke(userCallContext)
        val expected = generateCallerResponseOutputContext(queueName = null, skillName = null)

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `testing feature context test for ivr`() = runBlockingTest {
        val orderContext = Generators.generateOrderContext()
        val userCallContext = generateUserCallContext(order = orderContext)

        coEvery {
            voicebotContextMock.fetch(userCallContext)
        } returns null

        coEvery {
            ivrContextMock.fetch(any())
        } returns generateIvrResponseOutputContext()

        val response = processFeatureContext.invoke(userCallContext)
        val expected = generateCallerResponseOutputContext()

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `testing feature context test for inbound`() = runBlockingTest {
        val orderContext = Generators.generateOrderContext()
        val userCallContext = generateUserCallContext(order = orderContext)

        coEvery {
            voicebotContextMock.fetch(userCallContext)
        } returns null

        coEvery {
            ivrContextMock.fetch(any())
        } returns null

        coEvery {
            inboundContextMock.fetch(any())
        } returns generateInboundResponseOutputContext()

        val response = processFeatureContext.invoke(userCallContext)
        val expected = generateCallerResponseOutputContext(ivr = null, channel = Constants.INBOUND)

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `testing feature context test for null`() = runBlockingTest {
        val orderContext = Generators.generateOrderContext()
        val userCallContext = generateUserCallContext(order = orderContext)

        coEvery {
            voicebotContextMock.fetch(userCallContext)
        } returns null

        coEvery {
            ivrContextMock.fetch(any())
        } returns null

        coEvery {
            inboundContextMock.fetch(any())
        } returns null

        val response = processFeatureContext.invoke(userCallContext)
        val expected = null

        assertTrue(ReflectionEquals(expected).matches(response))
    }

}
