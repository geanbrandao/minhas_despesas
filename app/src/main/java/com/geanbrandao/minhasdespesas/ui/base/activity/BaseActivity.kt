package com.geanbrandao.minhasdespesas.ui.base.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.getScreenWidth
import timber.log.Timber

open class BaseActivity : AppCompatActivity() {

    private var loader: AlertDialog? = null
    private var quantity: Int = 0 // indica quantos loaders estão ativos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        savedInstanceState?.let {
            with(it) {
                quantity = getInt(QUANTITY_KEY, 0)
            }
        }

        createLoader()
    }

    fun hideLoader() {
//        Timber.d("hideLoader - quantity $quantity")
        if (quantity > 1) {
            quantity -= 1
        } else {
//            Timber.d("hideLoader - quantity $quantity")
            quantity = 0
            loader!!.dismiss()
        }
    }

    fun showLoader() {
        quantity += 1
//        Timber.d("showLoader - quantity $quantity")
        loader?.show()
        val x = getScreenWidth(0.6f).toInt()
        loader?.window?.attributes?.width = x
        loader?.window?.attributes?.height = x
        loader?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun createLoader() {
        Timber.d("CreateLoader")
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_loader, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        loader = builder.create()

        loader?.setCanceledOnTouchOutside(false)
        loader?.setCancelable(false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(QUANTITY_KEY, quantity)
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val QUANTITY_KEY = "QUANTITY_KEY"
    }
}