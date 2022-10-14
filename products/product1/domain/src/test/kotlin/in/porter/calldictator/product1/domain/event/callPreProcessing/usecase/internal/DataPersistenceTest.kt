package `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal

import com.fasterxml.jackson.databind.ObjectMapper
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.repo.ConversationRepo
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCall
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerResponse
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateCallerResponseOutputContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.helpers.Generators.generateUserCallContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.usecase.internal.mapper.RepoMapper
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class DataPersistenceTest {
    private val repoMapper: RepoMapper = RepoMapper(ObjectMapper())
    private val conversationRepoMock: ConversationRepo = mockk()
    private val dataPersistence = DataPersistence(repoMapper, conversationRepoMock)

    @BeforeEach
    fun init() {
        clearAllMocks()
    }

    @Test
    fun `persist call data test`() = runBlockingTest {
        val callerContext = generateCallerContext()
        val call = generateCall()

        coEvery {
            conversationRepoMock.createCallRecord(any())
        } returns call

        val response = dataPersistence.persistCallData(callerContext)
        val expected = call

        assertTrue(ReflectionEquals(expected).matches(response))
    }

    @Test
    fun `persist call processed data test`() = runBlockingTest {
        val callerContext = generateCallerContext()
        val userCallContext = generateUserCallContext()
        val callerResponseOutputContext = generateCallerResponseOutputContext()
        val callerResponse = generateCallerResponse(callerContext, userCallContext, callerResponseOutputContext)
        val call = generateCall()

        coEvery {
            conversationRepoMock.createCallContextRecord(any())
        } returns Unit

        coEvery {
            conversationRepoMock.createCallHandlingRecord(any())
        } returns Unit

        val response = dataPersistence.persistCallProcessedData(callerResponse, call)
        val expected = Unit

        assertTrue(ReflectionEquals(expected).matches(response))
    }
}