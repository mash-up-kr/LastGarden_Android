package com.mashup.lastgarden.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mashup.lastgarden.data.remote.PerfumeRemoteDataSource
import com.mashup.lastgarden.data.vo.Perfume
import okio.IOException
import retrofit2.HttpException

class PerfumeListPagingSource(
    private val perfumeName: String,
    private val remote: PerfumeRemoteDataSource
) : PagingSource<Int, Perfume>() {
    companion object {
        private const val TAG = "PerfumeListPagingSource"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Perfume> {
        return try {
            val nextCursor = params.key
            val response = remote.getPerfumesWithName(perfumeName, nextCursor)

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = response.lastOrNull()?.perfumeId?.toInt()
            )
        } catch (e: IOException) {
            Log.e(TAG, "IOException occured", e)
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e(TAG, "HTTPException occured", e)
            return LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load perfumes", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Perfume>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }
}