plugins {
  id(Plugins.kotlinKapt)
}

dependencies {
  implementation(project(Modules.CALLDICTATOR.domain))
  testImplementation(Libs.kotlinTest)

  implementation(Libs.KotlinUtils.openTracing)
  implementation(Libs.KotlinUtils.serdeJackson)
  implementation(Libs.Ktor.clientCore)

  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)
}
