package com.ghn.cocknovel.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.PopupWindowCompat.showAsDropDown
import androidx.databinding.DataBindingUtil.setContentView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ghn.cocknovel.BR
import com.ghn.cocknovel.R
import com.ghn.cocknovel.databinding.FragmentMineBinding
import com.ghn.cocknovel.utils.CameraUtils
import com.ghn.cocknovel.utils.CameraUtils.getImageBeforeKitKatPath
import com.ghn.cocknovel.utils.CameraUtils.getImageOnKitKatPath
import com.ghn.cocknovel.utils.CameraUtils.getTakePhotoIntent
import com.ghn.cocknovel.viewmodel.BookStoreViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.kt.network.base.BaseFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class MineFragment : BaseFragment<FragmentMineBinding, BookStoreViewModel>() {
    //底部弹窗
    private var bottomSheetDialog: BottomSheetDialog? = null

    //存储拍完照后的图片
    private var outputImagePath: File? = null

    //启动相机标识
    private val TAKE_PHOTO = 1

    //启动相册标识
    private val SELECT_PHOTO = 2

    //是否拥有权限
    private var hasPermissions = false
    override fun initVariableId(): Int {
        return BR.mode
    }

    override fun initContentView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_mine
    }

    override fun initParam() {

    }

    override fun initViewObservable() {
        binding?.rounIcon?.let { showMsg(it,"1111") }
        binding?.rounIcon?.setOnClickListener {
            XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.CAMERA).permission(Permission.READ_MEDIA_IMAGES)
                .request(object : OnPermissionCallback {

                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                        if (!allGranted) {
//                            toast("获取部分权限成功，但部分权限未正常授予")
                            hasPermissions = false
                            return
                        }
                        hasPermissions = true
                        changeAvatar();
                    }

                    override fun onDenied(
                        permissions: MutableList<String>, doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
//                            toast("被永久拒绝授权，请手动授予录音和日历权限")
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            activity?.let { it1 ->
                                XXPermissions.startPermissionActivity(
                                    it1, permissions
                                )
                            }
                        } else {
//                            toast("获取录音和日历权限失败")
                        }
                    }
                })

        }

    }

    /**
     * 检查版本
     */
    private fun checkVersion() {
        //Android6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果你是在Fragment中，则把this换成getActivity()
            XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.CAMERA).permission(Permission.READ_MEDIA_IMAGES)
                .request(object : OnPermissionCallback {

                    override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                        if (!allGranted) {
//                            toast("获取部分权限成功，但部分权限未正常授予")
                            hasPermissions = false
                            return
                        }
                        hasPermissions = true
                        changeAvatar();
                    }

                    override fun onDenied(
                        permissions: MutableList<String>, doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
//                            toast("被永久拒绝授权，请手动授予录音和日历权限")
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            activity?.let { it1 ->
                                XXPermissions.startPermissionActivity(
                                    it1, permissions
                                )
                            }
                        } else {
//                            toast("获取录音和日历权限失败")
                        }
                    }
                })
        } else {
            //Android6.0以下

        }
    }

    private fun changeAvatar() {
        bottomSheetDialog = BottomSheetDialog(activity!!)
        var bottomView = getLayoutInflater().inflate(R.layout.dialog_bottom, null);
        bottomSheetDialog!!.setContentView(bottomView)
        val tvTakePictures: TextView = bottomView.findViewById(R.id.tv_take_pictures)
        val tvOpenAlbum = bottomView.findViewById<TextView>(R.id.tv_open_album)
        val tvCancel = bottomView.findViewById<TextView>(R.id.tv_cancel)
        //拍照
        tvTakePictures.setOnClickListener { v: View? ->
//            showMsg("拍照")
            takePhoto()
            bottomSheetDialog!!.cancel()
        }
        //打开相册
        tvOpenAlbum.setOnClickListener { v: View? ->
            openAlbum()
//            showMsg("打开相册")
            bottomSheetDialog!!.cancel()
        }
        //取消
        tvCancel.setOnClickListener { v: View? -> bottomSheetDialog!!.cancel() }
        bottomSheetDialog!!.show();
    }

    private fun openAlbum() {
        if (!hasPermissions) {
//            showMsg("未获取到权限");
            checkVersion();
            return;
        }
        startActivityForResult(CameraUtils.selectPhotoIntent, SELECT_PHOTO);
    }

    private fun takePhoto() {
        if (!hasPermissions) {
            checkVersion();
            return;
        }
        val timeStampFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
        val filename = timeStampFormat.format(Date())
        outputImagePath = File(
            activity!!.getExternalCacheDir(),
            "$filename.jpg"
        )
        val takePhotoIntent = activity?.let {
            getTakePhotoIntent(
                it, outputImagePath!!
            )
        }
        // 开启一个带有返回值的Activity，请求码为TAKE_PHOTO
        startActivityForResult(takePhotoIntent, TAKE_PHOTO)
    }

    //图片控件
    private val ivHead: ShapeableImageView? = null

    //Base64
    private var base64Pic: String? = null

    //拍照和相册获取图片的Bitmap
    private var orc_bitmap: Bitmap? = null

    //Glide请求图片选项配置
    private val requestOptions =
        RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE) //不做磁盘缓存
            .skipMemoryCache(true) //不做内存缓存


    /**
     * 返回到Activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PHOTO -> if (resultCode == RESULT_OK) {
                //显示图片
                displayImage(outputImagePath!!.absolutePath)
            }
            SELECT_PHOTO -> if (resultCode == RESULT_OK) {
                //判断手机系统版本号
                val imagePath: String? = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                    //4.4及以上系统使用这个方法处理图片
                    activity?.let { getImageOnKitKatPath(data!!, it) }
                } else {
                    activity?.let { getImageBeforeKitKatPath(data!!, it) }
                }
                //显示图片
                if (imagePath != null) {
                    displayImage(imagePath)
                }
            }
            else -> {}
        }
    }

    /**
     * 通过图片路径显示图片
     */
    private fun displayImage(imagePath: String) {
        if (!TextUtils.isEmpty(imagePath)) {
            //显示图片
            binding?.rounIcon?.let { Glide.with(this).load(imagePath).apply(requestOptions).into(it) }
            //压缩图片
//            orc_bitmap = compression(BitmapFactory.decodeFile(imagePath))
            //转Base64
//            base64Pic = bitmapToBase64(orc_bitmap)
        } else {
//            showMsg("图片获取失败")
            Log.i("TAG", "displayImage: "+"图片获取失败")
        }
    }


}