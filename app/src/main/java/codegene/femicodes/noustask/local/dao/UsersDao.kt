package codegene.femicodes.noustask.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import codegene.femicodes.noustask.models.User

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(filters: List<User>)

    @Query("SELECT * FROM users")
    fun getData(): LiveData<List<User>>

}
