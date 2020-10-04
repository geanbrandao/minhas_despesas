package com.geanbrandao.minhasdespesas.ui.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import com.geanbrandao.minhasdespesas.ui.adapters.ExpensesAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var root: View

    private val adapter: ExpensesAdapter by lazy {
        ExpensesAdapter(
            requireContext(),
        {

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false)

        createListeners()

        return root
    }

    private fun createListeners() {
        root.recycler.adapter = adapter

        adapter.addAll(arrayListOf(ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData()))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }


}