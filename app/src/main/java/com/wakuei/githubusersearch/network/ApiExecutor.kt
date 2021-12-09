package com.wakuei.githubusersearch.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiExecutor {
    companion object {
        fun searchUsers(keyword: String, page:Int, uiCallback: UiCallback.UsersCallback) {
            val call: Call<UserResponse> = RetrofitInterface.getInStance()
                .searchUsers(keyword, page, Config.itemDefaultCount)
            val cb: Callback<UserResponse> = object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                    when(response.code()){
                        200->{
                            if(response.body()!=null)
                                uiCallback.onSearchUserListSuccess(response.body()!!.items)
                        }
                        304->{
                            uiCallback.onError("304 Not Modified")
                        }
                        422->{
                            uiCallback.onError("422 Unprocessable Entity")
                        }
                        503->{
                            uiCallback.onError("503 Service Unavailable")
                        }
                        else->{
                            uiCallback.onError("Api Error")
                        }
                    }
                }

                override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                    uiCallback.onError("Network Error")
                }
            }
            call.enqueue(cb)
        }
    }
}