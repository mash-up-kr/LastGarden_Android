package com.mashup.lastgarden.ui.scent

import androidx.annotation.IntDef
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView

class SnapPagerScrollListener() : RecyclerView.OnScrollListener() {
    private var snapHelper: PagerSnapHelper? = null
    private var type = 0
    private var notifyOnInit = false
    private var listener: OnChangeListener? = null
    private var snapPosition = 0

    companion object {
        @IntDef(ON_SCROLL, ON_SETTLED)
        annotation class Type

        const val ON_SCROLL = 0
        const val ON_SETTLED = 1
    }

    constructor(
        snapHelper: PagerSnapHelper?,
        @Type type: Int,
        notifyOnInit: Boolean,
        listener: OnChangeListener?
    ) : this() {
        this.snapHelper = snapHelper
        this.type = type
        this.notifyOnInit = notifyOnInit
        this.listener = listener
        snapPosition = RecyclerView.NO_POSITION
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (type == ON_SCROLL && newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    private fun getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
        val snapView = snapHelper!!.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        return layoutManager.getPosition(snapView)
    }

    private fun notifyListenerIfNeeded(newSnapPosition: Int) {
        if (snapPosition != newSnapPosition) {
            if (notifyOnInit && !hasItemPosition()) {
                listener!!.onSnapped(newSnapPosition)
            } else if (hasItemPosition()) {
                listener!!.onSnapped(newSnapPosition)
            }
            snapPosition = newSnapPosition
        }
    }

    private fun hasItemPosition(): Boolean {
        return snapPosition != RecyclerView.NO_POSITION
    }
}

interface OnChangeListener {
    fun onSnapped(position: Int)
}