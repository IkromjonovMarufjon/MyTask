package uz.infinity.mytask.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.infinity.mytask.data.resources.local.database.entities.UserEntity
import uz.infinity.mytask.databinding.ItemUserBinding

class UserAdapter : ListAdapter<UserEntity, UserAdapter.UserViewHolder>(UserDiffUtil) {
    private var clickMoreButtonListener: ((UserEntity) -> Unit)? = null

    object UserDiffUtil : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.email == newItem.email && oldItem.password == newItem.password
        }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonMore.setOnClickListener {
                clickMoreButtonListener?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        fun bind() {
            getItem(absoluteAdapterPosition).apply {
                binding.textEmail.text = email
                binding.textPassword.text = password
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()
    }

    fun setClickMoreButtonListener(block: (UserEntity) -> Unit) {
        clickMoreButtonListener = block
    }
}