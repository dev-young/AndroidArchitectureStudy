package io.github.devy.architecture_study.presentation.features.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.devy.architecture_study.R
import io.github.devy.architecture_study.databinding.ListItemUserBinding
import io.github.devy.architecture_study.databinding.ListItemUserHeaderBinding
import io.github.devy.architecture_study.domain.model.User

private const val VIEW_TYPE_ITEM = 0
private const val VIEW_TYPE_HEADER = 1

class UserListAdapter(
    private val clickListener: ((pos: Int, model: User) -> Unit),
    private val likeClickListener: ((pos: Int, model: User) -> Unit),
) : ListAdapter<UserListAdapter.ItemData, RecyclerView.ViewHolder>(object :
    DiffUtil.ItemCallback<ItemData>() {
    override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ItemData,
        newItem: ItemData,
    ): Boolean {
        return oldItem.toString() == newItem.toString()
    }

}) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = ListItemUserHeaderBinding.inflate(layoutInflater, parent, false)
                HeaderItem(binding)
            }
            VIEW_TYPE_ITEM -> {
                val binding = ListItemUserBinding.inflate(layoutInflater, parent, false)
                val vh = UserListItem(binding)
                binding.root.setOnClickListener {
                    val item = getItem(vh.adapterPosition)
                    if (item is ItemData.UserData) {
                        clickListener.invoke(
                            vh.adapterPosition,
                            item.data
                        )
                    }
                }
                binding.ivLike.setOnClickListener {
                    val item = getItem(vh.adapterPosition)
                    if (item is ItemData.UserData) {
                        likeClickListener.invoke(
                            vh.adapterPosition,
                            item.data
                        )
                    }
                }
                vh
            }
            else -> throw ClassCastException("Unknown ViewType: $viewType")
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is ItemData.UserData -> VIEW_TYPE_ITEM
        is ItemData.Header -> VIEW_TYPE_HEADER
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserListItem -> holder.onBind(getItem(position) as ItemData.UserData)
            is HeaderItem -> holder.onBind(getItem(position) as ItemData.Header)
        }
    }

    fun updateList(_list: List<User>, keyword: String) {
        if (_list.isEmpty()) {
            submitList(emptyList())
            return
        }
        val list = arrayListOf<ItemData>()
        list.add(ItemData.Header(keyword))
        list.addAll(_list.map { ItemData.UserData(it) })
        submitList(list)
    }


    fun notifyItemChangedByUserId(userId: Int) {
        currentList.indexOfFirst { it is ItemData.UserData && it.data.id == userId }
            .let {
                if (it > -1) {
                    notifyItemChanged(it)
                }
            }
    }

    sealed class ItemData(val id: Int) {
        data class UserData(val data: User) : ItemData(data.id)
        data class Header(val name: String) : ItemData(-1)
    }

    class UserListItem(
        val binding: ListItemUserBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(userData: ItemData.UserData) {
            userData.data.apply {
                binding.tvName.text = name
                binding.tvDescription.text = description
                binding.ivLike.imageTintList =
                    ContextCompat.getColorStateList(itemView.context,
                        if (like) R.color.like else R.color.un_like)
                Glide.with(binding.ivThumb)
                    .load(photo)
                    .circleCrop()
                    .into(binding.ivThumb)
            }
        }

    }

    class HeaderItem(
        val binding: ListItemUserHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(header: ItemData.Header) {
            binding.tvHead.text =
                String.format(itemView.context.getString(R.string.header_text), header.name)
        }
    }

}