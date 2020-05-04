package codegene.femicodes.noustask.repository

import codegene.femicodes.noustask.domain.BaseDataSource
import codegene.femicodes.noustask.remote.AppService
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(private val service: AppService) :
    BaseDataSource() {

    suspend fun fetchUsers() = getResult { service.getUsers() }

}