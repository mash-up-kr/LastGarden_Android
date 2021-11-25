package com.mashup.lastgarden.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mashup.lastgarden.data.remote.StoryRemoteDataSource
import com.mashup.lastgarden.data.vo.Story
import retrofit2.HttpException
import java.io.IOException

class PerfumeStoryPagingSource(
    private val id: Int,
    private val remote: StoryRemoteDataSource
) : PagingSource<Int, Story>() {

    companion object {
        private const val TAG = "PerfumeStoryPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val cursor = params.key
            val response = remote.getPerfumeStoryList(id, cursor)
            val lastPerfume = response.lastOrNull()
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = lastPerfume?.storyId
            )
        } catch (e: IOException) {
            Log.e(TAG, "IOException occurred", e)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e(TAG, "HTTPException occurred", e)
            return LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load stories", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }

}