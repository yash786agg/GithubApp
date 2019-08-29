package com.app.model.project

import androidx.recyclerview.widget.DiffUtil

class ProjectDiffCallback : DiffUtil.ItemCallback<Project>() {
    override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean = oldItem.projectId == newItem.projectId

    override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean = oldItem == newItem
}