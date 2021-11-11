package com.mashup.lastgarden.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mashup.lastgarden.data.remote.PerfumeDetailRemoteDataSource
import com.mashup.lastgarden.data.vo.Story
import retrofit2.HttpException
import java.io.IOException

class PerfumeDetailPagingSource(
    private val id: Int,
    private val remote: PerfumeDetailRemoteDataSource
) : PagingSource<Int, Story>() {
    companion object {
        private const val TAG = "PerfumeDetailPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val cursor = params.key
            val response = remote.getStoryByPerfume(id, cursor)
            val lastPerfume = response.lastOrNull()
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = lastPerfume?.likeCount?.toInt()
            )
        } catch (e: IOException) {
            Log.e(TAG, "IOException occured", e)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e(TAG, "HTTPException occured", e)
            return LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e(this::class.java.simpleName, "Failed to load stories", e)
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