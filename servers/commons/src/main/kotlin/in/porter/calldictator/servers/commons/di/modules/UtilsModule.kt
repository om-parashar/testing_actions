package `in`.porter.calldictator.servers.commons.di.modules

import `in`.porter.kotlinutils.commons.config.Environment
import `in`.porter.kotlinutils.serde.jackson.custom.EitherSerde
import `in`.porter.kotlinutils.serde.jackson.custom.InstantSerde
import `in`.porter.kotlinutils.serde.jackson.custom.MoneySerde
import `in`.porter.kotlinutils.serde.jackson.custom.UrlSerde
import `in`.porter.calldictator.servers.commons.extensions.loadResource
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.cio.*
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
  fun provideEnvironment(): Environment =
    Properties().loadResource(this::javaClass, "application.properties")
      .getProperty("env")
      .let { Environment.valueOf(it) }
}
