package com.wakuei.githubusersearch.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wakuei.githubusersearch.R
import com.wakuei.githubusersearch.databinding.ActivityMainBinding
import com.wakuei.githubusersearch.repository.UserRepository
import com.wakuei.githubusersearch.viewmodel.UserViewModel
import com.wakuei.githubusersearch.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: UserViewModel
    private var mAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    private fun init() {
        mBinding.btnSearch.setOnClickListener(this)

        mAdapter = UserAdapter(this)
        mBinding.rvList.adapter = mAdapter
        mBinding.rvList.layoutManager = LinearLayoutManager(this)
        mBinding.rvList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val userRepo = UserRepository()
        val viewModelFactory = ViewModelFactory {
            UserViewModel(userRepo)
        }

        mViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)


        mViewModel.mList.observe(this, {
            mAdapter?.submitList(it)
        })

        mViewModel.mIsLoading.observe(this, {
            if (it) mBinding.pbLoading.visibility = View.VISIBLE
            else mBinding.pbLoading.visibility = View.GONE
        })

        mViewModel.mErrorMessage.observe(this, {
            if (!TextUtils.isEmpty(it)) Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        mBinding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = mBinding.rvList.layoutManager as LinearLayoutManager
                if (mBinding.pbLoading.visibility == View.GONE) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == mViewModel.mDataList.size - 1) {
                        val keyWord = mBinding.etSearch.text.toString()
                        if (TextUtils.isEmpty(keyWord)) mViewModel.searchUsers(null)
                        else mViewModel.searchMoreData(keyWord)
                    }
                }
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSearch -> {
                val keyWord = mBinding.etSearch.text.toString()
                if (TextUtils.isEmpty(keyWord)) mViewModel.searchUsers(null)
                else mViewModel.searchUsers(keyWord)

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(mBinding.btnSearch.windowToken, 0)
            }
        }
    }
}