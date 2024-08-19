package com.openin.openinapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.openin.openinapp.R
import com.openin.openinapp.databinding.ItemUserBinding
import com.openin.openinapp.model.CardModel

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList: List<CardModel> = emptyList()

    fun setUsers(users: List<CardModel>) {
        this.userList = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemUserBinding = DataBindingUtil.inflate(inflater, R.layout.item_user, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: CardModel) {
            binding.user = user

            binding.imageView.setImageResource(user.imageResId)

            binding.executePendingBindings()
        }
    }
}