package cn.coderpig.cp_network_capture.ui.activity

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import cn.coderpig.cp_network_capture.R
import cn.coderpig.cp_network_capture.databinding.ActivityConfigSettingBinding
import cn.coderpig.cp_network_capture.utils.*
import cn.coderpig.cp_network_capture.utils.binding
import cn.coderpig.cp_network_capture.utils.isFoldRequestHeaders
import cn.coderpig.cp_network_capture.utils.isFoldResponseHeaders
import cn.coderpig.cp_network_capture.utils.isOpenNetworkCapture

/**
 * Author: zpj
 * Date: 2023-09-05 16:47
 * Desc: 配置设置页
 */
class ConfigSettingActivity : AppCompatActivity() {
    private val mBinding by binding(ActivityConfigSettingBinding::inflate)
    private val mOnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        when (buttonView.id) {
            R.id.sw_network_capture -> isOpenNetworkCapture = isChecked
            R.id.sw_fold_request_headers -> isFoldRequestHeaders = isChecked
            R.id.sw_fold_response_headers -> isFoldResponseHeaders = isChecked
            R.id.sw_print_network_log -> isPrintNetworkLog = isChecked
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_setting)
        mBinding.apply {
            swNetworkCapture.isChecked = isOpenNetworkCapture
            swNetworkCapture.setOnCheckedChangeListener(mOnCheckedChangeListener)
            swFoldRequestHeaders.isChecked = isFoldRequestHeaders
            swFoldRequestHeaders.setOnCheckedChangeListener(mOnCheckedChangeListener)
            swFoldResponseHeaders.isChecked = isFoldResponseHeaders
            swFoldResponseHeaders.setOnCheckedChangeListener(mOnCheckedChangeListener)
            swPrintNetworkLog.isChecked = isPrintNetworkLog
            swPrintNetworkLog.setOnCheckedChangeListener(mOnCheckedChangeListener)
        }
    }
}