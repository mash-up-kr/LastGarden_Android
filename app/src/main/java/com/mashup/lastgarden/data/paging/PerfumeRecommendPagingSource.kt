package com.mashup.lastgarden.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mashup.lastgarden.data.remote.PerfumeRemoteDataSource
import com.mashup.lastgarden.data.vo.Perfume
import okio.IOException
import retrofit2.HttpException

class PerfumeRecommendPagingSource(
    private val remote: PerfumeRemoteDataSource
) : PagingSource<Pair<Int?, Long?>, Perfume>() {
    companion object {
        private const val TAG = "PerfumeRecommendPagingSource"
    }

    override suspend fun load(params: LoadParams<Pair<Int?, Long?>>): LoadResult<Pair<Int?, Long?>, Perfume> {
        return try {
            val nextIdCursor = params.key?.first
            val nextLikeCursor =
                if (nextIdCursor !== null) params.key?.second?.toInt() ?: 0 else null
            val response = remote.getSteadyPerfumes(nextIdCursor, nextLikeCursor)
            val lastPerfume = response.lastOrNull()
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = lastPerfume?.perfumeId to lastPerfume?.likeCount
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

    override fun getRefreshKey(state: PagingState<Pair<Int?, Long?>, Perfume>): Pair<Int?, Long?>? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }
}