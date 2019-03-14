package com.prince.smsreader.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import com.prince.smsreader.R
import com.prince.smsreader.model.SMSMessages
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_sms.*
import permissions.dispatcher.*
import javax.inject.Inject

@RuntimePermissions
class SMSActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: SMSViewModelFactory

    @Inject
    lateinit var mAdapter: SMSAdapter

    private var newMessageTime: String? = null

    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(SMSViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sms)

        newMessageTime = intent.getStringExtra("message_time")

        rv_parent.itemAnimator = DefaultItemAnimator()
        rv_parent.adapter = mAdapter

        readSMSWithPermissionCheck()

        viewModel.getSMSMessages().observe(this, Observer<List<SMSMessages>> {
            mAdapter.submitList(it)
        })

    }


    override fun onNewIntent(intent: Intent?) {
        intent?.apply {
            newMessageTime = getStringExtra("message_time")
            readSMS()
        }
    }

    @NeedsPermission(android.Manifest.permission.RECEIVE_SMS)
    fun readSMS() {
        viewModel.setMessageTime(newMessageTime)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @OnShowRationale(Manifest.permission.RECEIVE_SMS)
    fun showRationaleForSMS(request: PermissionRequest) {
        Toast.makeText(this, "Please give SMS Permission", Toast.LENGTH_LONG).show()
        request.proceed()
    }

    @OnPermissionDenied(Manifest.permission.RECEIVE_SMS)
    fun onSMSDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.RECEIVE_SMS)
    fun onSMSNeverAskAgain() {
        Toast.makeText(this, "Go to settings to enable permissions ", Toast.LENGTH_LONG).show()
    }
}
