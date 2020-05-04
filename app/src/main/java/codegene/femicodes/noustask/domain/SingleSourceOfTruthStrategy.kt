package codegene.femicodes.noustask.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import codegene.femicodes.noustask.domain.AppResult.Status.ERROR
import codegene.femicodes.noustask.domain.AppResult.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [AppResult.Status.SUCCESS] - with data from database
 * [AppResult.Status.ERROR] - if error has occurred from any source
 * [AppResult.Status.LOADING]
 */
fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> AppResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<AppResult<T>> =
    liveData(Dispatchers.IO) {
        emit(AppResult.loading<T>())
        val source = databaseQuery.invoke().map { AppResult.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)
        } else if (responseStatus.status == ERROR) {
            emit(AppResult.error<T>(responseStatus.message!!))
            emitSource(source)
        }
    }

