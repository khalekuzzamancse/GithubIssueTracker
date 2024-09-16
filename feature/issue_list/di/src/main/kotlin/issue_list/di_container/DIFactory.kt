package issue_list.di_container

import issue_list.data.factory.IssueListDataFactory
/**
 * Centralizes and manages the instantiation logic, ensuring a `single source of truth` for object creation.
 * - This factory serves as a `Mediator` between the `:data` and `:ui` modules, providing necessary implementations to the `:ui` module
 * - By managing object creation centrally, it prevents the `:ui` module from directly depending on the `:data` module, promoting loose coupling
 * - This approach ensures maintainability by streamlining instance creation and keeping dependencies well-organized.
 */
object DIFactory {
    fun createIssueListRepository() = IssueListDataFactory.createIssueListRepository()
}