package com.geanbrandao.eventmanager.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CustomAppCompatButton: AppCompatButton {

    private val hashMap: HashMap<Int, Boolean> = hashMapOf()

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    constructor(context: Context,attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    private fun itsOk() {
        val array: ArrayList<Boolean> = arrayListOf()
        hashMap.values.forEach {
           array.add(it)
        }
        val mIsEnabled: Boolean = array.reduce { acc, b ->
            acc && b
        }
        isEnabled = mIsEnabled
    }

    fun setValueInHash(id: Int, value: Boolean) {
        hashMap[id] = value // caso seja igual seta o mesmo
        itsOk()
//        Timber.d("TAMANHO DO HASH ${hashMap.size}")
    }

}