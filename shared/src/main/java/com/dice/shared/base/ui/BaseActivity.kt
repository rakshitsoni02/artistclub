package com.dice.shared.base.ui

import androidx.appcompat.app.AppCompatActivity
import com.dice.shared.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Base activity providing common config & support
 */
@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    fun showFragment(fragment: BaseFragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_tag, fragment)
            .addToBackStack(null)
            .commit()
    }
}
