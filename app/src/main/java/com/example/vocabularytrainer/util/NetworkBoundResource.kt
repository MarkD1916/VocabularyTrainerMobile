package com.example.vocabularytrainer.util

import com.example.vocabularytrainer.presentation.home.Resource
import kotlinx.coroutines.flow.*


/**
 * Caching mechanism
 */
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>, // logic for query to db
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow<Resource<ResultType>> {
    emit(Resource.Loading(null))
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
        try {
            val fetchedResult = fetch() // get data from server
            saveFetchResult(fetchedResult) // take fetch result and insert into db
            query().map {
                Resource.Success(it) // make query to db and map to Success obj
            }
        } catch (t: Throwable) {
            onFetchFailed(t)
            query().map {
                Resource.Error("Couldn't reach server. It might be down", it)
            }
        }
    } else {
        query().map { Resource.Success(it) }
    }
    emitAll(flow)
}