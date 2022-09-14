import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id(Plugins.kotlinJvm) version Libs.kotlinVersion apply false
  id(Plugins.kotlinKover) version Plugins.kotlinKoverVersion
}

allprojects {
  repositories {
    mavenCentral()
    jcenter()

    maven {
      url = uri("https://repos.spring.io/plugins-release")
    }


    maven {
      url = uri("s3://porter-maven/releases")
      authentication {
        create<AwsImAuthentication>("awsIm")
      }
    }
  }
}

subprojects {
  group = "in.porter.calldictator"
  version = "0.1.0"

  apply(plugin = Plugins.kotlinJvm)
  apply(plugin = Plugins.kotlinKover)

  dependencies {
    "implementation"(Libs.KotlinUtils.commons)
    "testImplementation"(Libs.KotlinUtils.testing)
  }

  kover{
    verify{
      rule{
        isEnabled = true
        name = "Line Coverage of Tests must be more than 80%"
        overrideClassFilter{
          excludes += listOf(
            "**/*_MembersInjector.class",
            "**/Dagger*Component.class",
            "**/Dagger*Component\$Builder.class",
            "**/*_*Factory.class",
          )
        }
        bound {
          minValue = 80
        }
      }
    }
  }

  tasks.withType<Test> {
    @Suppress("UnstableApiUsage")
    useJUnitPlatform()
  }

  tasks.named<KotlinCompile>("compileKotlin") {
    kotlinOptions.jvmTarget = "1.8"
  }

  tasks.named<KotlinCompile>("compileTestKotlin") {
    kotlinOptions.jvmTarget = "1.8"
  }
}
