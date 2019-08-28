package com.app.repository

import com.app.api.GithubApi
import javax.inject.Inject

class MainRepository @Inject constructor(private val githubApi : GithubApi) {

}