plugins {
  id(Plugins.kotlinKapt)
}

dependencies {

  implementation(Libs.KotlinUtils.openTracing)
  implementation(Libs.KotlinUtils.serdeJackson)
  implementation(Libs.Ktor.clientCore)

  implementation(Libs.Dagger.dagger)
  kapt(Libs.Dagger.compiler)
}
