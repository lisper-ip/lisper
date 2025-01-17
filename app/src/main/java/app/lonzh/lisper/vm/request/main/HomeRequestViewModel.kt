package app.lonzh.lisper.vm.request.main

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.ext.launchView
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.lisper.data.BannerBean
import app.lonzh.netlibrary.config.RequestConfig
import app.lonzh.netlibrary.response.PageList
import com.drake.brv.PageRefreshLayout
import rxhttp.async
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/21/21 11:43 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/21/21 11:43 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class HomeRequestViewModel : BaseViewModel() {
    val homeBannerLiveData by lazy { MutableLiveData<MutableList<BannerBean>>() }

    val homeArticlesLiveData by lazy { MutableLiveData<PageList<ArticleBean>>() }

    val collectArticleLiveData by lazy { MutableLiveData<Any>() }

    val unCollectArticleLiveData by lazy { MutableLiveData<Any>() }

    fun getHomeArticles(page: Int) = launchView({
        val bannerResult = RxHttp.get("/banner/json").toLpResponse<MutableList<BannerBean>>().async(this).await()
        homeBannerLiveData.value = bannerResult

        val joined = ArrayList<ArticleBean>()
        if (page == PageRefreshLayout.startIndex) {
            val topResult =
                RxHttp.get("/article/top/json").toLpResponse<List<ArticleBean>>().await()
            joined.addAll(topResult)
            joined.map {
                it.top = true
            }
        }
        val result =
            RxHttp.get("/article/list/$page/json").toLpResponse<PageList<ArticleBean>>().await()
        if (page == PageRefreshLayout.startIndex) {
            joined.addAll(result.datas)
            result.datas = joined
        }
        homeArticlesLiveData.value = result
    }, RequestConfig().setTag("HomeArticles"))

    fun collectArticle(articleBean: ArticleBean) = launch({
        val result = RxHttp.postJson("/lg/collect/${articleBean.id}/json").toLpResponse<Any>().await()
        collectArticleLiveData.value = result
    })

    fun unCollectArticle(articleBean: ArticleBean) = launch({
        val result = RxHttp.postJson("/lg/uncollect_originId/${articleBean.id}/json").toLpResponse<Any>().await()
        unCollectArticleLiveData.value = result
    })
}