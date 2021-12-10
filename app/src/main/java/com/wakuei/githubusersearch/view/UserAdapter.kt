package com.wakuei.githubusersearch.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wakuei.githubusersearch.databinding.ItemUserBinding
import com.wakuei.githubusersearch.model.UserModel

class UserAdapter(private val mContext: Context) :
    ListAdapter<UserModel, RecyclerView.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(mContext)), mContext)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    /**
     * ListAdapter not updating item in RecyclerView
     * https://stackoverflow.com/questions/49726385/listadapter-not-updating-item-in-recyclerview?rq=1
     */
    override fun submitList(list: List<UserModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    class ViewHolder(
        itemView: ItemUserBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(itemView.root) {
        private val mBinding = itemView
        fun bind(data: UserModel) {
            mBinding.txtName.text = data.login
            Glide.with(context)
                .load(data.avatar_url)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(30)))
                .into(mBinding.imgPhoto)
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }
}