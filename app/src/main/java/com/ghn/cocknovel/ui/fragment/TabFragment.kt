package com.ghn.cocknovel.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.basemodel.base.BaseFragment
import com.example.basemodel.base.BaseRecyclerAdapter
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentTabBinding
import com.ghn.cocknovel.viewmodel.RecommendViewModel
import com.kt.NetworkModel.bean.TabFrameBean
import kotlinx.android.synthetic.main.item_tab.view.cst
import kotlinx.android.synthetic.main.item_tab.view.item_tab_author
import kotlinx.android.synthetic.main.item_tab.view.item_tab_desc
import kotlinx.android.synthetic.main.item_tab.view.item_tab_niceDate
import kotlinx.android.synthetic.main.item_tab.view.item_tab_title
import kotlinx.android.synthetic.main.item_tab.view.item_tab_url


class TabFragment : BaseFragment<FragmentTabBinding, RecommendViewModel>() {
    var adapter: BaseRecyclerAdapter<TabFrameBean.Data.Data>? = null

    companion object {
        var tabcid = 294
        val page = 0
        fun tabling(cid: Int) {
            tabcid = cid
        }
    }

    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_tab
    }

    override fun initParam() {

    }

    override fun initViewObservable() {
        tabcid.let { viewModel?.project_content(page, it) }
        val recyclerview_tablist = mutableListOf<TabFrameBean.Data.Data>()
        binding?.fragmentRecyclerview?.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        adapter = object : BaseRecyclerAdapter<TabFrameBean.Data.Data>(recyclerview_tablist) {
            @SuppressLint("CheckResult")
            override fun bindData(holder: BaseViewHolder?, position: Int) {
                holder?.itemView?.item_tab_title?.text = datas[position].title
                holder?.itemView?.item_tab_desc?.text = datas[position].desc
                holder?.itemView?.item_tab_author?.text = datas[position].author
                holder?.itemView?.item_tab_niceDate?.text = datas[position].niceDate
                holder?.itemView?.item_tab_url?.let {
                    Glide.with(this@TabFragment).load(datas[position].envelopePic).into(
                        it
                    )
                }
                holder?.itemView?.cst?.setOnClickListener {
                    viewModel?.setWebview(datas[position].link)
                }
            }

            override val layoutId: Int = R.layout.item_tab
        }
        val controller = LayoutAnimationController(AnimationUtils.loadAnimation(activity, R.anim.animate))
        binding?.fragmentRecyclerview?.layoutAnimation = controller
        binding?.fragmentRecyclerview?.adapter = adapter
        viewModel?.mProjectcontent?.observe(this) {
            adapter?.addData(it.datas)
        }

    }
}