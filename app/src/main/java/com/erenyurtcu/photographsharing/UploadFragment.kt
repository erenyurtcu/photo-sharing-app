package com.erenyurtcu.photographsharing

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.erenyurtcu.photographsharing.databinding.FragmentUploadBinding
import com.erenyurtcu.photographsharing.databinding.FragmentUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.api.Context

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedImage : Uri? = null
    var selectedBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLaunchers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.uploadButton.setOnClickListener { upload(it) }
        binding.uploadImageView.setOnClickListener { selectImage(it) }
    }

    fun upload(view: View){

    }

    fun selectImage(view: View){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(view,"Permission is required to enter the gallery.",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }).show()
                } else{
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else{
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                    Snackbar.make(view,"Permission is required to enter the gallery.",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }).show()
                } else{
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else{
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }

        }

    }

    private fun registerLaunchers(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK){
                val intentFromResult = result.data
                if(intentFromResult != null){
                    selectedImage = intentFromResult.data
                    try{
                        if(Build.VERSION.SDK_INT >= 28){
                            val source = ImageDecoder.createSource(requireActivity().contentResolver,selectedImage!!)
                            selectedBitmap = ImageDecoder.decodeBitmap(source)
                            binding.uploadImageView.setImageBitmap(selectedBitmap)
                        } else{
                            selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,selectedImage)
                            binding.uploadImageView.setImageBitmap(selectedBitmap)
                        }
                    } catch(e : Exception){
                            e.printStackTrace()
                    }
                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if(result) {
                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            } else{
                Toast.makeText(requireContext(),"You have been denied permission to access the gallery.",Toast.LENGTH_LONG).show()
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}