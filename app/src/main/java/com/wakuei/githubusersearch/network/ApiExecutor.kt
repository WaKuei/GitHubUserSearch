package com.wakuei.githubusersearch.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiExecutor {
    companion object {
        fun searchUsers(keyword: String?, page: Int, uiCallback: UiCallback.UsersCallback) {
            val call: Call<SearchUserResponse> =
                RetrofitInterface.getInStance().searchUsers(keyword, page, Config.itemDefaultCount)
            val cb: Callback<SearchUserResponse> = object : Callback<SearchUserResponse> {
                override fun onResponse(
                    call: Call<SearchUserResponse?>,
                    responseSearch: Response<SearchUserResponse?>
                ) {
                    when (responseSearch.code()) {
                        200 -> {
                            if (responseSearch.body() != null)
                                uiCallback.onSearchUserListSuccess(responseSearch.body()!!.items)
                        }
                        304 -> {
                            uiCallback.onError("304 Not Modified")
                        }
                        422 -> {
                            uiCallback.onError("422 Unprocessable Entity")
                        }
                        503 -> {
                            uiCallback.onError("503 Service Unavailable")
                        }
                        403 -> {
                            uiCallback.onError("403 API rate limit exceeded")
                        }
                        else -> {
                            uiCallback.onError("API Error")
                        }
                    }
                }

                override fun onFailure(call: Call<SearchUserResponse?>, t: Throwable) {
                    uiCallback.onError("Network Error")
                }
            }
            call.enqueue(cb)
        }
    }
}