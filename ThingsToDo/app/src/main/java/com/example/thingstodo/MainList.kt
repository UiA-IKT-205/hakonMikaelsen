package com.example.thingstodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thingstodo.databinding.FragmentMainListBinding
import kotlinx.android.synthetic.main.fragment_main_list.view.*
import kotlinx.android.synthetic.main.fragment_main_list_object.*
import kotlinx.android.synthetic.main.fragment_main_list_object.view.*


class MainList : Fragment() {

    private var _binding:FragmentMainListBinding? = null
    private val binding get() = _binding!!

    private val temp = listOf("Aplha", "Beta", "Gamma", "Delta")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentMainListBinding.inflate(inflater)
        val view = binding.root

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        temp.forEach{
            val listObject = MainListObject.newInstance(it)



            fragmentTransaction.add(view.todoListView.id, listObject, "listobject_$it")
        }


        fragmentTransaction.commit()
        return view
    }





}