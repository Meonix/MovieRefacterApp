package com.mionix.myapplication.base


inline fun <T:Any> Response<T>.onLoading(action: (Status) -> Unit): Response<T> {
    if (this is Response) action(status)
    return this
}

inline fun <T> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
    if (this is Response) data?.let { action(it) }
    return this
}

//
inline fun <T : Any> Response<T>.onFailure(action: (ResponseError) -> Unit){
    if (this is Response) error?.let { action(it) }
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}

data class Response<out T>(val status: Status, val data: T?, val error: ResponseError?) {
    companion object {
        fun <T> loading(data: T? = null): Response<T> {
            return Response(Status.LOADING, data, null)
        }
        fun <T> success(data: T?): Response<T> {
            return Response(Status.SUCCESS, data, null)
        }
        fun <T> error(error: ResponseError?): Response<T> {
            return Response(Status.ERROR, null, error)
        }
    }
}

interface BaseResponse<Type> {
    fun loading()
    fun onSuccess(result: Type)
    fun onError(error: ResponseError?)
}