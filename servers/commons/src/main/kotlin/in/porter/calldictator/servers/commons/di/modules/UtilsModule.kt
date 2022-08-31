package `in`.porter.calldictator.servers.commons.di.modules

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import `in`.porter.calldictator.providers.omsconnect.entities.OMSConfig
import `in`.porter.calldictator.servers.commons.extensions.loadResource
import `in`.porter.kotlinutils.commons.config.Environment
import `in`.porter.kotlinutils.serde.commons.SerdeMapper
import `in`.porter.kotlinutils.serde.jackson.custom.*
import `in`.porter.kotlinutils.serde.jackson.json.JacksonSerdeMapperFactory
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.util.*
import org.apache.logging.log4j.kotlin.Logging
import rheoconnect.entities.RheoConfig
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
  fun provideEnvironment(): Environment =
    Properties().loadResource(this::javaClass, "application.properties")
      .getProperty("env")
      .let { Environment.valueOf(it) }

  @Provides
  @Singleton
  fun provideRheoHost(): RheoConfig {
    val properties = Properties().loadResource(this::javaClass, "application.properties")
    return RheoConfig(
      properties.getProperty("rheo.host"),
      properties.getProperty("rheo.feature_toggling")
    )
  }

  @Provides
  @Singleton
  fun provideOMSHost(): OMSConfig {
    val properties = Properties().loadResource(this::javaClass, "application.properties")
    return OMSConfig(
      properties.getProperty("oms.host"),
      properties.getProperty("oms.fetch_driver_api"),
      properties.getProperty("oms.fetch_customer_api"),
      properties.getProperty("oms.fetch_order_api"),
      properties.getProperty("oms.fetch_city_api")
    )
  }
}
