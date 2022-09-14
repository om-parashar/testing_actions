plugins {
  id(Plugins.kotlinKapt)
  id(Plugins.mavenPublish)
}

dependencies {
  api(project(Modules.CALLDICTATOR.Api.models))
  implementation(Libs.KotlinUtils.serdeJackson)
  implementation(Libs.KotlinUtils.awsSqs)

  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)

}
val sourceJar = task("sourceJar", Jar::class) {
  dependsOn(JavaPlugin.CLASSES_TASK_NAME)
  archiveClassifier.set("sources")
  from(project.the<SourceSetContainer>()["main"].allSource)
}


publishing {
  publications {
    create<MavenPublication>(name) {
      artifactId = "calldictator-client"

      from(components["java"])
      artifact(sourceJar)
    }
  }

  repositories {
    maven {
      url = uri("s3://porter-maven/releases")
      authentication {
        create<AwsImAuthentication>("awsIm")
      }
    }
  }
}
