package com.example.internet

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.internet.databinding.ItemTodoBinding


class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
//    private lateinit var binding:ItemTodoBinding

    inner class TodoViewHolder( val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Todo>( ) {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)
    var todos: List<Todo>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount() = todos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

     override fun onBindViewHolder(holder: TodoViewHolder,position: Int){
        holder.apply{
            val todo=todos[position]
            binding.textView.text=todo.title
            binding.checkbox.isChecked=todo.completed

        }
    }

}
