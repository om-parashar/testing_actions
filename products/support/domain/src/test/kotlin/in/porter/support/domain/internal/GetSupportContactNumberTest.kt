package `in`.porter.support.domain.internal

import `in`.porter.support.domain.clients.SupportContactProviderClient
import `in`.porter.support.domain.entities.SupportContactInfoRequest
import `in`.porter.support.domain.entities.rheo.Constants
import `in`.porter.support.domain.entities.rheo.RheoMapResponseContext
import `in`.porter.support.domain.usecase.internal.GetSupportContactNumber
import `in`.porter.support.domain.usecase.internal.helper.RheoContextAttributeGenerator
import `in`.porter.support.domain.usecase.internal.helper.RheoRequestContextGenerator
import `in`.porter.support.domain.usecase.internal.mappers.DomainXRheoMapper
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GetSupportContactNumberTest {
    private val domainXRheoMapper: DomainXRheoMapper = DomainXRheoMapper(RheoContextAttributeGenerator(), RheoRequestContextGenerator())
    private val supportContactProviderClientMock: SupportContactProviderClient = mockk(relaxUnitFun = true)
    private val getSupportContactNumber = GetSupportContactNumber(domainXRheoMapper, supportContactProviderClientMock)

    @BeforeEach
    fun init() {
        clearMocks(supportContactProviderClientMock)
    }

    private fun getRheoSupportNumberVariation(caller_type: String?): Map<String, Map<String, String>> {
        val spotCustomerAirtel = mapOf(
            "Surat" to mapOf("country_code" to "+91", "number" to "2244104406"),
            "Indore" to mapOf("country_code" to "+91", "number" to "2244104423"),
            "Bangalore" to mapOf("country_code" to "+91", "number" to "2244104405"),
            "unknown" to mapOf("country_code" to "+91", "number" to "2244104410")
        )
        val spotPartnerHybrid = mapOf(
            "Surat" to mapOf("country_code" to "+91", "number" to "2244104406"),
            "Indore" to mapOf("country_code" to "+91", "number" to "2244104423"),
            "Bangalore" to mapOf("country_code" to "+91", "number" to "2262684405"),
            "unknown" to mapOf("country_code" to "+91", "number" to "2262684410")
        )
        val spotUnknownAirtel = mapOf(
            "Surat" to mapOf("country_code" to "+91", "number" to "2244104406"),
            "Indore" to mapOf("country_code" to "+91", "number" to "2244104423"),
            "Bangalore" to mapOf("country_code" to "+91", "number" to "2244104405"),
            "unknown" to mapOf("country_code" to "+91", "number" to "2244104410")
        )
        return when (caller_type) {
            "customer" -> spotCustomerAirtel
            "partner" -> spotPartnerHybrid
            "unknown" -> spotUnknownAirtel
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

    private fun generateRheoMapResponseContext(result: Map<String, Map<String, String>>, status: Boolean = true, msg: String = ""): RheoMapResponseContext {
        return RheoMapResponseContext(status, msg, result)
    }

    @Nested
    inner class CallerTypeCustomer {

        @Test
        fun `get number for customer in Bangalore`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation("customer"))

                val expected = "+912244104405"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "customer",
                        "Bangalore"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for customer in unknown city`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation("customer"))

                val expected = "+912244104410"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "customer",
                        "Qwerty"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for customer in Surat when Rheo returns empty context`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation(null))

                val expected = "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "customer",
                        "Surat"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for customer in Surat when Rheo is down`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } throws Exception()

                val expected = "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "customer",
                        "Surat"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

    }

    @Nested
    inner class CallerTypePartner {

        @Test
        fun `get number for partner in Indore`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation("partner"))

                val expected = "+912244104423"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "partner",
                        "Indore"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for partner in unknown city`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation("partner"))

                val expected = "+912262684410"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "partner",
                        "Qwerty"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for partner in Bangalore when Rheo returns empty context`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation(null))

                val expected = "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "partner",
                        "Bangalore"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for partner in Bangalore when Rheo is down`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } throws Exception()

                val expected = "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "partner",
                        "Bangalore"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }
    }

    @Nested
    inner class CallerTypeUnknown {

        @Test
        fun `get number for unknown caller in Surat`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation("unknown"))

                val expected = "+912244104406"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "unknown",
                        "Surat"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for unknown caller in unknown city`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation("unknown"))

                val expected = "+912244104410"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "unknown",
                        "Qwerty"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for unknown caller in Indore when Rheo returns empty context`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation(null))

                val expected = "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "unknown",
                        "Indore"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for unknown caller in Indore when Rheo is down`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } throws Exception()

                val expected = "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "unknown",
                        "Indore"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }
    }

    @Nested
    inner class RandomTests {

        @Test
        fun `get number for random caller in random city`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } returns generateRheoMapResponseContext(getRheoSupportNumberVariation("Qwerty"))

                val expected = "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "Qwerty",
                        "Qwerty"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }

        @Test
        fun `get number for random caller in random city when Rheo is down`() {
            runBlockingTest {
                coEvery {
                    supportContactProviderClientMock.getSupportNumberContext(any())
                } throws Exception()

                val expected = "${Constants.COUNTRY_CODE_INDIA}${Constants.FALLBACK_CC_NUMBER_INDIA_AIRTEL}"
                val response = getSupportContactNumber.invoke(
                    generateSupportContactInfoRequest(
                        "Qwerty",
                        "Qwerty"
                    )
                )

                coVerify { supportContactProviderClientMock.getSupportNumberContext(any()) }
                assertEquals(expected, response)
            }
        }
    }
}
