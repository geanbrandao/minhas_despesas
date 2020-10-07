package com.geanbrandao.minhasdespesas.ui.add_edit.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import com.geanbrandao.minhasdespesas.*
import com.geanbrandao.minhasdespesas.modal.database.entity_expenses.ExpensesData
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

class AddEditActivity : BaseActivity() {

    private val viewModel: AddEditViewModel by viewModel()

    private var disposable: Disposable? = null

    private lateinit var picker: DatePickerDialog

    private var date: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        createListeners()

        savedInstanceState?.let {

        } ?: run {
            val item = intent.getSerializableExtra(HomeFragment.EXPENSE_EDIT_KEY) as ExpensesData?
            item?.let {
                input_amount.setText("%.2f".format(item.amount))
                input_title.setText(item.title)
                text_date.text = item.date.toStringDateFormated()
                input_description.setText(item.description)
            }
        }

    }

    private fun createListeners() {
        setupToolbar()
        setupDatePicker()

        button_save.setOnClickListener {
            addItem()
        }

        text_date.setOnClickListener {
            picker.show()
        }

        text_category.setOnClickListener {
            goToActivityFoResult(CategoryActivity::class.java, 123)
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

        date = formatDateString(year, month+1, day)

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
            val data: ExpensesData = getData()
            Timber.d("data - ${data.toString()}")

            val intent = Intent()
            intent.putExtra("expense", data)
            setResult(RESULT_OK, intent)
            finish()

        }?: run {
            // ADD
            val data: ExpensesData = getData()
            Timber.d("data - ${data.toString()}")

            val intent = Intent()
            intent.putExtra("expense", data)
            setResult(RESULT_OK, intent)
            finish()
        }


    }

    private fun getData(): ExpensesData {

        val data: ExpensesData? = intent.getSerializableExtra(HomeFragment.EXPENSE_EDIT_KEY) as ExpensesData?

        val id = data?.let {
            it.id
        } ?: run {
            0
        }

        val title = input_title.text.toString()
        val amount = input_amount.text.toString().replace(",", "").toInt() / 100f
        val description =  input_description.text.toString()

        return ExpensesData(id, amount, title, date ?: Date(), description)
    }

    private fun setupToolbar() {
        toolbar.title.text = getString(R.string.add_edit_activity_title_page)

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
}