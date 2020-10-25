package com.geanbrandao.minhasdespesas.ui.statistics

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.minhasdespesas.R
import com.geanbrandao.minhasdespesas.increaseHitArea
import com.geanbrandao.minhasdespesas.ui.adapters.BarAdapter
import com.geanbrandao.minhasdespesas.ui.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_add_edit.toolbar
import kotlinx.android.synthetic.main.activity_statistics.*
import kotlinx.android.synthetic.main.component_toolbar.view.*
import kotlinx.android.synthetic.main.page_month_statistics.*
import kotlinx.android.synthetic.main.page_month_statistics.view.*


class StatisticsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_SECURE,
//            WindowManager.LayoutParams.FLAG_SECURE
//        )
        setContentView(R.layout.activity_statistics)


        createListeners()
    }

    private fun createListeners() {
        setupToolbar()
        setupCarousel()

//        toolbar.title.setOnClickListener {
//            openWhatsAppConversation("", "teste")
//        }
    }

    private fun setupCarousel() {
        carousel_view.pageCount = 5
        carousel_view.setViewListener {
            bindView()
        }
    }

    private fun bindView(): View {
        val view =
            LayoutInflater.from(this@StatisticsActivity).inflate(
                R.layout.page_month_statistics,
                null
            )

//        val adapter: BarAdapter by lazy {
//            BarAdapter()
//        }

        val month = view.text_month as AppCompatTextView
        val recyclerBars = view.recycler_bars as RecyclerView






        return view
    }

//    fun openWhatsAppConversation(number: String?, message: String?) {
//        var number = "5511933058272"
//        number = number.replace(" ", "").replace("+", "")
//        val sendIntent = Intent("android.intent.action.MAIN")
//        sendIntent.type = "text/plain"
//        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
//        sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
//        sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net")
//        startActivity(sendIntent)
//    }

    private fun setupToolbar() {
        toolbar.title.text = getString(R.string.statistics_activity_title_page)

        toolbar.back.increaseHitArea(20f)
        toolbar.back.setOnClickListener {
            stepBefore()
        }
    }

    private fun stepBefore() {
        finish()
    }

    override fun onBackPressed() {
        stepBefore()
    }
}