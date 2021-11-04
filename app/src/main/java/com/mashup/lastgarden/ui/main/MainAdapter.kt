package com.mashup.lastgarden.ui.main

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mashup.base.image.GlideRequests
import com.mashup.lastgarden.R
import com.mashup.lastgarden.databinding.ItemHotStoriesHeaderBinding
import com.mashup.lastgarden.databinding.ItemMainBannerBinding
import com.mashup.lastgarden.databinding.ItemMainHotStoriesBinding
import com.mashup.lastgarden.databinding.ItemMainPerfumeRankingsBinding
import com.mashup.lastgarden.databinding.ItemMainPerfumeRecommendsBinding
import com.mashup.lastgarden.databinding.ItemMainSeeMoreBinding
import com.mashup.lastgarden.databinding.ItemMainTodayPerfumeBinding
import com.mashup.lastgarden.databinding.ItemMainTodayPerfumeStoriesBinding
import com.mashup.lastgarden.databinding.ItemPerfumeRankingHeaderBinding
import com.mashup.lastgarden.databinding.ItemPerfumeRecommendHeaderBinding
import com.mashup.lastgarden.databinding.ItemRefreshAnotherPerfumeBinding

class MainAdapter(
    private val glideRequests: GlideRequests,
    private val todayPerfumeStoryAdapter: TodayPerfumeStoryAdapter,
    private val hotStoryAdapter: HotStoryAdapter,
    private val rankingAdapter: PerfumeRankingAdapter,
    private val recommendAdapter: PerfumeRecommendAdapter,
    private val mainItemClickListener: OnMainItemClickListener
) : ListAdapter<MainAdapterItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MainAdapterItem>() {
            override fun areItemsTheSame(
                oldItem: MainAdapterItem,
                newItem: MainAdapterItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: MainAdapterItem,
                newItem: MainAdapterItem
            ): Boolean = oldItem == newItem
        }
    }

    interface OnMainItemClickListener {
        fun onRefreshPerfumeClick()
        fun onBannerClick()
    }

    private class TodayPerfumeHeaderViewHolder(
        binding: ItemMainTodayPerfumeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val perfumeImageView: ImageView = binding.perfumeImage
        val todayPerfumeTextView: TextView = binding.todayPerfume
    }

    private class TodayPerfumeStoriesViewHolder(
        binding: ItemMainTodayPerfumeStoriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = itemView as RecyclerView
    }

    private class RefreshAnotherPerfumeViewHolder(
        binding: ItemRefreshAnotherPerfumeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val refreshView: TextView = binding.refreshView
    }

    private class BannerViewHolder(
        binding: ItemMainBannerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val bannerView: FrameLayout = itemView as FrameLayout
    }

    private class HotStoryHeaderViewHolder(
        binding: ItemHotStoriesHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private class HotStoriesViewHolder(
        binding: ItemMainHotStoriesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = itemView as RecyclerView
    }

    private class RankingHeaderViewHolder(
        binding: ItemPerfumeRankingHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private class PerfumeRankingsViewHolder(
        binding: ItemMainPerfumeRankingsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = itemView as RecyclerView
    }

    private class PerfumeRecommendHeaderViewHolder(
        binding: ItemPerfumeRecommendHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    private class PerfumeRecommendsViewHolder(
        binding: ItemMainPerfumeRecommendsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = itemView as RecyclerView
    }

    private class SeeMoreViewHolder(
        binding: ItemMainSeeMoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val seeMoreView: TextView = binding.seeMoreView
    }

    enum class ViewType {
        TODAY_PERFUME_HEADER,
        TODAY_PERFUME_STORIES,
        REFRESH_ANOTHER_PERFUME,
        BANNER,
        HOT_STORY_HEADER,
        HOT_STORIES,
        RANKING_HEADER,
        PERFUME_RANKINGS,
        PERFUME_RECOMMEND_HEADER,
        PERFUME_RECOMMENDS,
        SEE_MORE
    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        if (position !in 0 until itemCount) return RecyclerView.NO_ID
        return getItem(position)?.id?.hashCode()?.toLong() ?: RecyclerView.NO_ID
    }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MainAdapterItem.TodayPerfume -> ViewType.TODAY_PERFUME_HEADER
        is MainAdapterItem.TodayPerfumeStories -> ViewType.TODAY_PERFUME_STORIES
        is MainAdapterItem.RefreshAnotherPerfume -> ViewType.REFRESH_ANOTHER_PERFUME
        is MainAdapterItem.Banner -> ViewType.BANNER
        is MainAdapterItem.HotStoryHeader -> ViewType.HOT_STORY_HEADER
        is MainAdapterItem.HotStories -> ViewType.HOT_STORIES
        is MainAdapterItem.PerfumeRankingsHeader -> ViewType.RANKING_HEADER
        is MainAdapterItem.PerfumeRankings -> ViewType.PERFUME_RANKINGS
        is MainAdapterItem.PerfumeRecommendsHeader -> ViewType.PERFUME_RECOMMEND_HEADER
        is MainAdapterItem.PerfumeRecommends -> ViewType.PERFUME_RECOMMENDS
        else -> ViewType.SEE_MORE
    }.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.TODAY_PERFUME_HEADER.ordinal -> TodayPerfumeHeaderViewHolder(
                ItemMainTodayPerfumeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.TODAY_PERFUME_STORIES.ordinal -> TodayPerfumeStoriesViewHolder(
                ItemMainTodayPerfumeStoriesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.REFRESH_ANOTHER_PERFUME.ordinal -> RefreshAnotherPerfumeViewHolder(
                ItemRefreshAnotherPerfumeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.BANNER.ordinal -> BannerViewHolder(
                ItemMainBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.HOT_STORY_HEADER.ordinal -> HotStoryHeaderViewHolder(
                ItemHotStoriesHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.HOT_STORIES.ordinal -> HotStoriesViewHolder(
                ItemMainHotStoriesBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.RANKING_HEADER.ordinal -> RankingHeaderViewHolder(
                ItemPerfumeRankingHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.PERFUME_RANKINGS.ordinal -> PerfumeRankingsViewHolder(
                ItemMainPerfumeRankingsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.PERFUME_RECOMMEND_HEADER.ordinal -> PerfumeRecommendHeaderViewHolder(
                ItemPerfumeRecommendHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.PERFUME_RECOMMENDS.ordinal -> PerfumeRecommendsViewHolder(
                ItemMainPerfumeRecommendsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> SeeMoreViewHolder(
                ItemMainSeeMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position !in 0 until itemCount) return
        val item = getItem(position)

        when (holder) {
            is TodayPerfumeHeaderViewHolder -> holder.bind(item)
            is TodayPerfumeStoriesViewHolder -> holder.bind(item)
            is RefreshAnotherPerfumeViewHolder -> holder.bind(item)
            is BannerViewHolder -> holder.bind(item)
            is HotStoriesViewHolder -> holder.bind(item)
            is PerfumeRankingsViewHolder -> holder.bind(item)
            is PerfumeRecommendsViewHolder -> holder.bind(item)
            is SeeMoreViewHolder -> holder.bind(item)
        }
    }

    private fun TodayPerfumeHeaderViewHolder.bind(item: MainAdapterItem) {
        if (item !is MainAdapterItem.TodayPerfume || item.name.isNullOrBlank()) return

        glideRequests.load(item.perfumeImage)
            .placeholder(R.drawable.ic_empty_perfume)
            .error(R.drawable.ic_empty_perfume)
            .centerCrop()
            .into(perfumeImageView)

        val perfumeName = item.name
        val text = itemView.context.getString(
            R.string.main_today_perfume_header_content,
            item.name
        )
        val perfumeIndex = text.indexOf(perfumeName)
        SpannableStringBuilder(text).apply {
            setSpan(
                ForegroundColorSpan(itemView.resources.getColor(R.color.purple, null)),
                perfumeIndex,
                perfumeIndex + perfumeName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                StyleSpan(Typeface.BOLD),
                perfumeIndex,
                perfumeIndex + perfumeName.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }.let { todayPerfumeTextView.setText(it, TextView.BufferType.SPANNABLE) }
    }

    private fun TodayPerfumeStoriesViewHolder.bind(item: MainAdapterItem) {
        if (item !is MainAdapterItem.TodayPerfumeStories) return

        recyclerView.adapter = todayPerfumeStoryAdapter
    }

    private fun RefreshAnotherPerfumeViewHolder.bind(item: MainAdapterItem) {
        if (item !is MainAdapterItem.RefreshAnotherPerfume) return

        refreshView.setOnClickListener { mainItemClickListener.onRefreshPerfumeClick() }
    }

    private fun BannerViewHolder.bind(item: MainAdapterItem) {
        if (item !is MainAdapterItem.Banner) return

        bannerView.setOnClickListener { mainItemClickListener.onBannerClick() }
    }

    private fun HotStoriesViewHolder.bind(item: MainAdapterItem) {
        if (item !is MainAdapterItem.HotStories) return
        recyclerView.adapter = hotStoryAdapter
    }

    private fun PerfumeRankingsViewHolder.bind(item: MainAdapterItem) {
        if (item !is MainAdapterItem.PerfumeRankings) return

        recyclerView.adapter = rankingAdapter
    }

    private fun PerfumeRecommendsViewHolder.bind(item: MainAdapterItem) {
        if (item !is MainAdapterItem.PerfumeRecommends) return

        recyclerView.adapter = recommendAdapter
    }

    private fun SeeMoreViewHolder.bind(item: MainAdapterItem) {
        if (item !is MainAdapterItem.SeeMore) return

        seeMoreView.setOnClickListener { }
    }
}