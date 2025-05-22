package com.example.basemodel.base.baseact

import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.example.basemodel.base.BaseViewModel
import com.example.basemodel.base.BaseViewModel.Companion.ParameterField.BUNDLE
import com.example.basemodel.base.BaseViewModel.Companion.ParameterField.CANONICAL_NAME
import com.example.basemodel.base.BaseViewModel.Companion.ParameterField.CLASS
import com.example.basemodel.base.BaseViewModel.Companion.ParameterField.REQUEST


/**
 * @author 浩楠
 * @date 2025/5/22 14:42
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 *  描述: TODO 添加通用 LiveData 页面跳转、关闭、setResult 事件等
 */
abstract class BaseMVVMActivity<V : ViewDataBinding, VM : BaseViewModel> :
    BaseCoreActivity<V, VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerUIObservers()
    }

    open fun registerUIObservers() {
        mViewModel.getUC()?.getStartActivityEvent()?.observe(this) { params ->
            params?.let {
                val clz = it[CLASS] as? Class<*>
                val intent = Intent(this, clz)
                val bundle = it[BUNDLE] as? Bundle
                bundle?.let { intent.putExtras(it) }
                startActivityForResult(intent, it[REQUEST] as Int)
            }
        }

        mViewModel.getUC()?.getStartModelActivityEvent()?.observe(this) {
            val clz = it!![CLASS]?.toString()
            val pkg = it[CANONICAL_NAME]?.toString()
            val intent = Intent().apply {
                setClassName(pkg!!, clz!!)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }

        mViewModel.getUC()?.getFinishEvent()?.observe(this) { finish() }

        mViewModel.getUC()?.getOnBackPressedEvent()?.observe(this) { onBackPressed() }

        mViewModel.getUC()?.getSetResultEvent()?.observe(this) { result ->
            val intent = Intent()
            result?.forEach { intent.putExtra(it.key, it.value.toString()) }
            setResult(RESULT_OK, intent)
        }

        mViewModel.getUC()?.getFinishResult()?.observe(this) {
            setResult(it!!)
            finish()
        }
    }
}
