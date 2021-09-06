package com.dice.shared.base.ui

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.dice.shared.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Base fragment providing common config
 */
@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    val baseActivity: BaseActivity?
        get() = activity as? BaseActivity

    fun dismissKeyboard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
    }


    fun showKeyboard(view: View) {
        ViewCompat.getWindowInsetsController(view)?.show(WindowInsetsCompat.Type.ime())
    }

    companion object {
        fun show(fragment: BaseFragment, activity: BaseActivity?) {
            if (null == activity) return
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view_tag, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
