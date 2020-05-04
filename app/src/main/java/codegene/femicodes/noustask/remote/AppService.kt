package codegene.femicodes.noustask.remote


import codegene.femicodes.noustask.models.UsersResponse
import retrofit2.Response
import retrofit2.http.GET


interface AppService {

    //https://cloud.nousdigital.net/

    @GET("s/Njedq4WpjWz4KKk/download")
    suspend fun getUsers(): Response<UsersResponse>

}