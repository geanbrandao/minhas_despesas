package com.geanbrandao.minhasdespesas.ui.category

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.geanbrandao.minhasdespesas.*
import com.geanbrandao.minhasdespesas.model.Category
import com.geanbrandao.minhasdespesas.model.database.entity_categories.CategoriesData
import com.geanbrandao.minhasdespesas.ui.adapters.CategoryAdapter
import com.geanbrandao.minhasdespesas.ui.add_edit.activity.AddEditActivity
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import com.geanbrandao.minhasdespesas.utils.CustomAppCompatEditText
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.component_toolbar.view.*
import kotlinx.android.synthetic.main.dialog_add_category.view.*
import kotlinx.android.synthetic.main.dialog_options_expense.view.image_close
import kotlinx.android.synthetic.main.dialog_options_expense.view.text_option_delete
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class CategoryActivity : BaseActivity() {

    private var disposableGet: Disposable? = null

    private var disposableAdd: Disposable? = null

    private var disposableDelete: Disposable? = null

    private val viewModel: CategoriesViewModel by viewModel()

    private val selectedCategories: ArrayList<Category> = arrayListOf()

    private val adapter: CategoryAdapter by lazy {
        CategoryAdapter(
            this,
            { item, isChecked ->
                addSelectedCategory(item, isChecked)
            },
            { item ->
                openOptions(item)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val categoriesFromIntent = intent.getSerializableExtra(AddEditActivity.SELECTED_CATEGORIES_KEY) as ArrayList<Category>?
        selectedCategories.addAll(categoriesFromIntent ?: arrayListOf())

        savedInstanceState?.let {
            getCategories()

        } ?: run {
            getCategories()
        }

        createListeners()
    }

    private fun createListeners() {
        setupToolbar()
        recycler_category.adapter = adapter

        button_ok.setOnClickListener {
//            if (selectedCategories.size == 0) {
//                showToast("Selecione pelo menos uma categoria para salvar.")
//            } else {
                backToAddEditItem()
//            }
        }

        text_create_category.setOnClickListener {
            openDialogCreateCategory()
        }

    }

    private fun openDialogCreateCategory() {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_add_category, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val alertDialog = builder.create()

        val input = view.input_category as CustomAppCompatEditText
        val cancel = view.action_cancel as AppCompatTextView
        val save = view.action_save as AppCompatTextView

        save.setOnClickListener {
            if (input.text.toString().trim().length < 2) {
                showToast("A categoria precisa no mínimo 2 caracteres")
            } else {
                alertDialog.dismiss()
                addCategory(input.text.toString())
            }
        }

        cancel.setOnClickListener {
            alertDialog.dismiss()
        }


        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
    }

    private fun openOptions(item: Category) {
        val view =
            LayoutInflater.from(this).inflate(R.layout.dialog_options_category, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val alertDialog = builder.create()

        val close = view.image_close as AppCompatImageView
        val optionDelete = view.text_option_delete as AppCompatTextView

        close.setOnClickListener {
            alertDialog.dismiss()
        }

        optionDelete.setOnClickListener {
            deleteCategory(item)
            alertDialog.dismiss()
        }

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertDialog.show()
    }

    private fun addCategory(category: String) {
        disposableAdd = viewModel.insert(this,
                CategoriesData(UUID.randomUUID().toString(),
                    category,
                    "ic_tag",
                    true,
                    getColorNameFromArray(adapter.itemCount)
                ))
            .doOnSubscribe {
                showLoader()
            }.doFinally {
                hideLoader()
            }.subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onComplete = {}
            )
    }

    private fun deleteCategory(category: Category) {
        disposableDelete = viewModel.delete(this, category.mapTo())
            .doOnSubscribe {
                showLoader()
            }.doFinally {
                hideLoader()
            }.subscribeBy(
                onError = {
                    Timber.e(it)
                },
                onComplete = {}
            )
    }


    private fun getCategories() {
        disposableGet = viewModel.getAll(this).subscribeBy(
            onNext = {
                adapter.clear()

                val categoriesFromDatabase = arrayListOf<Category>()

                it.forEach { category ->
//                        Timber.d("canRemove - ${category.canRemove}")
                    categoriesFromDatabase.add(category.mapTo())
                }

//                categoriesFromDatabase.sortBy { category ->
//                    category.id
//                }


                categoriesFromDatabase.forEach { categoryDB ->
                    selectedCategories.forEach { categoryIN ->
                        // aqui vai subtituir deixar o campo isSelected como selecionado
                        if (categoryDB.id == categoryIN.id) {
                            categoryDB.isSelected = true
                        }
//                        Timber.d("CATEGORY INTENT - $categoryIN")
                    }
                }
//                categoriesFromDatabase.forEach { categoryDB ->
//                    Timber.d("CATEGORY DATABASE - $categoryDB")
//                }
//                selectedCategories.addAll(categoriesFromIntent ?: arrayListOf())
                adapter.addAll(categoriesFromDatabase)

            },
            onError = {
                Timber.e(it)
                showDialogMessage(getString(R.string.errors_generic))
            },
            onComplete = {}
        )
    }

    private fun backToAddEditItem() {
        // pega as categorias selecionadas
        // seta o resultado.
        val intent = Intent()
        intent.putExtra(AddEditActivity.SELECTED_CATEGORIES_KEY, selectedCategories)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun addSelectedCategory(item: Category, isChecked: Boolean) {
        Timber.d("ADD SELECTED CATEGORY ${item.name} - $isChecked")
        if (isChecked) {
            selectedCategories.add(item)
        } else {
            selectedCategories.removeAll {
                it.id == item.id
            }
        }
    }

    private fun setupToolbar() {
        toolbar.title.text = getString(R.string.category_activity_title_page)

        toolbar.back.increaseHitArea(20f)
        toolbar.back.setOnClickListener {
            stepBefore()
        }
    }

    private fun stepBefore() {
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stepBefore()
    }

    override fun onStop() {
        super.onStop()
        disposableGet?.dispose()
        disposableAdd?.dispose()
        disposableDelete?.dispose()
    }
}

//CATEGORY INTENT - 7327fbfa-9c81-46fd-a400-c0374abbbeb5 - Transporte - ic_bus - false - false
//CATEGORY DATAB  - 7327fbfa-9c81-46fd-a400-c0374abbbeb5 - Transporte - ic_bus - false - false