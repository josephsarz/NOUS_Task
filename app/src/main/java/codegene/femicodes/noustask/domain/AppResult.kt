package codegene.femicodes.noustask.domain

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

data class AppResult<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): AppResult<T> {
            return AppResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): AppResult<T> {
            return AppResult(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): AppResult<T> {
            return AppResult(Status.LOADING, data, null)
        }
    }
}