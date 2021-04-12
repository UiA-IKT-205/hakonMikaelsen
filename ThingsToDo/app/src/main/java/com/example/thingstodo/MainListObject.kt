package com.example.thingstodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.thingstodo.databinding.FragmentMainListObjectBinding
import kotlinx.android.synthetic.main.fragment_main_list_object.*
import kotlinx.android.synthetic.main.fragment_main_list_object.view.*


class MainListObject : Fragment() {

    private var _binding: FragmentMainListObjectBinding? = null
    private val binding get() = _binding!!

    private lateinit var listName:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listName = it.getString("NOTE") ?: "?"
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentMainListObjectBinding.inflate(inflater)
        val view = binding.root




        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(listname: String) =
            MainListObject().apply {
                arguments = Bundle().apply {
                    putString("LISTOBJECT", listname)
                }
            }
    }

}