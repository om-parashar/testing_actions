// package `in`.porter.support.domain.external

// import `in`.porter.support.domain.entities.SupportContactInfoRequest
// import `in`.porter.support.domain.entities.SupportContactInfoResponse
// import `in`.porter.support.domain.usecase.external.SupportContactInfo
// import `in`.porter.support.domain.usecase.internal.GetSupportContactEmail
// import `in`.porter.support.domain.usecase.internal.GetSupportContactNumber
// import io.mockk.clearMocks
// import io.mockk.coEvery
// import io.mockk.coVerify
// import io.mockk.mockk
// import kotlinx.coroutines.test.runBlockingTest
// import org.junit.jupiter.api.BeforeEach
// import org.junit.jupiter.api.Test
// import org.junit.jupiter.api.TestInstance
// import kotlin.test.assertEquals

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
// class SupportContactInfoTests {
//     private val getSupportContactNumberMock: GetSupportContactNumber = mockk()
//     private val getSupportContactEmailMock: GetSupportContactEmail = mockk()
//     private val supportContactInfo = SupportContactInfo(getSupportContactNumberMock, getSupportContactEmailMock)

//     @BeforeEach
//     fun init() {
//         clearMocks(getSupportContactNumberMock)
//         clearMocks(getSupportContactEmailMock)
//     }

//     private fun generateSupportContactInfoRequest(get_phone: Boolean = true, get_email: Boolean = true): SupportContactInfoRequest =
//         SupportContactInfoRequest(
//             "test",
//             "test",
//             get_phone,
//             get_email,
//             "test",
//             "test"
//         )

//     private fun generateSupportContactInfoResponse(phone: String?, email: String?): SupportContactInfoResponse =
//         SupportContactInfoResponse(
//             phone,
//             email
//         )



//     @Test
//     fun `get both phone number and email`(){
//         runBlockingTest {
//             coEvery {
//                 getSupportContactNumberMock.invoke(any())
//             } returns "+919999999999"

//             coEvery {
//                 getSupportContactEmailMock.invoke(any())
//             } returns "test@porter.in"

//             val expected = generateSupportContactInfoResponse(
//                 "+919999999999",
//                 "test@porter.in"
//             )
//             val response = supportContactInfo.invoke(generateSupportContactInfoRequest(true, true))

//             coVerify {
//                 getSupportContactNumberMock.invoke(any())
//                 getSupportContactEmailMock.invoke(any())
//             }
//             assertEquals(expected, response)
//         }
//     }

//     @Test
//     fun `get only phone number`(){
//         runBlockingTest {
//             coEvery {
//                 getSupportContactNumberMock.invoke(any())
//             } returns "+919999999999"

//             val expected = generateSupportContactInfoResponse(
//                 "+919999999999",
//                 null
//             )
//             val response = supportContactInfo.invoke(generateSupportContactInfoRequest(true, false))

//             coVerify {
//                 getSupportContactNumberMock.invoke(any())
//             }
//             coVerify (exactly = 0) {
//                 getSupportContactEmailMock.invoke(any())
//             }
//             assertEquals(expected, response)
//         }
//     }

//     @Test
//     fun `get only email`(){
//         runBlockingTest {
//             coEvery {
//                 getSupportContactEmailMock.invoke(any())
//             } returns "test@porter.in"

//             val expected = generateSupportContactInfoResponse(
//                 null,
//                 "test@porter.in"
//             )
//             val response = supportContactInfo.invoke(generateSupportContactInfoRequest(false, true))


//             coVerify {
//                 getSupportContactEmailMock.invoke(any())
//             }
//             coVerify (exactly = 0) {
//                 getSupportContactNumberMock.invoke(any())
//             }
//             assertEquals(expected, response)
//         }
//     }

//     @Test
//     fun `get neither phone number nor email`(){
//         runBlockingTest {
//             val expected = generateSupportContactInfoResponse(
//                 null,
//                 null
//             )
//             val response = supportContactInfo.invoke(generateSupportContactInfoRequest(false, false))

//             coVerify (exactly = 0) {
//                 getSupportContactNumberMock.invoke(any())
//                 getSupportContactEmailMock.invoke(any())
//             }
//             assertEquals(expected, response)
//         }
//     }
// }
