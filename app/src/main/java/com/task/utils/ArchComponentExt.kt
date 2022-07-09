package com.task.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.task.data.dto.trade.TradeItems
import kotlin.reflect.KFunction1

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T> LifecycleOwner.observeEvent(liveData: LiveData<SingleEvent<T>>, action: KFunction1<SingleEvent<T>, Unit>) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}
