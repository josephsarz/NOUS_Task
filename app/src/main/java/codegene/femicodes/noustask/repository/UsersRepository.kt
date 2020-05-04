package codegene.femicodes.noustask.repository

import codegene.femicodes.noustask.domain.resultLiveData
import codegene.femicodes.noustask.local.dao.UsersDao
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val filterDao: UsersDao,
    private val remote: UsersRemoteDataSource
) {

    fun observeItems() = resultLiveData(
        databaseQuery = { filterDao.getData() },
        networkCall = { remote.fetchUsers() },
        saveCallResult = { filterDao.insertAll(it.items) }
    )


}