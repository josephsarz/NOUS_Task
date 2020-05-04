package codegene.femicodes.noustask.domain

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    @TypeConverter
    fun stringToList(ids: List<String>): String {
        val json = Gson().toJson(ids)
        return json
    }

    @TypeConverter
    fun listToString(json: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, listType)
    }
}