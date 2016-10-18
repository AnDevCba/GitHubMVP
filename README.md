# GitHubMVP
Android app that allows the user to search for GitHub repos by a given user name. The architecture is based on Clean Architecture using Model-View-Presenter + Interactor + Repository patterns.

## Architecture PRs
1. [Data layer:](https://github.com/AnDevCba/GitHubMVP/pull/1/files) here you will see the API client to connect to GitHub service, classes that model GitHub service response, a cache mechanism and an implementation of the Repository pattern to retrieve data from the network if the username is not in the cache or from the memory if the username is already cached.
2. [Domain layer:](https://github.com/AnDevCba/GitHubMVP/pull/2/files) here lives the business logic represented by an Interactor that search repos by a given username.
3. [Presentation layer - Show Repos feature:](https://github.com/AnDevCba/GitHubMVP/pull/3/files) here you will find the view, presenter and UI model to show repos from the cache. 
4. [Presentation layer - Add Repos feature:](https://github.com/AnDevCba/GitHubMVP/pull/4/files) here you will find the view, presenter and UI model to search and to show repos by username.

## Refactoring PRs
1. [Caching strategy:](https://github.com/AnDevCba/GitHubMVP/pull/5/files) clean-up and refactor the caching strategy.
2. [Create Interactors:](https://github.com/AnDevCba/GitHubMVP/pull/8/files) clean-up and refactor for adding new Interactors. You will find the interactors to search repos by username, to save repos to the cache and to load repos from the cache.
3. [Adapter pattern for Repository and Interactor:](https://github.com/AnDevCba/GitHubMVP/pull/18/files) add Adapter pattern for Repository and Interactor components, so, the subclasses can override the desired methods.
4. [Refactor Unit Tests:](https://github.com/AnDevCba/GitHubMVP/pull/20/files) Simplified interactor unit tests and add common jargon.


## Testing PRs
1. [Unit Testing - Add dependencies:](https://github.com/AnDevCba/GitHubMVP/pull/7/files) add dependecies needed for Unit Testing on the JVM.
2. [Unit Testing - Repos cache:](https://github.com/AnDevCba/GitHubMVP/pull/10/files) add Unit Tests for ReposCache component.
3. [Unit Testing - In-memory repository:](https://github.com/AnDevCba/GitHubMVP/pull/11/files) add Unit Tests for InMemoryRepository component.
4. [Unit Testing - Network repository:](https://github.com/AnDevCba/GitHubMVP/pull/12/files) add Unit Tests for NetworkRepository component mocking service responses.
5. [Unit Testing - Save repos interactor:](https://github.com/AnDevCba/GitHubMVP/pull/13/files) add Unit Tests for SaveReposInteractor component.
6. [Unit Testing - Search repos by username interactor:](https://github.com/AnDevCba/GitHubMVP/pull/14/files) add Unit Tests for SearchReposByUsernameInteractor component mocking service response.
7. [Unit Testing - Add repos presenter:](https://github.com/AnDevCba/GitHubMVP/pull/15/files) add Unit Tests for AddReposPresenter component.
8. [Unit Testing - Repos presenter:](https://github.com/AnDevCba/GitHubMVP/pull/16/files) add Unit Tests for ReposPresenter component.
9. [Unit Testing - Repos by username UI model:](https://github.com/AnDevCba/GitHubMVP/pull/17/files) add Unit Tests for ReposByUsernameUI model.
10. [UI Testing using Espresso Test Recorder:](https://github.com/AnDevCba/GitHubMVP/pull/21/files) add UI Tests using Espresso Test Recorder.
