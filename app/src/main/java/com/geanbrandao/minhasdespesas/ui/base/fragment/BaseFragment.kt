package com.geanbrandao.minhasdespesas.ui.base.fragment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.getScreenWidth
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseFragment : Fragment() {

    private var loader: AlertDialog? = null
    private var quantity: Int = 0 // indica quantos loaders estão ativos

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_base, container, false)


        return root
    }

    fun hideLoader() {
        loader?.let {
            Timber.d("hideLoader - quantity $quantity")
            if (quantity > 1) {
                quantity -= 1
            } else {
                Timber.d("hideLoader - quantity $quantity")
                quantity = 0
                it.dismiss()
            }
        } ?: run {
            throw Exception("Loader can't be null")
        }
    }

    fun showLoader() {
        loader?.let {
            quantity += 1
            Timber.d("showLoader - quantity $quantity")
            it.show()
            val x = activity!!.getScreenWidth(0.6f).toInt()
            it.window?.attributes?.width = x
            it.window?.attributes?.height = x
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        } ?: run {
            throw Exception("Loader can't be null")
        }
    }

    fun createLoader(context: Context) {
        Timber.d("CreateLoader")
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loader, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(dialogView)
        loader = builder.create()

        loader?.setCanceledOnTouchOutside(false)
        loader?.setCancelable(false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment BaseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = BaseFragment()
    }
}