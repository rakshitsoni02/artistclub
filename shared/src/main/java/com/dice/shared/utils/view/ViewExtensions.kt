package com.dice.shared.utils.view

import android.app.Activity
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dice.shared.base.ui.BaseFragment
import com.dice.shared.utils.strings.SimpleTextWatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Easy toast function for Fragment.
 */
fun BaseFragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(baseActivity, text, duration).show()
}

/**
 * Inflate the layout specified by [layoutRes].
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun TextView.afterTextChanged(listener: (v: TextView, newText: CharSequence) -> Unit) {
    val textView = this
    val textWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            listener(textView, s)
            onUserInteraction()
        }
    }
    addTextChangedListener(textWatcher)
}

private fun TextView.onUserInteraction() {
    (context as? Activity)?.onUserInteraction()
}


/**
 * Launches a new coroutine and repeats `block` every time the Fragment's viewLifecycleOwner
 * is in and out of `minActiveState` lifecycle state.
 */
inline fun Fragment.launchAndRepeatWithViewLifecycle(
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(minActiveState) {
            block()
        }
    }
}