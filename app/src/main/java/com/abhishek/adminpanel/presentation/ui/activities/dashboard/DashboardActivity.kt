package com.oneclick.blackandoneparent.presentation.ui.activities.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.databinding.ActivityDashboardBinding
import com.oneclick.blackandoneparent.presentation.ui.activities.base.BaseActivity

class DashboardActivity : BaseActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)


    }
}