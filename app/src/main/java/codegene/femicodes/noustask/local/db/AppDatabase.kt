package codegene.femicodes.noustask.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import codegene.femicodes.noustask.local.dao.UsersDao
import codegene.femicodes.noustask.models.User


@Database(
    entities = [User::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {


    abstract fun userDao(): UsersDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        ).also { instance = it }
                }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "nous-db")
                .build()
        }
    }
}
