package codegene.femicodes.noustask.ui.itemDetail

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import codegene.femicodes.noustask.R
import codegene.femicodes.noustask.models.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_user_details.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class UserDetailsFragment : Fragment() {

    private val REQUEST_PERMISSION_STORAGE = 1100

    val args: UserDetailsFragmentArgs by navArgs()
    var user: User? = null

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

        user = args.data

        artwork.apply {
            transitionName = "artwork"
            Glide.with(this)
                .load(user?.imageUrl)
                .into(this)
        }

        title.text = user?.title
        description.text = user?.description



        email_fab.setOnClickListener {
            checkIfGrantedPermission()
        }
    }

    private fun checkIfGrantedPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            // We have access.
            user?.let { composeEmail(it) }
        } else {

            requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION_STORAGE);
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            user?.let { composeEmail(it) }
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private fun saveImage(image: Bitmap): File? {
        var savedImagePath: String? = null
        val imageFileName = "JPEG_" + "FILE_NAME" + ".jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString()
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        return File(savedImagePath)
    }


    private fun composeEmail(item: User) {

        val imageFile = generateAttachment(item.imageUrl)
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT, item.title)
            putExtra(Intent.EXTRA_TEXT, item.description)
        }
        if (imageFile != null) {
            val uri = Uri.fromFile(imageFile)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
        }
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        }
    }

    //Downloads the image and save it as a file in the device
    private fun generateAttachment(imageUrl: String?): File? {
        var file: File? = null
        Glide.with(artwork.context)
            .asBitmap()
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    file = saveImage(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })

        return file
    }

}