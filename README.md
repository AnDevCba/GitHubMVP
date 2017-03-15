# GitHubMVP
Android app that allows the user to search for GitHub repos by a given username. We use Material Design for UI/UX. The architecture is based on Clean Architecture using Model-View-Presenter + Interactor + Repository patterns.

## Material Design
![Material design](/images/material_design.gif)

## Architecture
![App architecture](/images/architecture_android.png)

1. [Data layer:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/main/java/com/andevcba/githubmvp/data) All data needed for the application comes from this layer using the Repository pattern with a Factory pattern to create an `InMemoryRepository` or a `NetworkRepository` if the given username is cached or not, respectively. The idea behind this is the data source is transparent for the client.
2. [Domain layer:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/main/java/com/andevcba/githubmvp/domain/interactor) All the business logic happens in this layer. You will find all the `Interactor`s here.
3. [Presentation layer:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/main/java/com/andevcba/githubmvp/presentation) Here lives the Model-View-Presenter (MVP) pattern, where the `View` is an Activity or a Fragment, the `Model` is a UI model and the `Presenter` is the man-in-the-middle that handles user actions from the `View`, then consumes data through `Interactor`s and finally passes the data to be rendered to the `View`.

## Testing
1. [Unit Testing:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/test/java/com/andevcba/githubmvp) Unit Tests that run on the JVM, using JUnit and Mockito frameworks.
2. [UI Testing:](https://github.com/AnDevCba/GitHubMVP/tree/master/app/src/androidTest/java/com/andevcba/githubmvp/presentation) UI Tests that runs on an Emulator or a device, using Robotium and Espresso frameworks.