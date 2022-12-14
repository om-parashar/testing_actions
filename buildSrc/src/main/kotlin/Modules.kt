object Modules {

  object CALLDICTATOR {
    const val domain = ":domain"

    object Data {
      private const val data = ":data"

      const val psql = "$data-psql"
    }

    object Api {
      private const val api = ":api"

      const val models = "$api-models"
      const val service = "$api-service"
    }
  }

  object support {
    const val domain = ":support-domain"

    object Api {
      private const val api = ":support-api"

      const val models = "$api-models"
      const val service = "$api-service"
    }
  }

  object Providers {
    const val rheoConnect = ":rheo-connection"
    const val omsConnect = ":oms-connection"
    const val utils = ":utils"
  }


  object Servers {
    const val commons = ":server-commons"
    const val client = ":client"
  }

}
