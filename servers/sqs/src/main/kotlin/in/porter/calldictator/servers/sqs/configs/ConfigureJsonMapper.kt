package `in`.porter.calldictator.servers.sqs.configs

import `in`.porter.kotlinutils.serde.jackson.custom.DurationSerde
import `in`.porter.kotlinutils.serde.jackson.custom.InstantSerde
import `in`.porter.kotlinutils.serde.jackson.custom.MoneySerde
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.KotlinModule

fun ObjectMapper.customConfigure() {
  propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
  configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  registerModules(KotlinModule(), DurationSerde.millisModule, InstantSerde.millisModule, MoneySerde.moneyModule)
}
