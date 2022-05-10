import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id(Plugins.kotlinJvm) version Libs.kotlinVersion apply false
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

  dependencies {
    "implementation"(Libs.KotlinUtils.commons)
    "testImplementation"(Libs.KotlinUtils.testing)
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
