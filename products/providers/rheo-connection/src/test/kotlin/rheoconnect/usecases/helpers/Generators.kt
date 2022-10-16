package rheoconnect.usecases.helpers

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import `in`.porter.kotlinutils.serde.jackson.custom.*
import `in`.porter.kotlinutils.serde.jackson.json.JacksonSerdeMapperFactory
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoRequestContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.RheoResponseContext
import `in`.porter.support.domain.entities.rheo.RheoRequestContext as SupportRheoRequestContext
import `in`.porter.calldictator.product1.domain.event.callPreProcessing.entities.rheo.ContextAttribute
import `in`.porter.support.domain.entities.rheo.RheoMapResponseContext
import `in`.porter.support.domain.entities.rheo.RheoStringResponseContext
import rheoconnect.entities.*
import `in`.porter.support.domain.entities.rheo.ContextAttribute as SupportContextAttribute

object Generators {

    fun generateSerdeMapper() = JacksonSerdeMapperFactory().build().apply {
        this.configure {
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            registerModules(
                KotlinModule(),
                InstantSerde.millisModule,
                MoneySerde.moneyModule,
                LocalDateSerde.localDateModule,
                LocalTimeSerde.localTimeSerde,
                DurationSerde.millisModule)
        }
    }
    fun generateRheoConfig() = RheoConfig("test", "test")

    fun getDefaultfeatureName() = "test"
    fun getDefaultUserId() = "test"
    fun getDefaultStatus() = true
    fun getDefaultMessage() = (RheoMessage.FLAG_ON_TARGET_MATCH).toString()
    fun generateDefaultResultMap() = mapOf<String, String>()
    fun generateDefaultResultMapOfMap() = mapOf<String, Map<String, String>>()

    fun generateRheoContextAttrs() = listOf(RheoContextAttribute("test", "String", "test"))

    fun generateRheoInput(featureName: String = getDefaultfeatureName(), userId: String = getDefaultUserId(), contextAttrs: List<RheoContextAttribute> = generateRheoContextAttrs()) =
        RheoInput(featureName, userId, contextAttrs)


    fun generateRheoResponse(flagStatus: Boolean = true, msg: RheoMessage = RheoMessage.FLAG_ON_TARGET_MATCH, context: String? = null) =
        RheoResponse(flagStatus, msg, context)



    fun generateContextAttrs(): List<ContextAttribute>?{
        return null
    }

    fun generateRheoRequestContext(featureName: String = getDefaultfeatureName(),
                                   userId: String = getDefaultUserId(),
                                   contextAttrs: List<ContextAttribute>? = generateContextAttrs()
    ) =
        RheoRequestContext(featureName, userId, contextAttrs)


    fun generateSupportContextAttrs(): List<SupportContextAttribute>{
        return listOf(SupportContextAttribute.StringType("test", "test"))
    }

    fun generateSupportRheoRequestContext(featureName: String = getDefaultfeatureName(),
                                   userId: String = getDefaultUserId(),
                                   contextAttrs: List<SupportContextAttribute> = generateSupportContextAttrs()
    ) =
        SupportRheoRequestContext(featureName, userId, contextAttrs)


    fun generateRheoResponseContext(status: Boolean = getDefaultStatus(),
                                    msg: String = getDefaultMessage(),
                                    result: Map<String, String> = generateDefaultResultMap()
    ) =
        RheoResponseContext(status, msg, result)

    fun generateRheoStringResponseContext(status: Boolean = getDefaultStatus(),
                                    msg: String = getDefaultMessage(),
                                    result: Map<String, String> = generateDefaultResultMap()
    ) =
        RheoStringResponseContext(status, msg, result)

    fun generateRheoMapResponseContext(status: Boolean = getDefaultStatus(),
                                          msg: String = getDefaultMessage(),
                                          result: Map<String, Map<String, String>> = generateDefaultResultMapOfMap()
    ) =
        RheoMapResponseContext(status, msg, result)

    fun getRawRheoResponseContextString() = "{\"Ahmedabad\":{\"country_code\":\"+91\",\"number\":\"2244104406\"},\"Lucknow\":{\"country_code\":\"+91\",\"number\":\"2244104420\"},\"Chandigarh\":{\"country_code\":\"+91\",\"number\":\"2244104425\"},\"Kolkata\":{\"country_code\":\"+91\",\"number\":\"2244104408\"},\"Surat\":{\"country_code\":\"+91\",\"number\":\"2244104406\"},\"Indore\":{\"country_code\":\"+91\",\"number\":\"2244104423\"},\"Mumbai\":{\"country_code\":\"+91\",\"number\":\"2262684410\"},\"Hyderabad\":{\"country_code\":\"+91\",\"number\":\"2262684403\"},\"Bangalore\":{\"country_code\":\"+91\",\"number\":\"2262684405\"},\"unknown\":{\"country_code\":\"+91\",\"number\":\"2262684410\"},\"Coimbatore\":{\"country_code\":\"+91\",\"number\":\"2244104421\"},\"Vadodara\":{\"country_code\":\"+91\",\"number\":\"2244104426\"},\"Chennai\":{\"country_code\":\"+91\",\"number\":\"2244104404\"},\"Kochi\":{\"country_code\":\"+91\",\"number\":\"2244104428\"},\"Jaipur\":{\"country_code\":\"+91\",\"number\":\"2244104409\"},\"Nagpur\":{\"country_code\":\"+91\",\"number\":\"2244104424\"},\"Ludhiana\":{\"country_code\":\"+91\",\"number\":\"2244104427\"},\"Pune\":{\"country_code\":\"+91\",\"number\":\"2262684407\"},\"Delhi NCR\":{\"country_code\":\"+91\",\"number\":\"2262684401\"}}"

    fun getRawRheoNumberResponseString() = "{\"flag_status\":true,\"msg\":\"FLAG_ON_TARGET_MATCH\",\"context\":\"{\\\"Ahmedabad\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104406\\\"},\\\"Lucknow\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104420\\\"},\\\"Chandigarh\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104425\\\"},\\\"Kolkata\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104408\\\"},\\\"Surat\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104406\\\"},\\\"Indore\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104423\\\"},\\\"Mumbai\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2262684410\\\"},\\\"Hyderabad\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2262684403\\\"},\\\"Bangalore\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2262684405\\\"},\\\"unknown\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2262684410\\\"},\\\"Coimbatore\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104421\\\"},\\\"Vadodara\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104426\\\"},\\\"Chennai\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104404\\\"},\\\"Kochi\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104428\\\"},\\\"Jaipur\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104409\\\"},\\\"Nagpur\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104424\\\"},\\\"Ludhiana\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2244104427\\\"},\\\"Pune\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2262684407\\\"},\\\"Delhi NCR\\\":{\\\"country_code\\\":\\\"+91\\\",\\\"number\\\":\\\"2262684401\\\"}}\"}"

    fun getRawRheoIVRResponseString() = "{\"ivr\":\"start_trip_vicinity_not_cancelled\",\"channel\":\"inbound\"}"
}