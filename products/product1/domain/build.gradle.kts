plugins {
  id(Plugins.kotlinKapt)
}

dependencies {
  implementation(project(Modules.Providers.rheoConnect))
  implementation(project(Modules.Providers.omsConnect))

  implementation(Libs.KotlinUtils.openTracing)
  implementation(Libs.Dagger.dagger)
  implementation(Libs.KotlinUtils.serdeJackson)
}
