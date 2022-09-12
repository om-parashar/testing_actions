rootProject.name = "calldictator"

include(":domain")
project(":domain").projectDir = File("products/product1/domain")

include("data-psql")
project(":data-psql").projectDir = File("products/product1/data/psql")

include("api-models")
project(":api-models").projectDir = File("products/product1/api/models")

include("api-service")
project(":api-service").projectDir = File("products/product1/api/service")

include(":support-domain")
project(":support-domain").projectDir = File("products/support/domain")

include("support-api-models")
project(":support-api-models").projectDir = File("products/support/api/models")

include("support-api-service")
project(":support-api-service").projectDir = File("products/support/api/service")

include(":server-commons")
project(":server-commons").projectDir = File("servers/commons")

include(":ktor-server")
project(":ktor-server").projectDir = File("servers/ktor")

include(":ktor-external")
project(":ktor-external").projectDir = File("servers/ktor-external")

include(":sqs-server")
project(":sqs-server").projectDir = File("servers/sqs")

include(":client")
project(":client").projectDir = File("servers/client")

include(":oms-connection")
project(":oms-connection").projectDir = File("products/providers/oms-connection")

include(":rheo-connection")
project(":rheo-connection").projectDir = File("products/providers/rheo-connection")

include(":utils")
project(":utils").projectDir = File("products/providers/utils")

//includeBuild("../kotlin-utils")
