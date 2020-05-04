package codegene.femicodes.noustask.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("items")
	val items: List<User> = emptyList()
)

@Entity(tableName = "users")
data class User(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@PrimaryKey
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(imageUrl)
		parcel.writeString(description)
		parcel.writeValue(id)
		parcel.writeString(title)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<User> {
		override fun createFromParcel(parcel: Parcel): User {
			return User(parcel)
		}

		override fun newArray(size: Int): Array<User?> {
			return arrayOfNulls(size)
		}
	}
}
