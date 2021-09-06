package com.dice.app.home.screens.view

import android.os.Bundle
import com.dice.app.databinding.ActivityHomeBinding
import com.dice.artistoverview.screens.view.ArtistOverViewFragment
import com.dice.shared.base.ui.BaseActivity

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            showFragment(ArtistOverViewFragment.newInstance())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
