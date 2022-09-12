package `in`.porter.calldictator.servers.ktor.external.app

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import `in`.porter.calldictator.servers.ktor.external.callDictator.callDictatorRoutes
import `in`.porter.calldictator.servers.ktor.external.di.HttpComponentFactory
import `in`.porter.calldictator.servers.ktor.external.support.supportContactRoutes
import `in`.porter.kotlinutils.instrumentation.sentryktorfeature.SentryKtorFeature
import `in`.porter.kotlinutils.serde.jackson.custom.*
import `in`.porter.kotlinutils.webserver.ktor.features.calltracing.CallTracingFeature
import `in`.porter.kotlinutils.webserver.ktor.features.doublereceive.PorterDoubleReceive
import `in`.porter.kotlinutils.webserver.ktor.features.timeout.TimeoutFeature
import `in`.porter.kotlinutils.webserver.ktor.features.tracingids.requestId
import `in`.porter.kotlinutils.webserver.ktor.features.tracingids.traceId
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import java.time.Duration

@KtorExperimentalAPI
fun Application.main() {

  install(ContentNegotiation) {
    jackson {
      propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
      configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true)
      registerModules(
        KotlinModule(),
        InstantSerde.millisModule,
        LocalDateSerde.localDateModule,
        LocalTimeSerde.localTimeSerde,
        DurationSerde.millisModule,
        MoneySerde.moneyModule,
        UrlSerde.urlModule
      )
    }
  }

  install(XForwardedHeaderSupport)

  install(PorterDoubleReceive)

  install(CallTracingFeature)

  install(TimeoutFeature) {
    duration = Duration.ofSeconds(20)
  }

  install(SentryKtorFeature) {
    sentryTagsProvider = {
      val tags = mutableListOf<SentryKtorFeature.SentryTag>()
      it.traceId?.also { traceId ->
        tags.add(SentryKtorFeature.SentryTag("traceId", traceId))
      }
      it.requestId?.also { requestId ->
        tags.add(SentryKtorFeature.SentryTag("requestId", requestId))
      }

      tags
    }
  }

  install(StatusPages) {
    @Suppress("EXPERIMENTAL_API_USAGE")
    exception<MissingRequestParameterException> {
      call.respond(HttpStatusCode.BadRequest, mapOf("message" to "Request parameter ${it.parameterName} is missing"))
      throw it
    }

    exception<JsonProcessingException> {
      call.respond(HttpStatusCode.BadRequest, mapOf("message" to it.originalMessage))
      throw it
    }
  }

  val httpComponent = HttpComponentFactory.build()

  routing {
    get("/") { call.respond(HttpStatusCode.OK, Unit) }
    route("/api/v1/call") { callDictatorRoutes(httpComponent) }
    route("/api/v1/support") { supportContactRoutes(httpComponent) }
  }
}
