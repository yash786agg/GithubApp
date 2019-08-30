package com.app.extensions

import com.app.model.project.Project

interface ProjectItem {

    fun onProjectItemClickListener(project: Project?)
}