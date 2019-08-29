package com.app.ui.project.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.extensions.ProjectItem
import com.app.model.project.ProjectDiffCallback
import com.app.model.project.Project
import com.app.nandroid.R
import com.app.nandroid.databinding.AdapterProjectListBinding

class ProjectListAdapter : PagedListAdapter<Project, ProjectListAdapter.MyViewHolder>(ProjectDiffCallback())
{
    private var onProjectItemClickListener : ProjectItem? = null

    // OVERRIDE ---
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding : AdapterProjectListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
            , R.layout.adapter_project_list, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it,onProjectItemClickListener) }
    }

    fun setonProjectItemClickListener(onProjectItemClickListener : ProjectItem) {
        this.onProjectItemClickListener = onProjectItemClickListener
    }

    override fun getItemViewType(position: Int): Int = position

    inner class MyViewHolder(private val binding : AdapterProjectListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(project: Project, clickListener: ProjectItem?) {
            binding.dataManager = project
            itemView.setOnClickListener { clickListener?.onProjectItemClickListener(project) }
        }
    }
}


