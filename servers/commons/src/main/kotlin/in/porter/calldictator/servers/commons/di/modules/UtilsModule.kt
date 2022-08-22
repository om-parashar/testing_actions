package `in`.porter.calldictator.servers.commons.di.modules

import `in`.porter.kotlinutils.commons.config.Environment
import `in`.porter.calldictator.servers.commons.extensions.loadResource
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import `in`.porter.kotlinutils.serde.jackson.custom.*
import `in`.porter.kotlinutils.serde.jackson.json.JacksonSerdeMapperFactory
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.util.*
import org.apache.logging.log4j.kotlin.Logging
import java.util.*
import javax.inject.Singleton

@Module
class UtilsModule {

  companion object : Logging

  @KtorExperimentalAPI
  @Provides
  fun provideHttpClient() = HttpClient(CIO) {
    install(HttpTimeout)
    install(JsonFeature) {
      serializer = JacksonSerializer {
        propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        registerModules(KotlinModule(),
          EitherSerde.module,
          InstantSerde.millisModule,
          UrlSerde.urlModule,
          MoneySerde.moneyModule)
      }
    }
  }

  @Provides
  @Singleton
  fun provideSerdeMapper() : SerdeMapper =
    getSerdeMapper()

  private fun getSerdeMapper() =
    JacksonSerdeMapperFactory().build().apply {
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


  @Provides
  @Singleton
  fun provideObjectMapper() = ObjectMapper()
    .apply {
      propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

      registerModule(KotlinModule())
      registerModule(DurationSerde.millisModule)
      registerModule(InstantSerde.millisModule)
      registerModule(ZonedDateTimeMillisSerde.epochMillisModule)
      registerModules(UrlSerde.urlModule)
    }


  @Provides
  @Singleton
  fun provideEnvironment(): Environment =
    Properties().loadResource(this::javaClass, "application.properties")
      .getProperty("env")
      .let { Environment.valueOf(it) }
}
