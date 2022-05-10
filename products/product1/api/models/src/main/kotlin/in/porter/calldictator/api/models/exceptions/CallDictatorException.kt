package `in`.porter.calldictator.api.models.exceptions

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties("stack_trace", "cause", "suppressed", "localized_message")
open class CallDictatorException(override val message: String) : Exception(message)
