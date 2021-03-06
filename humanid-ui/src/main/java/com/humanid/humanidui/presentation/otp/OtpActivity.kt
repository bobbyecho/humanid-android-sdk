package com.humanid.humanidui.presentation.otp

import android.content.Context
import android.content.Intent
import com.humanid.humanidui.R
import com.humanid.humanidui.base.BaseActivity
import com.humanid.humanidui.event.CloseAllActivityEvent
import com.humanid.humanidui.util.BundleKeys
import org.greenrobot.eventbus.EventBus

class OtpActivity : BaseActivity(), OtpFragment.OnVerifyOtpListener {

    private lateinit var phoneNumber: String

    private lateinit var countryCode: String

    companion object{
        fun start(context: Context, phoneNumber: String, countryCode: String){
            val intent = Intent(context, OtpActivity::class.java)
            intent.putExtra(BundleKeys.KEY_COUNTRY_CODE, countryCode)
            intent.putExtra(BundleKeys.KEY_PHONENUMBER, phoneNumber)
            context.startActivity(intent)
        }
    }

    override val layoutResource: Int
        get() = R.layout.activity_otp

    override fun initLib() {

    }

    override fun initIntent() {
        phoneNumber = intent.getStringExtra(BundleKeys.KEY_PHONENUMBER)
        countryCode = intent.getStringExtra(BundleKeys.KEY_COUNTRY_CODE)
    }

    override fun initUI() {

    }

    override fun initAction() {

    }

    override fun initProcess() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.containerOtp, OtpFragment.newInstance(countryCode = countryCode,
                phoneNumber = phoneNumber, onVerifyOtpListener = this))
                .commitAllowingStateLoss()
    }

    override fun onVerifySuccess(exchangeToken: String) {
        EventBus.getDefault().post(CloseAllActivityEvent(exchangeToken, null))

        finishActivity()
    }

    override fun onVerifyFailed(message: String) {
        EventBus.getDefault().post(CloseAllActivityEvent(null, message))

        finishActivity()
    }
}
