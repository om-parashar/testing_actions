package `in`.porter.support.domain.internal

import `in`.porter.support.domain.clients.SupportContactProviderClient
import `in`.porter.support.domain.entities.SupportContactInfoRequest
import `in`.porter.support.domain.entities.rheo.Constants
import `in`.porter.support.domain.entities.rheo.RheoStringResponseContext
import `in`.porter.support.domain.usecase.internal.GetSupportContactEmail
import `in`.porter.support.domain.usecase.internal.helper.RheoContextAttributeGenerator
import `in`.porter.support.domain.usecase.internal.helper.RheoRequestContextGenerator
import `in`.porter.support.domain.usecase.internal.mappers.DomainXRheoMapper
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetSupportContactEmailTest {
    private val domainXRheoMapper = DomainXRheoMapper(RheoContextAttributeGenerator(), RheoRequestContextGenerator())
    private val supportContactProviderClientMock: SupportContactProviderClient = mockk(relaxUnitFun = true)
    private val getSupportContactEmail = GetSupportContactEmail(domainXRheoMapper, supportContactProviderClientMock)

    @BeforeEach
    fun init() {
        clearMocks(supportContactProviderClientMock)
    }

    private fun getRheoSupportEmailVariation(caller_type: String?): Map<String, String>{
        val helpEmail = mapOf("email" to "help@porter.in")
        return when(caller_type){
            "customer", "partner", "unknown" -> return helpEmail
            else -> mapOf()
        }
    }


    private fun generateSupportContactInfoRequest(
        caller_type: String,
        geo_region: String,
        get_phone: Boolean = true,
        get_email: Boolean = true,
        user_id: String = "test",
        vertical: String = "spot"): SupportContactInfoRequest{
        return SupportContactInfoRequest(
            caller_type = caller_type,
            geo_region = geo_region,
            get_phone = get_phone,
            get_email = get_email,
            user_id = user_id,
            vertical = vertical
        )
    }

    private fun generateRheoStringResponseContext(result: Map<String, String>, status: Boolean = true, msg: String = ""): RheoStringResponseContext{
        return RheoStringResponseContext(status, msg, result)
    }


    @Test
    fun `get email for customer`() {
        runBlockingTest {
            coEvery {
                supportContactProviderClientMock.getSupportEmailContext(any())
            } returns generateRheoStringResponseContext(getRheoSupportEmailVariation("customer"))

            val expexted = "help@porter.in"
            val response = getSupportContactEmail.invoke(
                generateSupportContactInfoRequest(
                    "customer",
                    "Bangalore"
                )
            )

            coVerify { supportContactProviderClientMock.getSupportEmailContext(any()) }
            assertEquals(expexted, response)
        }
    }

    @Test
    fun `get email for partner when Rheo returns empty context`() {
        runBlockingTest {
            coEvery {
                supportContactProviderClientMock.getSupportEmailContext(any())
            } returns generateRheoStringResponseContext(getRheoSupportEmailVariation(null))

            val expexted = Constants.DEFAULT_SUPPORT_EMAIL
            val response = getSupportContactEmail.invoke(
                generateSupportContactInfoRequest(
                    "partner",
                    "Bangalore"
                )
            )

            coVerify { supportContactProviderClientMock.getSupportEmailContext(any()) }
            assertEquals(expexted, response)
        }
    }

    @Test
    fun `get email for partner when Rheo is down`() {
        runBlockingTest {
            coEvery {
                supportContactProviderClientMock.getSupportEmailContext(any())
            } throws Exception()

            val expexted = Constants.DEFAULT_SUPPORT_EMAIL
            val response = getSupportContactEmail.invoke(
                generateSupportContactInfoRequest(
                    "partner",
                    "Bangalore"
                )
            )

            coVerify { supportContactProviderClientMock.getSupportEmailContext(any()) }
            assertEquals(expexted, response)
        }
    }
}
