package com.dice.shared.utils.strings

import android.content.Context

sealed class StringProvider {

    fun text(context: Context?): CharSequence = generateText(context)

    protected open fun generateText(context: Context?): CharSequence = ""

    private data class ResourceText(val resId: Int) : StringProvider() {
        override fun generateText(context: Context?): CharSequence = context?.getText(resId) ?: ""
    }

    private data class RawText(val text: CharSequence) : StringProvider() {
        override fun generateText(context: Context?): CharSequence = text
    }

    companion object {
        fun of(resId: Int): StringProvider = ResourceText(resId)
        fun of(text: CharSequence): StringProvider = RawText(text)
    }
}
