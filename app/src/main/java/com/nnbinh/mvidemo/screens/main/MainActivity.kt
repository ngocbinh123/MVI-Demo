package com.nnbinh.mvidemo.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.nnbinh.mvidemo.R
import com.nnbinh.mvidemo.R.id
import com.nnbinh.mvidemo.R.layout
import com.nnbinh.mvidemo.base.BaseActivity
import com.nnbinh.mvidemo.base.BaseActivityVM
import com.nnbinh.mvidemo.data.CurrentUser
import com.nnbinh.mvidemo.screens.signInUp.SignInUpActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun initViewModel(): BaseActivityVM {
        return ViewModelProviders.of(this).get(MainVM::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        fab.setOnClickListener { view ->
            CurrentUser.clearInfo()
            val intent = Intent(this, SignInUpActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
