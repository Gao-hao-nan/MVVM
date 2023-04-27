package com.kt.network.bean

import com.kt.network.net.IBaseResponse
import java.lang.reflect.Array
import java.util.ArrayList

/**
 * @author 浩楠
 *
 * @date 2023/1/16   14:44.
 *
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * 描述:
 */
open class BaseResult<T> : IBaseResponse<T>() {
   var success:Boolean = false
   var errorCode:Int=0
   var errorMsg:String =""
   var data:T? = null
   override fun errorCode(): Int {
      return errorCode
   }

   override fun errorMsg(): String {
      return errorMsg
   }


   override fun data():T? {
      return data
   }


   override fun isSuccess(): Boolean {
      return errorCode == 0
   }

}
open class Response<T>(
   val errorCode :Int?,
   val errorMsg: String?,
   val data:T?
)
data class FontDataNew(
   val curPage :Double,
   val datas :ArrayList<Datas>,
   val offset :Double,
   val over :Boolean,
   val pageCount:Double,
   val size :Double,
   val total :Double,
)
data class Datas(
   val adminAdd :Boolean,
   val apkLink :String,
   val audit:Double,
   val author:String,
   val canEdit:Boolean,
   val chapterId:Double,
   val chapterName:String,
   val collect :Boolean,
   val courseId :Double,
   val desc:String,
   val descMd :String,
   val envelopePic:String,
   val fresh :Boolean,
   val host :String,
   val id :Double,
   val isAdminAdd :Boolean,
   val link :String,
   val niceDate:String,
   val niceShareDate :String,
   val origin :String,
   val prefix  :String,
   val projectLink :String,
   val publishTime :Double,
   val realSuperChapterId :Double,
   val route :Boolean,
   val selfVisible :Double,
   val shareDate :Double,
   val shareUser :String,
   val superChapterId :Double,
   val superChapterName:String,
   val tags :Any,
   val title :String,
   val type :Double,
   val userId :Double,
   val visible:Double,
   val zan :Double,
)
data class FontData(
   val title:String,
   val articleList: Any,
   val author :String,
   val children:Any,
   val courseId:Double,
   val cover:String,
   val desc:String,
   val id:Double,
   val lisense:String,
   val lisenseLink:String,
   val name:String,
   val order:Double,
   val parentChapterId:Double,
   val type:Double,
   val userControlSetTop:Boolean,
   val visible:Double,


)
data class SearchData(
   val chapterName:String,
   val desc:String,
   val envelopePic:String,
)
data class ColorData(
   val id : Int,
   val title:String
)

