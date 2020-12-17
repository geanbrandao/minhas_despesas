package com.geanbrandao.minhasdespesas.ui.add_edit.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.View
import com.geanbrandao.minhasdespesas.*
import com.geanbrandao.minhasdespesas.databinding.ActivityAddEditBinding
import com.geanbrandao.minhasdespesas.model.Category
import com.geanbrandao.minhasdespesas.model.Expense
import com.geanbrandao.minhasdespesas.ui.adapters.CategorySimpleAdapter
import com.geanbrandao.minhasdespesas.ui.add_edit.AddEditViewModel
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import com.geanbrandao.minhasdespesas.ui.category.CategoryActivity
import com.geanbrandao.minhasdespesas.ui.navigation.home.fragments.HomeFragment
import io.reactivex.disposables.Disposable
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.time.OffsetDateTime
import java.util.*
import kotlin.collections.ArrayList

class AddEditActivity : BaseActivity() {

    private lateinit var binding: ActivityAddEditBinding

    private val viewModel: AddEditViewModel by viewModel()

    private var disposable: Disposable? = null

    private lateinit var picker: DatePickerDialog

    private var date: OffsetDateTime? = null

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
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


        date = OffsetDateTime.now()

        createListeners()

        Timber.d("DATE ${date.toString()}")

        savedInstanceState?.let {


        } ?: run {
            val item = intent.getSerializableExtra(HomeFragment.EXPENSE_EDIT_KEY) as Expense?
            item?.let {
                binding.inputAmount.setText("%.2f".format(item.amount))
                binding.inputTitle.setText(item.title)
                binding.textDate.text = item.selectedDate.toStringDateFormated()
                date = item.selectedDate
                binding.inputDescription.setText(item.description)

                if (it.categories.size > 0) {
                    adapter.addAll(it.categories)
                    binding.recyclerSelectedCategory.show()
                    binding.textCategory.hide()
                }
            }
        }

    }

    private fun createListeners() {
        setupToolbar()
        setupDatePicker()

        binding.recyclerSelectedCategory.adapter = adapter

        binding.buttonSave.setOnClickListener {
            addItem()
        }

        binding.textDate.setOnClickListener {
            picker.show()
        }

        binding.textCategory.setOnClickListener {
            goToSelectCategories(adapter.data)
        }

        binding.recyclerSelectedCategory.setOnClickListener {
            goToSelectCategories(adapter.data)
        }

        Selection.setSelection(binding.inputAmount.text, binding.inputAmount.text!!.length)

        binding.inputAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                query?.let {
                    binding.inputAmount.removeTextChangedListener(this)
                    val clearText = it.toString().replace(" ", "")
                            .replace(".", "")
                            .replace(",", "")
                            .replace("-", "")
                    val parsed = clearText.toDouble() / 100f
                    val formatted = "%.2f".format(parsed.toFloat())
                    binding.inputAmount.setText(formatted)
                    binding.inputAmount.setSelection(formatted.length)
                    binding.inputAmount.addTextChangedListener(this)
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

        binding.textDate.text = date?.toStringDateFormated()

        picker = DatePickerDialog(this, { _, y, m, d ->
            Timber.d("$y/$m/$d")
            date = formatDateString(y, m + 1, d)
            binding.textDate.text = date?.toStringDateFormated()
        }, year, month, day)
    }

    private fun addItem() {

        intent.getSerializableExtra(HomeFragment.EXPENSE_EDIT_KEY)?.let {
            // EDIT
            val data: Expense = getData()
            Timber.d("data - ${data.toString()}")

            val intent = Intent()
            intent.putExtra("expense", data)
            setResult(RESULT_OK, intent)
            finish()

        } ?: run {
            // ADD
            val data: Expense = getData()
            Timber.d("data - ${data.toString()}")

            val intent = Intent()
            intent.putExtra("expense", data)
            setResult(RESULT_OK, intent)
            finish()
        }


    }

    private fun getData(): Expense {

        val data: Expense? = intent.getSerializableExtra(HomeFragment.EXPENSE_EDIT_KEY) as Expense?

        val id = data?.id ?: UUID.randomUUID().toString() // se for editado ja tem id

        val title = binding.inputTitle.text.toString()
        val amount = binding.inputAmount.text.toString().replace(",", "").toInt() / 100f
        val description = binding.inputDescription.text.toString()

        val selectedDate = date ?: OffsetDateTime.now()

        val createdAt = data?.createdAt ?: OffsetDateTime.now()
        val updatedAt = data?.updatedAt ?: OffsetDateTime.now()

        return Expense(
                id = id,
                amount = amount,
                title = title,
                selectedDate = selectedDate,
                description = description,
                categories = adapter.data,
                createdAt = createdAt,
                updatedAt = updatedAt
        )
    }

    private fun goToSelectCategories(data: ArrayList<Category>) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(SELECTED_CATEGORIES_KEY, data)
        startActivityForResult(intent, SELECTED_CATEGORIES_CODE)
    }

    private fun setupToolbar() {
        binding.toolbar.title.text = getString(R.string.add_edit_activity_title_page)

        binding.toolbar.back.increaseHitArea(20f)
        binding.toolbar.back.setOnClickListener {
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
                        binding.recyclerSelectedCategory.show()
                        binding.textCategory.hide()
                    } else {
                        adapter.clear()
                        binding.recyclerSelectedCategory.hide()
                        binding.textCategory.show()
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