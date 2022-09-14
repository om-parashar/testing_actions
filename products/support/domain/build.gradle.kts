plugins {
    id(Plugins.kotlinKapt)
}

dependencies {
    implementation(Libs.KotlinUtils.openTracing)
    implementation(Libs.Dagger.dagger)
    implementation(Libs.KotlinUtils.serdeJackson)
    testImplementation(Libs.Ktor.serverTest)
    testImplementation(Libs.kotlinTest)
}
