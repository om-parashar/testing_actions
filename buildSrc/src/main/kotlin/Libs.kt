object Libs {
  const val kotlinVersion = "1.3.70"

  object KotlinUtils {
    private const val group = "in.porter.kotlinutils"
    private const val version = "0.47.1-MANI-TEST-V1"

    const val serdeJackson = "$group:serde-jackson:$version"
    const val commons = "$group:commons:$version"
    const val exposed = "$group:exposed:$version"
    const val testing = "$group:testing:$version"
    const val openTracing = "$group:opentracing:$version"
    const val sentryKtorFeature = "$group:sentry-ktor-feature:$version"
    const val ktorWebServer = "$group:webserver-ktor:$version"
    const val awsSqs = "$group:aws-sqs:$version"
    const val awsS3 = "$group:aws-s3:$version"
    const val geos = "$group:geos:$version"
  }

  object Dagger {
    private const val group = "com.google.dagger"
    private const val version = "2.27"

    const val dagger = "$group:dagger:$version"
    const val compiler = "$group:dagger-compiler:$version"
  }

  object Ktor {
    private const val version = "1.3.1"
    private const val group = "io.ktor"

    const val clientCore = "$group:ktor-client-core-jvm:$version"
    const val clientCio = "$group:ktor-client-cio:$version"
    const val clientJson = "$group:ktor-client-json:$version"
    const val clientJackson = "$group:ktor-client-jackson:$version"

    const val serverCore = "$group:ktor-server-core:$version"
    const val serverNetty = "$group:ktor-server-netty:$version"
    const val serverJackson = "$group:ktor-jackson:$version"
  }

  object Log4j {
    private const val version = "2.16.0"
    private const val group = "org.apache.logging.log4j"

    const val core = "$group:log4j-core:$version"
    const val slf4jImpl = "$group:log4j-slf4j-impl:$version"
  }

  object Testing {
    private const val testContainersGroup = "org.testcontainers"
    private const val testContainersVersion = "1.12.3"
    const val testContainers = "$testContainersGroup:testcontainers:$testContainersVersion"
    const val testContainersPostgresql = "$testContainersGroup:postgresql:$testContainersVersion"
    const val testContainersJuniper = "$testContainersGroup:junit-jupiter:$testContainersVersion"
  }

  const val caffeine = "com.github.ben-manes.caffeine:caffeine:2.8.0"
  const val hikariCP = "com.zaxxer:HikariCP:3.4.1"
  const val sentry = "io.sentry:sentry:1.7.27"
  const val postgresql = "org.postgresql:postgresql:42.2.8"
  const val elasticEcs = "co.elastic.logging:log4j2-ecs-layout:0.5.0"
  const val shadowTransformer = "com.github.jengelman.gradle.plugins:shadow:2.0.2"
  const val money = "org.javamoney:moneta:1.4.2"

  object Micrometer {
    private const val group = "io.micrometer"
    private const val version = "1.5.3"

    const val core = "$group:micrometer-core:$version"
    const val cloudwatch = "$group:micrometer-registry-cloudwatch2:$version"
  }

}
