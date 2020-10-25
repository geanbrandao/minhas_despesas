package com.geanbrandao.minhasdespesas.ui.add_edit.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.View
import com.geanbrandao.minhasdespesas.*
import com.geanbrandao.minhasdespesas.model.Category
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.model.database.entity_expenses.ExpensesData
import com.geanbrandao.minhasdespesas.ui.adapters.CategorySimpleAdapter
import com.geanbrandao.minhasdespesas.ui.add_edit.AddEditViewModel
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import com.geanbrandao.minhasdespesas.ui.category.CategoryActivity
import com.geanbrandao.minhasdespesas.ui.navigation.home.fragments.HomeFragment
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_add_edit.*
import kotlinx.android.synthetic.main.component_toolbar.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class AddEditActivity : BaseActivity() {

    private val viewModel: AddEditViewModel by viewModel()

    private var disposable: Disposable? = null

    private lateinit var picker: DatePickerDialog

    private var date: Date? = null

    private val adapter: CategorySimpleAdapter by lazy {
        CategorySimpleAdapter(
            this,
            {
                goToSelectCategories(adapter.data)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)


        date = Date()

        createListeners()

        Timber.d("DATE ${date.toString()}")

        savedInstanceState?.let {


        } ?: run {
            val item = intent.getSerializableExtra(HomeFragment.EXPENSE_EDIT_KEY) as Expense?
            item?.let {
                input_amount.setText("%.2f".format(item.amount))
                input_title.setText(item.title)
                text_date.text = item.date.toDate()?.toStringDateFormated()
                date = item.date.toDate()
                input_description.setText(item.description)

                if (it.categories.size > 0) {
                    adapter.addAll(it.categories)
                    recycler_selected_category.visibility = View.VISIBLE
                    text_category.visibility = View.GONE
                }
            }
        }

    }

    private fun createListeners() {
        setupToolbar()
        setupDatePicker()

        recycler_selected_category.adapter = adapter

        button_save.setOnClickListener {
            addItem()
        }

        text_date.setOnClickListener {
            picker.show()
        }

        text_category.setOnClickListener {
            goToSelectCategories(adapter.data)
        }

        recycler_selected_category.setOnClickListener {
            goToSelectCategories(adapter.data)
        }

        Selection.setSelection(input_amount.text, input_amount.text!!.length)

        input_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                query?.let {
                    input_amount.removeTextChangedListener(this)
                    val clearText = it.toString().replace(" ", "")
                        .replace(".", "")
                        .replace(",", "")
                        .replace("-", "")
                    val parsed = clearText.toDouble() / 100f
                    val formatted = "%.2f".format(parsed.toFloat())
                    input_amount.setText(formatted)
                    input_amount.setSelection(formatted.length)
                    input_amount.addTextChangedListener(this)
                    Timber.tag("Valor").d(" formated - ${formatted}")

                }
            }
        })
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        Timber.d("CELENDAR $year/$month/$day")

        text_date.text = date?.toStringDateFormated()

        picker = DatePickerDialog(this, { _, y, m, d ->
            Timber.d("$y/$m/$d")
            date = formatDateString(y, m+1, d)
            text_date.text = date?.toStringDateFormated()
        }, year, month, day)
    }

    private fun addItem() {

        intent.getSerializableExtra(HomeFragment.EXPENSE_EDIT_KEY)?.let {
            // EDIT
            val data: Expense = getData()
            Timber.d("data EDIT - ${data.toString()}")

            val intent = Intent()
            intent.putExtra("expense", data)
            setResult(RESULT_OK, intent)
            finish()

        }?: run {
            // ADD
            val data: Expense = getData()
            Timber.d("data ADD - ${data.toString()}")

            val intent = Intent()
            intent.putExtra("expense", data)
            setResult(RESULT_OK, intent)
            finish()
        }


    }

    private fun getData(): Expense {

        val data: Expense? = intent.getSerializableExtra(HomeFragment.EXPENSE_EDIT_KEY) as Expense?

        val id = data?.id ?: UUID.randomUUID().toString() // se for editado ja tem id

        val title = input_title.text.toString()
        val amount = input_amount.text.toString().replace(",", "").toInt() / 100f
        val description =  input_description.text.toString()

        return Expense(id, amount, title, date.toString(), description, adapter.data)
    }

    private fun goToSelectCategories(data: ArrayList<Category>) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(SELECTED_CATEGORIES_KEY, data)
        startActivityForResult(intent, SELECTED_CATEGORIES_CODE)
    }

    private fun setupToolbar() {
        toolbar.title.text = getString(R.string.add_edit_activity_title_page)

        toolbar.back.increaseHitArea(20f)
        toolbar.back.setOnClickListener {
            stepBefore()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECTED_CATEGORIES_CODE) {
                val value = data?.getSerializableExtra(SELECTED_CATEGORIES_KEY) as ArrayList<Category>?
                value?.let {
                    if (it.size > 0) {
                        it.forEach { Timber.d("NOME DA CATEGORIA - ${it.name}") }
                        adapter.clear()
                        adapter.addAll(it)
                        recycler_selected_category.visibility = View.VISIBLE
                        text_category.visibility = View.GONE
                    } else {
                        adapter.clear()
                        recycler_selected_category.visibility = View.GONE
                        text_category.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    private fun stepBefore() {
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stepBefore()
    }

    companion object {
        const val SELECTED_CATEGORIES_CODE = 1232
        const val SELECTED_CATEGORIES_KEY = "SELECTED_CATEGORIES_KEY"
    }
}