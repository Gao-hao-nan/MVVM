package com.ghn.cocknovel.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.basemodel.base.BaseFragment
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentRecommendBinding
import com.ghn.cocknovel.databinding.ItemHomeBinding
import com.ghn.cocknovel.viewmodel.RecommendViewModel
import com.kt.NetworkModel.bean.WBanner
import com.kt.network.bean.datas
import com.stx.xhb.androidx.XBanner
import com.stx.xhb.androidx.transformers.Transformer


class RecommendFragment : BaseFragment<FragmentRecommendBinding, RecommendViewModel>() {
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_recommend
    }

    override fun initParam() {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("CheckResult", "SetTextI18n")
    override fun initViewObservable() {
        mViewModel?.getBanner()
        mViewModel?.flowbanner()
        mViewModel?.mBanner?.observe(this) {
            mBinding?.homexbanner?.setBannerData(it)
            //刷新数据之后，需要重新设置是否支持自动轮播
            mBinding?.homexbanner?.setAutoPlayAble(it.size > 1)
            //设置模式是否为一屏多页（可选）
            mBinding?.homexbanner?.setIsClipChildrenMode(true)
            mBinding?.homexbanner?.setBannerData(it)
            //设置轮播的动画，默认情况下一屏多页左右的图片不会缩放，更改动画可以改变轮播的效果，
            //Transformer还有很多效果，感兴趣的朋友可以自行尝试
            mBinding?.homexbanner?.setPageTransformer(Transformer.Scale)
            mBinding?.homexbanner?.loadImage(object : XBanner.XBannerAdapter {
                override fun loadBanner(banner: XBanner?, model: Any?, view: View?, position: Int) {
                    Glide.with(this@RecommendFragment).load((model as WBanner.Data).imagePath)
                        .into(view as ImageView)
                }
            })
        }
        mViewModel?.getHomeStatus()
        mBinding?.recyclerViewHome?.linear()?.setup {
            addType<datas>(R.layout.item_home)
            onBind {
                val binding=getBinding<ItemHomeBinding>()
                val mode=getModel<datas>()
                binding.itemHomeAuthor.text="作者: ${mode.shareUser}"
                binding.itemHomeTitle.text="标题: ${mode.title}"
                binding.itemHomeSource.text="目录名称: ${mode.chapterName}"
                binding.itemHomeTime.text="时间: ${mode.niceDate}"
            }
        }?.models=mViewModel?.homeStatus
    }
}




