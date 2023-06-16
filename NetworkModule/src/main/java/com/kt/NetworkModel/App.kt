package com.kt.NetworkModel

import android.app.Application
import android.app.Person
import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

/**
 * @author 浩楠
 *
 * @date 2023/1/9   18:00.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * 描述:
 */
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        AutoSize.initCompatMultiProcess(this);
        AutoSize.checkAndInit(this);
        AutoSizeConfig.getInstance().setCustomFragment(true).setExcludeFontScale(true)
            .setPrivateFontScale(0.8f).setLog(false).setBaseOnWidth(true).setUseDeviceSize(true)
            .getUnitsManager().setSupportDP(true).setDesignSize(2160F, 3840F).setSupportSP(true)
            .setSupportSubunits(Subunits.MM);

    }

    /**
     * 更换王漢宗勘亭流繁
     */
    open fun changeTTF() {
//        ChangeDefaultFontUtils.setDefaultFont(this, "SERIF", "fonts/wangmozongkantingliufan.ttf");
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        instance = this
    }


    companion object {
        private var instance: Application? = null
        fun get(): App {
            return instance as App
        }

    }
}