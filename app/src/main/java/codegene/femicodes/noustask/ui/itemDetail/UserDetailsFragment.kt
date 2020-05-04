package codegene.femicodes.noustask.ui.itemDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import codegene.femicodes.noustask.R
import codegene.femicodes.noustask.models.User
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user_details.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserDetailsFragment : Fragment() {

    val args: UserDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = args.data

        artwork.apply {
            transitionName = "artwork"
            Glide.with(this)
                .load(item.imageUrl)
                .into(this)
        }

        title.text = item.title
        description.text = item.description

        email_fab.setOnClickListener {
            composeEmail(item)
        }
    }

    private fun composeEmail(item: User) {
        val imageFile = generateAttachment(item.imageUrl)
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT, item.title)
            putExtra(Intent.EXTRA_TEXT, item.description)
        }
        if(imageFile != null){
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile))
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }

    //Downloads the image and save it as a file in the device
    private fun generateAttachment(imageUrl: String?) : File? {
        try {
            val rootSdDirectory: File = Environment.getExternalStorageDirectory()
            val pictureFile = File(rootSdDirectory, "attachment.jpg")
            if (pictureFile.exists()) {
                pictureFile.delete()
            }
            pictureFile.createNewFile()
            val fos = FileOutputStream(pictureFile)
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doOutput = true
            connection.connect()
            val `in`: InputStream = connection.inputStream
            val buffer = ByteArray(1024)
            var size = 0
            while (`in`.read(buffer).also { size = it } > 0) {
                fos.write(buffer, 0, size)
            }
            fos.close()
            return pictureFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}