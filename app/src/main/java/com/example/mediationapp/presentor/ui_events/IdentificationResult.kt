sealed class IdentificationEvent
data class IdentificationSuccess(val result: String) : IdentificationEvent()
data class IdentificationError(val result: Exception) : IdentificationEvent()

