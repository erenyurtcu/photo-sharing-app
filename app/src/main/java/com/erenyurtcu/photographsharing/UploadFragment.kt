package com.erenyurtcu.photographsharing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erenyurtcu.photographsharing.databinding.FragmentUploadBinding
import com.erenyurtcu.photographsharing.databinding.FragmentUserBinding

class UploadFragment : Fragment() {

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}