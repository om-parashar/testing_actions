package `in`.porter.calldictator.providers.omsconnect.entities

data class OMSConfig(
    val host: String,
    val fetch_driver_api: String,
    val fetch_customer_api: String,
    val fetch_order_api: String,
    val fetch_city_api: String
)
