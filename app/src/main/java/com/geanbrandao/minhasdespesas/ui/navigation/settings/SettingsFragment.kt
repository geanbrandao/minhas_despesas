package com.geanbrandao.minhasdespesas.ui.navigation.settings

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.modal.database.MyDatabase
import com.geanbrandao.minhasdespesas.showDialogMessage
import com.geanbrandao.minhasdespesas.ui.base.fragment.BaseFragment
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_options.view.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : BaseFragment() {

    private lateinit var root: View

    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_settings, container, false)

        createLoader(requireContext())

        createListeners()

        return root
    }

    private fun createListeners() {
        root.ll_option_clear.setOnClickListener {
            openDialogOptions("Tem certeza que deseja excluir todas suas despesas?")
        }
    }

    private fun openDialogOptions(message: String) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_options, null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        val alertDialog = builder.create()

        if (message.isNotEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dialogView.text_message.text = Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT)
            } else {
                dialogView.text_message.text = Html.fromHtml(message)
            }
        }

        dialogView.action_cancel.setOnClickListener {
            alertDialog.dismiss()
        }

        dialogView.action_ok.setOnClickListener {
            alertDialog.dismiss()
            clearDB()
        }


        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setCancelable(false)

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        alertDialog.show()
    }

    private fun clearDB() {
        disposable = Completable.fromAction {
            MyDatabase.getDatabaseInstance(requireContext())
                .clearAllTables()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                showLoader()
            }
            .doFinally {
                hideLoader()
            }
            .subscribeBy(
                onError = {
                    activity?.showDialogMessage(it.message ?: run { "" })
                },
                onComplete = {
                    activity?.showDialogMessage("Suas despesas foram apagadas")
                }
            )
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}