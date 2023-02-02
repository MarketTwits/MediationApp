sealed class ResponseEvent
data class ResponseSuccess(val result: String) : ResponseEvent()
data class ResponseError(val result: Exception) : ResponseEvent()

