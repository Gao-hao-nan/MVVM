package com.ghn.cocknovel

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import com.ghn.cocknovel.utils.ChangeDefaultFontUtils
import com.ghn.cocknovel.utils.ChangeDefaultFontUtils.setDefaultFont
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits
import java.lang.reflect.Field

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
        AutoSizeConfig.getInstance()
            .setCustomFragment(true)
            .setExcludeFontScale(true)
            .setPrivateFontScale(0.8f)
            .setLog(false)
            .setBaseOnWidth(true)
            .setUseDeviceSize(true)

        configUnits();
    }

    /**
     * 更换王漢宗勘亭流繁
     */
    open fun changeTTF() {
        ChangeDefaultFontUtils.setDefaultFont(this, "SERIF", "fonts/wangmozongkantingliufan.ttf");
    }

    private fun configUnits() {
        AutoSizeConfig.getInstance()
            .getUnitsManager()
            .setSupportDP(true)
            .setDesignSize(2160F, 3840F)
            .setSupportSP(true)
            .setSupportSubunits(Subunits.MM);
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