plugins {
    id(Plugins.kotlinKapt)
}

dependencies {
    implementation(Libs.KotlinUtils.openTracing)
    implementation(Libs.Dagger.dagger)
    implementation(Libs.KotlinUtils.serdeJackson)
}
