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
import com.geanbrandao.minhasdespesas.filterById
import com.geanbrandao.minhasdespesas.goToActivityFoResult
import com.geanbrandao.minhasdespesas.model.Category
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.database.entity_expense_category_join.ExpenseCategoryJoinData
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
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
import java.lang.reflect.Array
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
    private var disposableUpdate: Disposable? = null

    private var amoutSpent: Float = 0.0f
    private var countItems: Int = 0

    private val editItemSelectedCategories = arrayListOf<Category>()

    private val adapter: ExpensesAdapter by lazy {
        ExpensesAdapter(
                requireContext(),
                {
                    openOptions(it)
                }
        )
    }

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

            val list = it.getSerializable(EDIT_ITEM_SELECTED_CATEGORIES) as ArrayList<Category>?

            editItemSelectedCategories.addAll(arrayListOf())
            editItemSelectedCategories.addAll(list ?: arrayListOf())

            Timber.d("AQUI ESSE DEMONIO MUDOU ESSA DESGRACAO - $editItemSelectedCategories")

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
                        onNext = { listExpenses ->
                            resetListExpenses()
//                            Timber.d("Chamou o onNext = ${listExpenses.toString()}")
                            adapter.addAll(listExpenses.toCollection(ArrayList()))

                            listExpenses.forEach { expense ->
                                amoutSpent += expense.amount
                                countItems += 1
                            }

                            root.text_amount_spent.text = getString(R.string.text_value_money, amoutSpent)

                            root.text_count_items.text =
                                    getString(R.string.home_fragment_count_spent, countItems)
                        },
                        onError = {
                            Timber.e(it)
                        },
                        onComplete = {}
                )
    }

    private fun resetListExpenses() {
        adapter.clear()
        amoutSpent = 0f
        countItems = 0
    }

    private fun openOptions(item: Expense) {
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
            editItem(item)
            alertDialog.dismiss()
        }

        optionDelete.setOnClickListener {
            deleteItem(item)
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertDialog.show()
    }

    private fun editItem(item: Expense) {
        // salva as categorias do item, para comparar com as categorias apos a edicao
//        Timber.d("EDITAR ITEM")
        editItemSelectedCategories.clear()
        editItemSelectedCategories.addAll(item.categories)

        val intent = Intent(requireActivity(), AddEditActivity::class.java)
        intent.putExtra(EXPENSE_EDIT_KEY, item)
        startActivityForResult(intent, EDIT_ITEM_CODE)
    }

    private fun deleteItem(item: Expense) {
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
            Timber.d("onActivityResult")
            when (requestCode) {
                ADD_ITEM_CODE -> {
                    val value = data?.getSerializableExtra("expense") as Expense?
                    value?.let {
                        addItem(it)
                    } ?: run {
                        activity?.showDialogMessage("Não foi possível aidiconar sua despesa.")
                    }
                }
                EDIT_ITEM_CODE -> {
                    val value = data?.getSerializableExtra("expense") as Expense?
                    value?.let {
                        updateItem(it)
                    } ?: run {
                        activity?.showDialogMessage("Não foi possível editar sua despesa.")
                    }
                }
            }
        }
    }

    private fun updateItem(item: Expense) {
        val addCategory: ArrayList<Category> = arrayListOf()
        val removeCategory: ArrayList<Category> = arrayListOf()

        val joinsAdd: ArrayList<ExpenseCategoryJoinData> = arrayListOf()
        val joinsRemove: ArrayList<ExpenseCategoryJoinData> = arrayListOf()
//        Timber.d("ISSO E O QUE ESTA SALVO ${editItemSelectedCategories}")

        if (editItemSelectedCategories.isNotEmpty()) {
//            Timber.d("LISESSESTA ANTERIOR NAOOOOO EH VAZIA")
            removeCategory.addAll(editItemSelectedCategories.filterById(item.categories))
            addCategory.addAll(item.categories.filterById(editItemSelectedCategories))

//            Timber.d("O QUE VAI SER REMOVIDO ${removeCategory}")
//            Timber.d("O QUE VAI SER ADICIONADO ${addCategory}")


            addCategory.forEach { joinsAdd.add(ExpenseCategoryJoinData(item.id, it.id)) }
            removeCategory.forEach { joinsRemove.add(ExpenseCategoryJoinData(item.id, it.id)) }
        } else {
//            Timber.d("LISTA ANTERIOR EH VAZIA")
            item.categories.forEach { joinsAdd.add(ExpenseCategoryJoinData(item.id, it.id)) }
        }

//        Timber.d("JoinsAdd - ${joinsAdd}")
//        Timber.d("JoinsRemove - ${joinsRemove}")

        disposableUpdate = viewModel.updateExpense(requireContext(), item,
                joinsRemove = joinsRemove, joinsAdd = joinsAdd)
                .doOnSubscribe {
                    showLoader()
                }.doFinally {
                    hideLoader()
                }.subscribeBy(
                        onError = {
                            Timber.e(it)
                            activity?.showDialogMessage(requireContext().getString(R.string.errors_generic))
                        },
                        onComplete = {
                            Timber.d("Item edited to database")
                            getExpenses()
                        }
                )
    }

    private fun addItem(item: Expense) {
        disposableAdd = viewModel.addExpense(requireContext(), item)
                .doOnSubscribe {
                    showLoader()
                }.doFinally {
                    hideLoader()
                }.subscribeBy(
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
        Timber.d("onSavaInstanceState")
        outState.putSerializable(EDIT_ITEM_SELECTED_CATEGORIES, editItemSelectedCategories)
        outState.putFloat(AMOUNT_SPENT_KEY, amoutSpent)
        outState.putInt(COUNT_ITEMS_KEY, countItems)
    }

    override fun onStop() {
        super.onStop()
        Timber.d("ON STOP")
        disposableGet?.dispose()
        disposableAdd?.dispose()
        disposableDelete?.dispose()
        disposableUpdate?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("ON DESTROY")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("ON RESUME")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("ON START")
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
        const val EDIT_ITEM_CODE = 3213
        const val AMOUNT_SPENT_KEY = "AMOUNT_SPENT_KEY"
        const val COUNT_ITEMS_KEY = "COUNT_ITEMS_KEY"
        const val EXPENSE_EDIT_KEY = "EXPENSE_EDIT_KEY"
        const val EDIT_ITEM_SELECTED_CATEGORIES = "EDIT_ITEM_SELECTED_CATEGORIES"
    }

    // TODO Dar uma opcao de definir um teto de gastos. E deixar o total em vermelho qunado o total for excedido
    // TODO tem um bug que ao excluir uma categoria sem finilizar a activity sem salvar alteracoes nas depesas a lista de despesas nao atualiza. E parece que a lista nao foi excluida
}

/*

O QUE VAI SER ADICIONADO
[7cdc58c0-e25f-43fa-89a4-3e6904304bf7 - Restaurante - ic_restaurant - false - false, a9fd5e71-593d-4ba9-9b4b-b9d613289690 - Serviços - ic_service - false - false, b182a73e-0e64-4ccf-9a06-ce4a42f0b9b0 - Educação - ic_education - false - false]
[7cdc58c0-e25f-43fa-89a4-3e6904304bf7 - Restaurante - ic_restaurant - false - false, a9fd5e71-593d-4ba9-9b4b-b9d613289690 - Serviços - ic_service - false - false, b182a73e-0e64-4ccf-9a06-ce4a42f0b9b0 - Educação - ic_education - false - false]
O QUE VAI SER REMOVIDO
 */