package com.geanbrandao.minhasdespesas.ui.navigation.home.fragments

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
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
import com.geanbrandao.minhasdespesas.goToActivityFoResult
import com.geanbrandao.minhasdespesas.modal.database.expenses.ExpensesData
import com.geanbrandao.minhasdespesas.showDialogMessage
import com.geanbrandao.minhasdespesas.ui.add_edit.activity.AddEditActivity
import com.geanbrandao.minhasdespesas.ui.adapters.ExpensesAdapter
import com.geanbrandao.minhasdespesas.ui.base.fragment.BaseFragment
import com.geanbrandao.minhasdespesas.ui.navigation.home.HomeViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.dialog_options_expense.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment() {

    private lateinit var root: View

    private val viewModel: HomeViewModel by viewModel()

    private var disposableGet: Disposable? = null
    private var disposableAdd: Disposable? = null
    private var disposableDelete: Disposable? = null

    private var amoutSpent: Float = 0.0f
    private var countItems: Int = 0

    private val adapter: ExpensesAdapter by lazy {
        ExpensesAdapter(
            requireContext(),
            {
                openOptions(it)
            }
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false)


        // CreateLoader
        createLoader(requireContext())

        Timber.d("CREATE VIEW")

        savedInstanceState?.let {
            Timber.d("SAVEINSTANCE")

            getExpenses()

            amoutSpent = it.getFloat(AMOUNT_SPENT_KEY)
            countItems = it.getInt(COUNT_ITEMS_KEY)
            root.text_amount_spent.text = getString(R.string.text_value_money, amoutSpent)
            root.text_count_items.text = getString(R.string.home_fragment_count_spent, countItems)

        } ?: run {
            Timber.d("SAVEINSTANCE NULL")
            root.text_amount_spent.text = getString(R.string.text_value_money, amoutSpent)
            root.text_count_items.text = getString(R.string.home_fragment_count_spent, countItems)

            getExpenses()
        }

        createListeners()

        return root
    }

    private fun createListeners() {
        root.recycler.adapter = adapter

        root.image_add.setOnClickListener {
            activity?.goToActivityFoResult(AddEditActivity::class.java, ADD_ITEM_CODE)
//            addItem(ExpensesData(0, 22.0f, "teste", Date(), ""))
        }
    }

    private fun getExpenses() {
        disposableGet = viewModel.getAll(requireContext())
            .subscribeBy(
                onNext = {
                    Timber.d("Chamou o onNext = ${it.size}")
                    adapter.clear()
                    adapter.addAll(it.toCollection(ArrayList()))

                    amoutSpent = 0f
                    it.forEach { expense ->
                        amoutSpent+= expense.amount
                    }
                    root.text_amount_spent.text = getString(R.string.text_value_money, amoutSpent)

                    countItems = it.size
                    root.text_count_items.text = getString(R.string.home_fragment_count_spent, countItems)
                },
                onError = {
                    Timber.e(it)
                },
                onComplete = {}
            )
    }

    private fun openOptions(item: ExpensesData) {
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_options_expense, null)
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
            deleteItem(item)
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertDialog.show()
    }

    private fun deleteItem(item: ExpensesData) {
        disposableDelete = viewModel.deleteExpense(requireContext(), item)
            .doOnSubscribe {
                showLoader()
            }.doFinally {
                hideLoader()
            }
            .subscribeBy(
                onError = {
                    Timber.e(it)
                    activity?.showDialogMessage(requireContext().getString(R.string.errors_generic))
                },
                onComplete = {
                    Timber.d("delete item from database")
                }
            )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ADD_ITEM_CODE -> {
                    val value = data?.getSerializableExtra("expense") as ExpensesData?
                    value?.let {
                        addItem(it)
                    } ?: run {
                        activity?.showDialogMessage("Não foi possível aidiconar sua despesa.")
                    }
                }
            }
        }
    }

    private fun addItem(item: ExpensesData) {

        disposableAdd = viewModel.addExpense(requireContext(), item)
            .doOnSubscribe {
                showLoader()
            }.doFinally {
                hideLoader()
            }
            .subscribeBy(
                onError = {
                    Timber.e(it)
                    activity?.showDialogMessage(requireContext().getString(R.string.errors_generic))
                },
                onComplete = {
                    Timber.d("Item added to database")
                    getExpenses()
                }
            )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat(AMOUNT_SPENT_KEY, amoutSpent)
        outState.putInt(COUNT_ITEMS_KEY, countItems)
    }

    override fun onStop() {
        super.onStop()
        disposableGet?.dispose()
        disposableAdd?.dispose()
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

        const val ADD_ITEM_CODE = 3212
        const val DATA_KEY = "DATA_KEY"
        const val AMOUNT_SPENT_KEY = "AMOUNT_SPENT_KEY"
        const val COUNT_ITEMS_KEY = "COUNT_ITEMS_KEY"
    }

    // TODO Dar uma opcao de definir um teto de gastos. E deixar o total em vermelho qunado o total for excedido
}