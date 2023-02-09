import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter

import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ghn.cocknovel.R
import com.ghn.cocknovel.bean.FontBean
import com.ghn.cocknovel.databinding.FontItemRecyclerviewBinding
import com.ghn.cocknovel.viewmodel.BookStoreViewModel

/**
 * @author 浩楠
 *
 * @date 2023/1/16   13:58.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * 描述:
 */
class FontAdapter(model: BookStoreViewModel) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.font_item_recyclerview) {
    private var model: BookStoreViewModel? = model
    private lateinit var listdatafont:List<FontBean>
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
//        holder.getView<TextView>(R.id.font_name).text
//        holder.getView<TextView>(R.id.font_name).text=listdatafont.get(position).name

    }
    override fun convert(holder: BaseViewHolder, item: String) {
        if (DataBindingUtil.getBinding<ViewDataBinding?>(holder.itemView) == null) {
            FontItemRecyclerviewBinding.bind(holder.itemView)
        }
        val binding = holder.getBinding<FontItemRecyclerviewBinding>()
        binding?.mode = model

    }
}