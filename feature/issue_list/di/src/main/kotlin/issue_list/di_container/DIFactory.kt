package issue_list.di_container

import issue_list.data.repository.IssueListRepositoryImpl

object DIFactory {
    fun createIssueListRepository() = IssueListRepositoryImpl()
}