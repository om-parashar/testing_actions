dependencies {
  implementation(project(Modules.CALLDICTATOR.domain))
  implementation(project(Modules.CALLDICTATOR.Api.models))

  implementation(Libs.KotlinUtils.openTracing)
}
