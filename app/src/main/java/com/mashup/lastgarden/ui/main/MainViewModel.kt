package com.mashup.lastgarden.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.base.extensions.combine
import com.mashup.lastgarden.data.repository.PerfumeRepository
import com.mashup.lastgarden.data.vo.PerfumeAndStories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository
) : ViewModel() {

    private val _todayPerfumeAndStories = MutableStateFlow<PerfumeAndStories?>(null)

    init {
        refreshTodayPerfume()
    }

    private val todayPerfumeItem: StateFlow<MainAdapterItem?> = _todayPerfumeAndStories
        .map { it?.perfume?.toMainAdapterItem() }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val todayPerfumeStories: StateFlow<List<MainAdapterItem.TodayPerfumeStories.TodayPerfumeStoryItem>> =
        _todayPerfumeAndStories
            .map { it?.stories?.toTodayPerfumeStoryItems() ?: emptyList() }
            .distinctUntilChanged()
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun refreshTodayPerfume() {
        viewModelScope.launch(Dispatchers.IO) {
            _todayPerfumeAndStories.value = perfumeRepository.fetchTodayPerfume()
        }
    }

    private val _hotStoriesItem = MutableStateFlow<MainAdapterItem.HotStories?>(null)
    val hotStories: StateFlow<MainAdapterItem.HotStories?> = _hotStoriesItem

    init {
        _hotStoriesItem.value = MainAdapterItem.HotStories(
            listOf(
                MainAdapterItem.HotStories.HotStoryItem(
                    id = "H1",
                    perfumeContentImageUrl = "https://fimgs.net/mdimg/perfume/375x500.30529.jpg",
                    storyImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwEYivLCugzfWz_JMqDWnHjJeHGay3wcwldQ&usqp=CAU",
                    authorName = "Jaemin.Park",
                    authorProfileImage = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxIQEhUQExIVEBUVFRAYEBAWFRUYEhcYFhIWFxUWFRUaHiggGB4lGxYTITEiJSktMC4vFx8zODMtNygtLisBCgoKDg0OGhAQGysmHyUrLS4tMCsrKy0tKy4rLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS03KystN//AABEIAOEA4QMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAAAwUBBAYCB//EAEMQAAIBAgMFBgIGBwUJAAAAAAECAAMRBBIhBTFBUWEGIjJxgZEToUJSYnKCsRQjM5KiwfAHssLR4RUkQ1Njc4Ojs//EABoBAQADAQEBAAAAAAAAAAAAAAADBAUBAgb/xAAlEQEAAgIBBAMAAwEBAAAAAAAAAQIDEQQFEiExE0FRFCJhcTL/2gAMAwEAAhEDEQA/APuMREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQERIMZi0ooalR1povidiAo1tqT5wJ5i8XnzT+07trVwtWnh8M+R0tUrtYEWt3KbA7wRckb/DA+mRNPY9apUoUnqoKVRkRqlMEkKxFyATNyAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICJi8XgZnyn+22jXtQbMThjmUoNAKupBbndb25ZTzn1XNKztJshMbh6mGfc6nK1vCw1Rh5EAzkkKjsr2jV9mLi6p/ZUmFc8S1IWPq1gfxCfMuxGAfam0jXqi4DmviOV7/q6flmyj7qESn/ANqVsNh8Ts1wVL1qZcfVNIn4g63K0bdAZddnu1ybNwnw8Ogq4muc1WowPw6fCmgG+owGthpdjqd05t60+41q601LOwRRvZiAo8ydJrbL2xQxWY0KqVghAcocygkXtcabp8YTsttfajCrWDWNir4hiiAfYpAEr6KPOfTewXZQ7MpVEaqKzVGDMQuVRZbWGpv5zrmnURETrhERAREQEREBERAREQEREBERAREQERMGAvK7aG2aVE5SxZ/+Wilm6X4KNDqSJna+0RQpltC5BFJPrNbQeXM8BecU7kHKDnqN3mY9d7v67h6DQaZ/M5nwxqvtYwYPk9+lnX7XVQ37L4QJOQOC7Pbl8MkKemslbtHXdRamlE8Sxzt6AWA9z5SqpUQuviY73O89Og6CSTHv1PN6iV2OLjSVcVWfxV6jeTZB7UwvzkBS+9nPnUqH8zPcSnbk5be7SmjFSPpXYrYeGqks9FGY+JzfMdw1a9+A9p72Nsqlg3+LQRA3OovxLdFYnMvoZvRO05eas7ixbFS3uF5hu06AgVx8G/0wc1P1Nrr5kW6zoEqAi4IIOoI3EcwZ84x1L6XLfPWx9sPhTp3qe96RO7maZOinpuPTfNnidT7v65FLNxNeavpETVwONSsgqo2ZWGh/MEHcQdCOFp7w+KR75WV8rFWsQbMACQbbjqJsxaJ9KOk8RE6EREBERAREQEREBERAREQEREBNPaWNWhTNRr6WsB4mJ0VV6k2m2Zx+38X8WvlHhpaAc6hHePopC+rytys8Ycc2lJix99ohX4rEsS1aqbtY3A3KOCJ/Wp1k2xtlVK1zcIL/AKyoQWu2ncQXGi7rn2JvbUxOpReb3P4AWH8QWdN2WxS5DR3OjVDbiVdywYcx3iPMTF4cVz5N5fO1/PM46f0a9fs24F0rZj9V1AB6Zl8PnYyn1uVIKspIZTvB0NvYg34gg8Z2WFwZp1KtQ1qlQVChWkxGSllXLamALgHeb31nL7VrLUxFRk1XuKSNxZb5iPcL5oZN1LiYqY+6viUfFzXm2pa0RPFbda+UEopbioZgGb0BJ9Jh0r3Wiq/adRtJRRqhIpo1UjQ5R3QeRYkLfpe8mqYCuouaD242KMfYNc+gnYYegtNAiAKqiyqNwtuEg2Sa5pL+kCmtXvZxSLGn4jlylgD4ct+t59FTpOLt8+2ZPMvvw4mrXUKWO4aMLG4Ogsy713i9900EwrM4BQhA12LWFwNQLXvvy8N06ztZh0D06lhmcsjj6yBGYXHGx0/GZQYZrXpnXJlyniVbw39mH4b8Zl8jB/HtNY8ruHJ8ldykpUggIQmmG8aoxVW55lGnrvl32SqBWqUrAXCOoGg3ZGAHTKn70qIp4o0XWuBfIe+o3lG0cfIMOqCOFyrVy17p8POfDE0nUeXfCZkWHqq6qykMrAFSNxB1BEln1USySIidCIiAiIgIiICIiAiIgIiIHiqwAJPAEn0nzzB1c6hr3LXZvvOczfMmfQq6ZlK8wR7i0+WYFymW/ABXHlofmDMfq8TNIXeFrulYV9Hpnq490JH92SOLkKFd2HeXJcMvDNnBGTiN4vrI8f4CQdRlZOrAgqvqdPWXuyMMUTMws72LA71Fu6p8hv6kzM4uGbzE/i1mvFY0riMRU7n6/XQio7InqQe8Ogv1m3T2PTVb1CWsODMiqBwRVIt8z1lhVpZip3ZWDD91hb+IzW2tmKWVc1zu8hoCeE1q4o+53/1U7vzwomzKTlu68EYjOB0b6X4veDXUg5gwBuCHVgN2oJIsfe0sNn7HN89Uksd6gm3kBwHzPEy6UW0Gg5CQZeFjtO48SljNaI0ptndonpgJmTEKNATUtUA5E6h/M2PMnfLCr2lJHdoEHm7qB/DmJkG2lQoAwDEkWuAfP+usosMArsg0UKhC8FJLAgch3Rp/nPWXLmw49xbbxTFS9vTdxFZ6r/EqNma1lAFlUXvZR10uTqbDoBW0ql69xuKMP3WW35t7ybFYiwsN/wCU08L+1Tyf8hMeb2yTNrL0VisRELWYIvMxK0Pabs5tf9Hf9HqH9WzWRj9Bidx+yxPoTyOncKZ8z2jSvra4IsROs7IbUNWmaTm70rC53sh8DeehU9Vvxn0vTeX8kdlvbM5WHtnuh0MTAmZrKZERAREQEREBERAREQEREDyxnAYzZDVMRV+Fl+EWLCqdVBbV1UDVyGzbrDvWvpOr2zULFaI0DBmqc8oIGX8ROvRSOMhAtoNOQ4SnyoreO2U2KZrO4aOA2UlKza1HG52sSPugaL6C/Mmb0RIIrERqEkzM+yIidCInmsbKx6N+U6Ob2pjLZqh11si87myj5/mZTop1JOZmN2bmeg4DgB0k+16oBpqb73bQE+EBQLD75PoJqmuTuRj1Iyj+Kx+Uz+be0z2wt4YjW00YKqDWCjUhXvyHh0J567pB8Nm8TZR9VL/N9/sBNjBKFqIALCzgAbtQG/wmUNRET+plvERKiRFilup9/aeOz2JNPE0zwcmm3k+7+MJPeIeyn2HrNDB/taP/AHsN/wDdJc4VprkiY/UOeN0l9SWZmBMz69jEREBERAREQEREBERAREQKfbAy1EqfRINNjyLEFCelwV82WRy4r0Q6lWAYMLMDuIO8GUdai9DRg1SnwqC5dRyqKNT94ett5r5scz5hJS30kieadQMMykMDuYEEe4nqVkpEROOkgx9TKjdQQPWYr4xF43PIan/SUOM2g1Y2SxI0+wn3jxPQa+U5a8UjcvVazM+GpUo/EqHWwQBR5t3mB9BT95FVw7L1HMSwo0ggy799yd5JNyT5m89zAz5+/JM/TQpTtrpTzy99GG9SGUcyOHqLj1m/iMJfVd/Ln5TSnK2+4dmFrRqh1DLuO7n1BHAjdNetjLaKPUzSpsyElDa/iU+E9eh6j1BkOd/qezAj5gR8UTO4Nth3LG5N5a9lcCatcPbuUdWPDORZV+ebpZecoWZ/EQFUEFgDmcqD3gDYAG17b9Z9U2dhqdOmq0wAlrrbrre/EnfeanT+NFrd0/Snysuo7f1siZmBMzfZxERAREQEREBERAREQEREBMETMQKDtNQppTaooyVWKqlRSVbMxtc28dhc2NxpKNcZXG6oG+/TB/uFZadrH79FOlV/UBUHyqNKefPdS5NqZe2rR4uKJpuUrbQxHBqQ/wDG/wDN5r1Kld/FWFvspb82I+U9xM/+Zl/Vn4afjX/Q1PiLVOjHu+qrZT6iTqLaAWHACZiQ3y3v/wCpSRWI9EREjdJq4yhfvDfxm1E7E6kU8STEU8rEe3lI5O8k73sjiM+Fp33pmpn8DFV91Cn1nBTq+wdXSsn2kf8AeTKR/wCu/rNPpd9ZJr+qfMjdYl1kRE+gZxERAREQEREBERAREQE1Np45cPTaq98q5b2Fz3mCiw8yJtyDGYdaqNTYXVgQw6ETk+vAraXaTDtvZk++jAfvWy/OblHalB9FrU2PIOpPtecFVR8NUajU1tqD9ZTucfzHA36E7AyuODDqAflMW/U747dt6r0cSto3WVt2la9dbG/6r83N/wAhKyVuFyiubKFDIw0AHhZbbuhMspk8zL8uTv8A1cw07K9pERKqUiIgIiICIiBp7QXcfMf185pyzqYZqzCkhAOrFiCQoGmoHM2HueErHVlb4bKRUBt8Mak8ilvEDzHysbW6YrzSLaRzeu9E6bsKvfrH7NAfOrNHBbCuM1dsg4Uwwv8AicbvJffhLzsXhPh06jXzZq1XId/cpn4ai/HwsfxTU4HGtXJ3Sp8nLE11DohMzAMzNxQIiICIiAiIgIiICIiAiIgVm3NkJiUynusutOoN6n+YO4jj5gEcDXpVKDmm4yOPZh9ZTxX8uM+omaO09l0sQuWot7aqdzKeasNQZS5fErmj/U+HNOOf8fNGqZSKn1TdvukEN8iT6CXUztDsxXpa0/8AeF9FqjzXRW8xbylbgMRkb9HcGmwv8JXUqxUfRswBuvzFt+swM/EyUjzHpo481LepWMREoJyIkT4hRx9tYiNiWJr/AKYvX2ntMQh+kB56TvbJtLMEgak2A3nhPJqqPpD3EjDrVdKQ1zuobllF2ceqqw9Z7x45teKuWtqNt/C3pUc50qVteqr9FellPuxnunjyF3AuLgOd9v5zxtOrmqHpp/n85qz6alIrWIZ0+XupULG7G/nJMNWSmjZjU0LNYVKgXXVu6Gtvud3GQSw2Fs/41TOR+rpkHozg6DqF3nrYcDJaxMzqHi8xEeV/sHC/DparkZyXZPqlty+YAUHqDLKYAmZchVIiICIiAiIgIiICIiAiIgIiIGLTWx+ASuhp1BmU8OIPAg8CDrebUwTOTETGpNvn209l18Lc3NWkN1UC5A/6ijd94ace7umgMaxFwQQdxE7DaNZsRdKb5KYJBcb6hBsQp4INQSNSeg157EbAtqEy/apm3uu4+omLyeBjmd0X8Oe2v7Kx6hO8zzNh9nVF+kD0ZCD7g2+U8LgKp+kg/Azf4hKP8LJ+LPzVRTBMt9k7BNR8laoygi6FFC5iPEpzZtbWI/FynVYTs5hqeopBiNzPdyPLNe3pLOLpl7eZlDfl1jxDgcNTar+zRqvVFLL+8NB6mW2A2TXpVKVapT+GgYqbspa7oyrotxa5A38d070LI8ThxUUowupFiP63S/i6djpO/tWvyrW8OJxa2dr/AFj8zIpe43YNVjdait1dSGtwuRox9BM4XszrerUzD6iAqPVr3PpaWIxWc+WulVs3ANiGst1QGz1eVt6pzbhyHHkeywuHWmoRBlVRYKJ6o0gihVAUAWCgAADkAN0kk9axWENrTYiInp5IiICIiAiIgIiICIiAiIgIiICQ4uj8RGS5XMrDMpswuLXB4GTRAqE2MQAPjOoAAAVaQAAGgHckWJwlSj3szVk+kCB8RftDIAGHMWv57peTBEjnHWXqLTDnqeI+JpTBrcytso83JC+l79JKadYf8Bj91qX82EvMszPMYa6d+SXOsHLIBSqKwemRddAAwz3cEr4C438dJ0Ii0zJK17fTzM7IiJ6cIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgf//Z",
                    title = "My Memory",
                    likeCount = 12
                ),
                MainAdapterItem.HotStories.HotStoryItem(
                    id = "H2",
                    perfumeContentImageUrl = "https://fimgs.net/mdimg/perfume/375x500.30529.jpg",
                    storyImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwEYivLCugzfWz_JMqDWnHjJeHGay3wcwldQ&usqp=CAU",
                    authorName = "Jaemin.Park",
                    authorProfileImage = "data:image/jpeg;base64,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",
                    title = "My Memory",
                    likeCount = 12
                ),
                MainAdapterItem.HotStories.HotStoryItem(
                    id = "H3",
                    perfumeContentImageUrl = "https://fimgs.net/mdimg/perfume/375x500.30529.jpg",
                    storyImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwEYivLCugzfWz_JMqDWnHjJeHGay3wcwldQ&usqp=CAU",
                    authorName = "Jaemin.Park",
                    authorProfileImage = "data:image/jpeg;base64,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",
                    title = "My Memory",
                    likeCount = 12
                ),
                MainAdapterItem.HotStories.HotStoryItem(
                    id = "H4",
                    perfumeContentImageUrl = "https://fimgs.net/mdimg/perfume/375x500.30529.jpg",
                    storyImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwEYivLCugzfWz_JMqDWnHjJeHGay3wcwldQ&usqp=CAU",
                    authorName = "Jaemin.Park",
                    authorProfileImage = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxIQEhUQExIVEBUVFRAYEBAWFRUYEhcYFhIWFxUWFRUaHiggGB4lGxYTITEiJSktMC4vFx8zODMtNygtLisBCgoKDg0OGhAQGysmHyUrLS4tMCsrKy0tKy4rLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS03KystN//AABEIAOEA4QMBIgACEQEDEQH/xAAbAAEAAgMBAQAAAAAAAAAAAAAAAwUBBAYCB//EAEMQAAIBAgMFBgIGBwUJAAAAAAECAAMRBBIhBTFBUWEGIjJxgZEToUJSYnKCsRQjM5KiwfAHssLR4RUkQ1Njc4Ojs//EABoBAQADAQEBAAAAAAAAAAAAAAADBAUBAgb/xAAlEQEAAgIBBAMAAwEBAAAAAAAAAQIDEQQFEiExE0FRFCJhcTL/2gAMAwEAAhEDEQA/APuMREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQERIMZi0ooalR1povidiAo1tqT5wJ5i8XnzT+07trVwtWnh8M+R0tUrtYEWt3KbA7wRckb/DA+mRNPY9apUoUnqoKVRkRqlMEkKxFyATNyAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICJi8XgZnyn+22jXtQbMThjmUoNAKupBbndb25ZTzn1XNKztJshMbh6mGfc6nK1vCw1Rh5EAzkkKjsr2jV9mLi6p/ZUmFc8S1IWPq1gfxCfMuxGAfam0jXqi4DmviOV7/q6flmyj7qESn/ANqVsNh8Ts1wVL1qZcfVNIn4g63K0bdAZddnu1ybNwnw8Ogq4muc1WowPw6fCmgG+owGthpdjqd05t60+41q601LOwRRvZiAo8ydJrbL2xQxWY0KqVghAcocygkXtcabp8YTsttfajCrWDWNir4hiiAfYpAEr6KPOfTewXZQ7MpVEaqKzVGDMQuVRZbWGpv5zrmnURETrhERAREQEREBERAREQEREBERAREQERMGAvK7aG2aVE5SxZ/+Wilm6X4KNDqSJna+0RQpltC5BFJPrNbQeXM8BecU7kHKDnqN3mY9d7v67h6DQaZ/M5nwxqvtYwYPk9+lnX7XVQ37L4QJOQOC7Pbl8MkKemslbtHXdRamlE8Sxzt6AWA9z5SqpUQuviY73O89Og6CSTHv1PN6iV2OLjSVcVWfxV6jeTZB7UwvzkBS+9nPnUqH8zPcSnbk5be7SmjFSPpXYrYeGqks9FGY+JzfMdw1a9+A9p72Nsqlg3+LQRA3OovxLdFYnMvoZvRO05eas7ixbFS3uF5hu06AgVx8G/0wc1P1Nrr5kW6zoEqAi4IIOoI3EcwZ84x1L6XLfPWx9sPhTp3qe96RO7maZOinpuPTfNnidT7v65FLNxNeavpETVwONSsgqo2ZWGh/MEHcQdCOFp7w+KR75WV8rFWsQbMACQbbjqJsxaJ9KOk8RE6EREBERAREQEREBERAREQEREBNPaWNWhTNRr6WsB4mJ0VV6k2m2Zx+38X8WvlHhpaAc6hHePopC+rytys8Ycc2lJix99ohX4rEsS1aqbtY3A3KOCJ/Wp1k2xtlVK1zcIL/AKyoQWu2ncQXGi7rn2JvbUxOpReb3P4AWH8QWdN2WxS5DR3OjVDbiVdywYcx3iPMTF4cVz5N5fO1/PM46f0a9fs24F0rZj9V1AB6Zl8PnYyn1uVIKspIZTvB0NvYg34gg8Z2WFwZp1KtQ1qlQVChWkxGSllXLamALgHeb31nL7VrLUxFRk1XuKSNxZb5iPcL5oZN1LiYqY+6viUfFzXm2pa0RPFbda+UEopbioZgGb0BJ9Jh0r3Wiq/adRtJRRqhIpo1UjQ5R3QeRYkLfpe8mqYCuouaD242KMfYNc+gnYYegtNAiAKqiyqNwtuEg2Sa5pL+kCmtXvZxSLGn4jlylgD4ct+t59FTpOLt8+2ZPMvvw4mrXUKWO4aMLG4Ogsy713i9900EwrM4BQhA12LWFwNQLXvvy8N06ztZh0D06lhmcsjj6yBGYXHGx0/GZQYZrXpnXJlyniVbw39mH4b8Zl8jB/HtNY8ruHJ8ldykpUggIQmmG8aoxVW55lGnrvl32SqBWqUrAXCOoGg3ZGAHTKn70qIp4o0XWuBfIe+o3lG0cfIMOqCOFyrVy17p8POfDE0nUeXfCZkWHqq6qykMrAFSNxB1BEln1USySIidCIiAiIgIiICIiAiIgIiIHiqwAJPAEn0nzzB1c6hr3LXZvvOczfMmfQq6ZlK8wR7i0+WYFymW/ABXHlofmDMfq8TNIXeFrulYV9Hpnq490JH92SOLkKFd2HeXJcMvDNnBGTiN4vrI8f4CQdRlZOrAgqvqdPWXuyMMUTMws72LA71Fu6p8hv6kzM4uGbzE/i1mvFY0riMRU7n6/XQio7InqQe8Ogv1m3T2PTVb1CWsODMiqBwRVIt8z1lhVpZip3ZWDD91hb+IzW2tmKWVc1zu8hoCeE1q4o+53/1U7vzwomzKTlu68EYjOB0b6X4veDXUg5gwBuCHVgN2oJIsfe0sNn7HN89Uksd6gm3kBwHzPEy6UW0Gg5CQZeFjtO48SljNaI0ptndonpgJmTEKNATUtUA5E6h/M2PMnfLCr2lJHdoEHm7qB/DmJkG2lQoAwDEkWuAfP+usosMArsg0UKhC8FJLAgch3Rp/nPWXLmw49xbbxTFS9vTdxFZ6r/EqNma1lAFlUXvZR10uTqbDoBW0ql69xuKMP3WW35t7ybFYiwsN/wCU08L+1Tyf8hMeb2yTNrL0VisRELWYIvMxK0Pabs5tf9Hf9HqH9WzWRj9Bidx+yxPoTyOncKZ8z2jSvra4IsROs7IbUNWmaTm70rC53sh8DeehU9Vvxn0vTeX8kdlvbM5WHtnuh0MTAmZrKZERAREQEREBERAREQEREDyxnAYzZDVMRV+Fl+EWLCqdVBbV1UDVyGzbrDvWvpOr2zULFaI0DBmqc8oIGX8ROvRSOMhAtoNOQ4SnyoreO2U2KZrO4aOA2UlKza1HG52sSPugaL6C/Mmb0RIIrERqEkzM+yIidCInmsbKx6N+U6Ob2pjLZqh11si87myj5/mZTop1JOZmN2bmeg4DgB0k+16oBpqb73bQE+EBQLD75PoJqmuTuRj1Iyj+Kx+Uz+be0z2wt4YjW00YKqDWCjUhXvyHh0J567pB8Nm8TZR9VL/N9/sBNjBKFqIALCzgAbtQG/wmUNRET+plvERKiRFilup9/aeOz2JNPE0zwcmm3k+7+MJPeIeyn2HrNDB/taP/AHsN/wDdJc4VprkiY/UOeN0l9SWZmBMz69jEREBERAREQEREBERAREQKfbAy1EqfRINNjyLEFCelwV82WRy4r0Q6lWAYMLMDuIO8GUdai9DRg1SnwqC5dRyqKNT94ett5r5scz5hJS30kieadQMMykMDuYEEe4nqVkpEROOkgx9TKjdQQPWYr4xF43PIan/SUOM2g1Y2SxI0+wn3jxPQa+U5a8UjcvVazM+GpUo/EqHWwQBR5t3mB9BT95FVw7L1HMSwo0ggy799yd5JNyT5m89zAz5+/JM/TQpTtrpTzy99GG9SGUcyOHqLj1m/iMJfVd/Ln5TSnK2+4dmFrRqh1DLuO7n1BHAjdNetjLaKPUzSpsyElDa/iU+E9eh6j1BkOd/qezAj5gR8UTO4Nth3LG5N5a9lcCatcPbuUdWPDORZV+ebpZecoWZ/EQFUEFgDmcqD3gDYAG17b9Z9U2dhqdOmq0wAlrrbrre/EnfeanT+NFrd0/Snysuo7f1siZmBMzfZxERAREQEREBERAREQEREBMETMQKDtNQppTaooyVWKqlRSVbMxtc28dhc2NxpKNcZXG6oG+/TB/uFZadrH79FOlV/UBUHyqNKefPdS5NqZe2rR4uKJpuUrbQxHBqQ/wDG/wDN5r1Kld/FWFvspb82I+U9xM/+Zl/Vn4afjX/Q1PiLVOjHu+qrZT6iTqLaAWHACZiQ3y3v/wCpSRWI9EREjdJq4yhfvDfxm1E7E6kU8STEU8rEe3lI5O8k73sjiM+Fp33pmpn8DFV91Cn1nBTq+wdXSsn2kf8AeTKR/wCu/rNPpd9ZJr+qfMjdYl1kRE+gZxERAREQEREBERAREQE1Np45cPTaq98q5b2Fz3mCiw8yJtyDGYdaqNTYXVgQw6ETk+vAraXaTDtvZk++jAfvWy/OblHalB9FrU2PIOpPtecFVR8NUajU1tqD9ZTucfzHA36E7AyuODDqAflMW/U747dt6r0cSto3WVt2la9dbG/6r83N/wAhKyVuFyiubKFDIw0AHhZbbuhMspk8zL8uTv8A1cw07K9pERKqUiIgIiICIiBp7QXcfMf185pyzqYZqzCkhAOrFiCQoGmoHM2HueErHVlb4bKRUBt8Mak8ilvEDzHysbW6YrzSLaRzeu9E6bsKvfrH7NAfOrNHBbCuM1dsg4Uwwv8AicbvJffhLzsXhPh06jXzZq1XId/cpn4ai/HwsfxTU4HGtXJ3Sp8nLE11DohMzAMzNxQIiICIiAiIgIiICIiAiIgVm3NkJiUynusutOoN6n+YO4jj5gEcDXpVKDmm4yOPZh9ZTxX8uM+omaO09l0sQuWot7aqdzKeasNQZS5fErmj/U+HNOOf8fNGqZSKn1TdvukEN8iT6CXUztDsxXpa0/8AeF9FqjzXRW8xbylbgMRkb9HcGmwv8JXUqxUfRswBuvzFt+swM/EyUjzHpo481LepWMREoJyIkT4hRx9tYiNiWJr/AKYvX2ntMQh+kB56TvbJtLMEgak2A3nhPJqqPpD3EjDrVdKQ1zuobllF2ceqqw9Z7x45teKuWtqNt/C3pUc50qVteqr9FellPuxnunjyF3AuLgOd9v5zxtOrmqHpp/n85qz6alIrWIZ0+XupULG7G/nJMNWSmjZjU0LNYVKgXXVu6Gtvud3GQSw2Fs/41TOR+rpkHozg6DqF3nrYcDJaxMzqHi8xEeV/sHC/DparkZyXZPqlty+YAUHqDLKYAmZchVIiICIiAiIgIiICIiAiIgIiIGLTWx+ASuhp1BmU8OIPAg8CDrebUwTOTETGpNvn209l18Lc3NWkN1UC5A/6ijd94ace7umgMaxFwQQdxE7DaNZsRdKb5KYJBcb6hBsQp4INQSNSeg157EbAtqEy/apm3uu4+omLyeBjmd0X8Oe2v7Kx6hO8zzNh9nVF+kD0ZCD7g2+U8LgKp+kg/Azf4hKP8LJ+LPzVRTBMt9k7BNR8laoygi6FFC5iPEpzZtbWI/FynVYTs5hqeopBiNzPdyPLNe3pLOLpl7eZlDfl1jxDgcNTar+zRqvVFLL+8NB6mW2A2TXpVKVapT+GgYqbspa7oyrotxa5A38d070LI8ThxUUowupFiP63S/i6djpO/tWvyrW8OJxa2dr/AFj8zIpe43YNVjdait1dSGtwuRox9BM4XszrerUzD6iAqPVr3PpaWIxWc+WulVs3ANiGst1QGz1eVt6pzbhyHHkeywuHWmoRBlVRYKJ6o0gihVAUAWCgAADkAN0kk9axWENrTYiInp5IiICIiAiIgIiICIiAiIgIiICQ4uj8RGS5XMrDMpswuLXB4GTRAqE2MQAPjOoAAAVaQAAGgHckWJwlSj3szVk+kCB8RftDIAGHMWv57peTBEjnHWXqLTDnqeI+JpTBrcytso83JC+l79JKadYf8Bj91qX82EvMszPMYa6d+SXOsHLIBSqKwemRddAAwz3cEr4C438dJ0Ii0zJK17fTzM7IiJ6cIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgf//Z",
                    title = "My Memory",
                    likeCount = 12
                ),
                MainAdapterItem.HotStories.HotStoryItem(
                    id = "H5",
                    perfumeContentImageUrl = "https://fimgs.net/mdimg/perfume/375x500.30529.jpg",
                    storyImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwEYivLCugzfWz_JMqDWnHjJeHGay3wcwldQ&usqp=CAU",
                    authorName = "Jaemin.Park",
                    authorProfileImage = "data:image/jpeg;base64,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",
                    title = "My Memory",
                    likeCount = 12
                )
            )
        )
    }

    private val _rankingsItem = MutableStateFlow<MainAdapterItem.PerfumeRankings?>(null)
    val rankingsItem: StateFlow<MainAdapterItem.PerfumeRankings?> = _rankingsItem

    init {
        _rankingsItem.value = MainAdapterItem.PerfumeRankings(
            listOf(
                MainAdapterItem.PerfumeRankings.PerfumeRankingItem(
                    id = "PR1",
                    rank = 1,
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.1444.jpg",
                    brandName = "Halloween",
                    name = "Halloween"
                ),
                MainAdapterItem.PerfumeRankings.PerfumeRankingItem(
                    id = "PR2",
                    rank = 2,
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.228.jpg",
                    brandName = "Dior",
                    name = "Fahrenheit"
                ),
                MainAdapterItem.PerfumeRankings.PerfumeRankingItem(
                    id = "PR3",
                    rank = 3,
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.51488.jpg",
                    brandName = "Givenchy",
                    name = "L'Interdit Eau de Parfum"
                ),
                MainAdapterItem.PerfumeRankings.PerfumeRankingItem(
                    id = "PR4",
                    rank = 4,
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.33253.jpg",
                    brandName = "Rive",
                    name = "Madame in Love La"
                ),
                MainAdapterItem.PerfumeRankings.PerfumeRankingItem(
                    id = "PR5",
                    rank = 5,
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.18930.jpg",
                    brandName = "Mugler",
                    name = "Mugler Show"
                ),
            )
        )
    }

    private val _recommendsItem = MutableStateFlow<MainAdapterItem.PerfumeRecommends?>(null)
    val recommendsItem: StateFlow<MainAdapterItem.PerfumeRecommends?> = _recommendsItem

    init {
        _recommendsItem.value = MainAdapterItem.PerfumeRecommends(
            listOf(
                MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem(
                    id = "PRI1",
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.33494.jpg",
                    name = "Love Spell Fragrance Mist",
                    brandName = "Victoria's Secret",
                    likeCount = 24
                ),
                MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem(
                    id = "PRI2",
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.631.jpg",
                    name = "Crystal Noir",
                    brandName = "Versace",
                    likeCount = 15
                ),
                MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem(
                    id = "PRI3",
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.631.jpg",
                    name = "Le Beau Jean",
                    brandName = "Paul Gaultier",
                    likeCount = 8
                ),
                MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem(
                    id = "PRI4",
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.17.jpg",
                    name = "Terre d'Hermes",
                    brandName = "Hermès",
                    likeCount = 6
                ),
                MainAdapterItem.PerfumeRecommends.PerfumeRecommendItem(
                    id = "PRI5",
                    imageUrl = "https://fimgs.net/mdimg/perfume/375x500.55858.jpg",
                    name = "Toy Boy",
                    brandName = "Moschino",
                    likeCount = 3
                ),
            )
        )
    }

    private val _mainItems = MutableStateFlow<List<MainAdapterItem>>(emptyList())
    val mainItems: StateFlow<List<MainAdapterItem>> = _mainItems

    init {
        viewModelScope.launch {
            todayPerfumeItem.combine(
                todayPerfumeStories,
                _hotStoriesItem.combine(rankingsItem, recommendsItem),
            )
                .map { (todayPerfume, todayPerfumeStories, hotStoriesAndRankingsAndRecommendsItem) ->
                    val (hotStoriesItem, rankingsItem, recommendsItem) = hotStoriesAndRankingsAndRecommendsItem
                    listOfNotNull(
                        todayPerfume,
                        todayPerfumeStories.toMainAdapterItem(),
                        MainAdapterItem.RefreshAnotherPerfume,
                        MainAdapterItem.Banner,
                        MainAdapterItem.HotStoryHeader,
                        hotStoriesItem,
                        MainAdapterItem.PerfumeRankingsHeader,
                        rankingsItem,
                        MainAdapterItem.PerfumeRecommendsHeader,
                        recommendsItem,
                        MainAdapterItem.SeeMore
                    )
                }
                .collectLatest { _mainItems.value = it }
        }
    }
}