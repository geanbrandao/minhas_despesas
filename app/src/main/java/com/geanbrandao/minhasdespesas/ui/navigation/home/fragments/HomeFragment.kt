package com.geanbrandao.minhasdespesas.ui.navigation.home.fragments

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import com.geanbrandao.minhasdespesas.ui.adapters.ExpensesAdapter
import com.geanbrandao.minhasdespesas.ui.navigation.home.HomeViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.dialog_options_expense.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var root: View

    private val viewModel: HomeViewModel by viewModel()

    private var disposable: Disposable? = null

    private val adapter: ExpensesAdapter by lazy {
        ExpensesAdapter(
            requireContext(),
        {
            openOptions(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false)

        savedInstanceState?.let {

        } ?: run {
            getExpenses()
        }

        createListeners()

        return root
    }

    private fun createListeners() {
        root.recycler.adapter = adapter

        adapter.addAll(arrayListOf(ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData()))
        adapter.addAll(arrayListOf(ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData()))
        adapter.addAll(arrayListOf(ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData(),ExpensesData()))
    }

    private fun getExpenses() {
        disposable = viewModel.getAll(requireContext())
            .subscribeBy(
                onNext = {
//                    adapter.clear()
                    adapter.addAll(it.toCollection(ArrayList()))
                },
                onError = {
                    Timber.e(it)
                },
                onComplete = {}
            )
    }

    private fun openOptions(item: ExpensesData) {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_options_expense, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        val alertDialog = builder.create()

        val close = view.image_close as AppCompatImageView
        val optionEdit = view.text_option_edit as AppCompatTextView
        val optionDelete = view.text_option_delete as AppCompatTextView

        close.setOnClickListener {
            alertDialog.dismiss()
        }

        optionEdit.setOnClickListener {

        }

        optionDelete.setOnClickListener {

        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertDialog.show()
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
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

    // TODO Dar uma opcao de definir um teto de gastos. E deixar o total em vermelho qunado o total for excedido
}