# GitHubMVP
Android app that allows the user to search for GitHub repos by a given user name. The architecture is based on Clean Architecture using Model-View-Presenter + Interactor + Repository patterns.

## Architecture 
![App architecture](/images/architecture_android.png)

1. [Data layer:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/main/java/com/andevcba/githubmvp/data) All data needed for the application comes from this layer using the Repository pattern with a Factory to create a `InMemoryRepository` or `NetworkRepository` if the given username is cached or not, respectively. The idea behind this is that the data source is transparent for the client.
2. [Domain layer:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/main/java/com/andevcba/githubmvp/domain/interactor) All the business logic happens in this layer. You will see all the `Interactor`s here.
3. [Presentation layer:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/main/java/com/andevcba/githubmvp/presentation) Here lives the Model-View-Presenter (MVP) pattern, where the View is an Activity and/or Fragment, the Model is a UI model and the Presenter is the man in-the-middle that handles user actions from a View, then consumes data through `Interactor`s and finally passes to the View the retrieved data to be rendered.

## Testing
1. [Unit Testing:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/test) Unit Tests that run on the JVM, using JUnit and Mockito frameworks.
2. [UI Testing:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/androidTest) UI Tests that runs on an Emulator or devices, using Robotium and Espresso frameworks.
