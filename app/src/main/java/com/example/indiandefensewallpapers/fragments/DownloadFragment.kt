import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.indiandefensewallpapers.ImageModel
import com.example.indiandefensewallpapers.adpater.DownloadAdapter
import com.example.indiandefensewallpapers.databinding.FragmentDownloadBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DownloadFragment : Fragment() {

    private lateinit var binding: FragmentDownloadBinding
    private val images = mutableListOf<ImageModel>()
    private lateinit var imageAdapter: DownloadAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)

        imageAdapter = DownloadAdapter(requireContext(), images)
        binding.rcvDownload.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcvDownload.adapter = imageAdapter

        loadDownloadedImages()

        return binding.root
    }

    private fun loadDownloadedImages() {
        lifecycleScope.launch(Dispatchers.IO) {
            val imageList = mutableListOf<ImageModel>()
            val projection = arrayOf(
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID
            )
            val selection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
            } else {
                "${MediaStore.Images.Media.DATA} LIKE ?"
            }
            val selectionArgs = arrayOf("%IndianDefenseWallpapers%")
            val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            val query = requireContext().contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
            )

            query?.use { cursor ->
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                while (cursor.moveToNext()) {
                    val name = cursor.getString(nameColumn)
                    val id = cursor.getLong(idColumn)
                    val uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    Log.d("DownloadFragment", "Image found: $name, URI: $uri") // Debug log
                    imageList.add(ImageModel(name, uri))
                }
            }

            withContext(Dispatchers.Main) {
                if (imageList.isEmpty()) {
                    Log.d("DownloadFragment", "No images found in the specified directory.")
                }
                images.clear()
                images.addAll(imageList)
                imageAdapter.notifyDataSetChanged()
            }
        }
    }
}
