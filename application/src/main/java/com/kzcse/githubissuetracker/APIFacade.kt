package com.kzcse.githubissuetracker

import android.util.Log
import com.kzcse.githubissuetracker.issue_details.IssueDetailsEntity
import core.network.GetRequests

class APIFacade {
    suspend fun requestDetails(
        issueNo: String,
    ): Result<IssueDetailsEntity> {
        return GetRequests()
            .request<IssueDetailsEntity>(
                url = "https://api.github.com/repos/flutter/flutter/issues/154701"
            )

    }

    //    suspend fun requestIssueList() {
//        val res = GetRequests()
//            .request<List<IssueDetailsEntity>>(
//                url = " https://api.github.com/repos/flutter/flutter/issues"
//            )
//        if (res.isSuccess){
//            Log.d("IssuedRes:", "${res.getOrThrow().map { it.title }}")
//        }
//
//
//    }
    suspend fun requestIssueList() = GetRequests()
        .request<List<IssueDetailsEntity>>(
            url = " https://api.github.com/repos/flutter/flutter/issues"
        )


}
