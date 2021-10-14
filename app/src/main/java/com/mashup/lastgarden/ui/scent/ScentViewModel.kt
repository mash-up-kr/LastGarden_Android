package com.mashup.lastgarden.ui.scent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mashup.lastgarden.data.vo.ScentItem

class ScentViewModel : ViewModel() {

    private val _scentList = MutableLiveData<List<ScentItem>>()
    val scentList: LiveData<List<ScentItem>>
        get() = _scentList

    var _position = MutableLiveData<Int>()
    val position: LiveData<Int>
        get() = _position

    init {
        _position.value = 0
    }

    fun getScentList(idx: Int) {
        val tagList1 = listOf(
            "#노을",
            "#해질녘",
            "#선선함",
            "#가을",
            "#노을",
            "#해질녘",
            "#선선함",
            "#가을",
            "#노을",
            "#해질녘",
            "#선선함",
            "#가을",
            "#노을",
            "#해질녘",
            "#선선함",
            "#가을",
            "#노을",
            "#해질녘",
            "#선선함",
            "#가을",
            "#노을",
            "#해질녘",
            "#선선함",
            "#가을"
        )
        val tagList2 = listOf("뽀송함", "빨래", "섬유유연제")
        val tagList3 = listOf("꽃향", "싱그러운", "꽃밭")

        _scentList.value =
            listOf(
                ScentItem(
                    1,
                    "https://us.123rf.com/450wm/farang/farang1304/farang130400041/19250164-%EC%9E%A5%EC%97%84%ED%95%9C-%EC%9D%BC%EB%AA%B0-%ED%95%98%EB%8A%98-%EC%84%B8%EB%A1%9C-%ED%8C%8C%EB%85%B8%EB%9D%BC%EB%A7%88.jpg?ver=6",
                    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA5MDdfODIg%2FMDAxNjMwOTQwODYxMDgx.Obn1Gf6jMUxtD9H7119x7GzvX3s-AsJWhUWRo-p0mbEg.Cjp4iVPwcsvVXlhUNrWcfPumylrr2l_OCeXtnQnVfSkg.JPEG.gkqlsgkqls7%2FIMG_1618.jpg&type=sc960_832",
                    "노을과고양이",
                    tagList1,
                    11,
                    35,
                    true
                ),
                ScentItem(
                    2, "https://imgc.1300k.com/aaaaaib/goods/215025/54/215025540976.jpg?3",
                    "https://file.mk.co.kr/meet/neds/2020/04/image_readtop_2020_356074_15861373034151671.jpg",
                    "다유니",
                    tagList2,
                    32,
                    51,
                    false
                ),
                ScentItem(
                    3,
                    "https://file.mk.co.kr/meet/neds/2021/05/image_readtop_2021_505022_16219901034654699.jpg",
                    "https://img9.yna.co.kr/photo/yna/YH/2021/03/04/PYH2021030403860000500_P4.jpg",
                    "라일락일락",
                    tagList3,
                    51,
                    56,
                    true
                )
            )
    }

}