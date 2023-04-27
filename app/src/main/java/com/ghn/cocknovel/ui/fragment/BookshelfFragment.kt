package com.ghn.cocknovel.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basemodel.base.BaseFragment
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentBookshelfBinding
import com.ghn.cocknovel.ui.adapter.BaseRecyclerAdapter
import com.ghn.cocknovel.viewmodel.RecommendViewModel
import com.kt.network.bean.Datas
import kotlinx.android.synthetic.main.fragment_bookshelf.*
import kotlinx.android.synthetic.main.fragment_bookshelf.RecommRecyclerview
import kotlinx.android.synthetic.main.fragment_recommend.*


class BookshelfFragment : BaseFragment<FragmentBookshelfBinding, RecommendViewModel>() {
    var adapter: BaseRecyclerAdapter<Datas>? = null
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_bookshelf
    }

    override fun initParam() {
    }

    override fun initViewObservable() {
        val RecommList = mutableListOf<Datas>()
        viewModel?.getwan()
        RecommRecyclerview.layoutManager = LinearLayoutManager(activity)
        adapter = object : BaseRecyclerAdapter<Datas>(RecommList) {
            override fun bindData(holder: BaseViewHolder?, position: Int) {
                val text: TextView = holder?.getView(R.id.recomm_title) as TextView
                text.text = datas[position].title
            }

            override val layoutId: Int = R.layout.animation
        }
        val controller =
            LayoutAnimationController(AnimationUtils.loadAnimation(activity, R.anim.animate))
        RecommRecyclerview.layoutAnimation = controller
        RecommRecyclerview.adapter = adapter
        viewModel?.loginStatus?.observe(this) {
            adapter?.addData(it.datas)
        }
    }
}