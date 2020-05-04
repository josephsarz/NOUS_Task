package codegene.femicodes.noustask.domain

import retrofit2.Response
import timber.log.Timber

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): AppResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return AppResult.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): AppResult<T> {
        Timber.e(message)
        return AppResult.error("Network call has failed for a following reason: $message")
    }

}

