package com.ghn.cocknovel.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.basemodel.base.BaseFragment
import com.example.basemodel.base.BaseRecyclerAdapter
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentRecommendBinding
import com.ghn.cocknovel.viewmodel.RecommendViewModel
import com.kt.NetworkModel.bean.WBanner
import com.kt.network.bean.datas
import com.stx.xhb.androidx.XBanner
import com.stx.xhb.androidx.transformers.Transformer


class RecommendFragment : BaseFragment<FragmentRecommendBinding, RecommendViewModel>() {
    var adapter: BaseRecyclerAdapter<datas>? = null
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

    @SuppressLint("CheckResult")
    override fun initViewObservable() {
        mViewModel?.getBanner()

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




        val recyclerview_homelist = mutableListOf<datas>()
        mViewModel?.getHomeStatus()
        mBinding?.recyclerViewHome?.layoutManager = LinearLayoutManager(activity)
        adapter = object : BaseRecyclerAdapter<datas>(recyclerview_homelist) {
            override fun bindData(holder: BaseViewHolder?, position: Int) {
                holder?.itemView?.findViewById<TextView>(R.id.item_home_author)?.text = "作者: " + datas[position].shareUser
//                holder?.itemView?.item_home_author?.text = "作者: " + datas[position].shareUser
//                holder?.itemView?.item_home_time?.text = datas[position].niceDate
//                holder?.itemView?.item_home_title?.text = datas[position].title
//                holder?.itemView?.item_home_source?.text =
//                    datas[position].superChapterName + "/" + datas[position].chapterName
//                holder?.getView(R.id.item_home_csl)?.setOnClickListener {
//                    viewModel?.setWebview(datas[position].link)
//                }
            }

            override val layoutId: Int = R.layout.item_home
        }
        val controller = LayoutAnimationController(AnimationUtils.loadAnimation(activity, R.anim.animate))

        mBinding?.recyclerViewHome?.layoutAnimation = controller
        mBinding?.recyclerViewHome?.adapter = adapter
        mViewModel?.homeStatus?.observe(this) {
            adapter?.addData(it.datas)

        }

    }


}




