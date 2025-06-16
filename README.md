基于 **模块化+Kotlin+协程+Retrofit+Jetpack+MVVM** 架构实现的 WanAndroid 客户端。 能提供大家学习如何从0到1打造一个符合[大型Android项目的架构模式]

|                             项目截图                             |                             项目截图                             |                             项目截图                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](https://github.com/Gao-hao-nan/MVVM/blob/master/image/image_gif1.gif) | ![](https://github.com/Gao-hao-nan/MVVM/blob/master/image/image_gif2.gif) | ![](https://github.com/Gao-hao-nan/MVVM/blob/master/image/image_gif3.gif)
### 1. 项目架构
1. 项目采用 Kotlin 语言编写，结合 Jetpack 相关控件，`Navigation`，`Lifecyle`，`DataBinding`，`LiveData`，`ViewModel`等搭建的 **MVVM** 架构模式；
2. 通过**组件化**，**模块化**拆分，实现项目更好解耦和复用
3. 使用 **协程+Retrofit+OkHttp** 优雅地实现网络请求；
4. 通过 **mmkv**，**Room** 数据库等实现对数据缓存的管理；
5. 使用 **Glide** 完成图片加载；
6. 通过RxAppCompatActivity+RxLifecycleAndroid 封装的基类

### 2. 新增了event模块（通过flow实现的轻量级）
| 功能点               | 方法                                            | 说明                            |
| ----------------- | --------------------------------------------- | ----------------------------- |
| 🔹 发送事件           | `EventChannel.post(event)`                    | 发送任意类型的事件                     |
| 🔹 订阅事件（支持粘性）     | `EventChannel.observe<T>(sticky = true)`      | 可指定是否接收历史事件                   |
| 🔹 只接收历史事件        | `EventChannel.observeOnlySticky<T>()`         | 不再继续监听新事件（可选）                 |
| 🔹 设置某类型粘性事件最大缓存数 | `EventChannel.setMaxStickyCacheSize<T>(size)` | 默认最多缓存 10 条                   |
| 🔹 获取粘性历史事件       | `EventChannel.getStickyEvents<T>()`           | 返回 List\<Pair\<T, timestamp>> |
| 🔹 清除某类粘性事件       | `EventChannel.clearStickyEvents<T>()`         | 清除某个事件的历史                     |
| 🔹 清除全部粘性事件       | `EventChannel.clearAllStickyEvents()`         | 全局清除所有缓存事件                    |
| 🔹 生命周期感知收集事件     | `flow.collectIn(owner)`                       | 自动在 LifecycleOwner 生命周期内取消    |

欢迎在 **Issue** 中提交对本仓库的改进建议~
有问题请联系QQ:1931672489
感谢您的阅读~

### 致谢

**API：**  鸿洋提供的 [**WanAndroid API**](https://www.wanandroid.com/blog/show/2)

**主要使用的开源框架:**

*   [**Retrofit**](https://github.com/square/retrofit)
*   [**OkHttp**](https://github.com/square/okhttp)
*   [**Glide**](https://github.com/bumptech/glide)
*   [**ARouter**](https://github.com/alibaba/ARouter)
*   [**MMKV**](https://github.com/Tencent/MMKV)
*   [**SmartRefreshLayout**](https://github.com/scwang90/SmartRefreshLayout)
