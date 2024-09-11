package issue_details.di_container

import issue_details.data.repository.IssueDetailsRepositoryImpl

@Suppress("Unused")
object DIFactory {
    fun createIssueDetailsRepository() = IssueDetailsRepositoryImpl()
}