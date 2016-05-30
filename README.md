# Popular movies app

This is an **example** app that I am using as a playground app to test various libraries, architectures and methodologies.

The idea was taken from Udacity's [Developing Android Apps] course, where it is suggested as coursework. The aim of the coursework is to build a movie-discovery app, that is backed up by [The Movie Database]'s [API].

A short presentation of the app, with screenshots can be found in my [personal portfolio webpage].

The app features:

  - Custom layout for tablets
  - Usage of the view binding library ```Butterknife```
  - Asynchronous communication with the TMDB API with ```Retrofit2```, including custom ```OkHtttp``` interceptors
  - Image loading with ```Picasso```
  - Dependency injection with ```Dagger2```, including custom modules, and sophisticated component hierarchy to support testing
  - Model-View-Presenter architecture
  - Extensive testing with ```JUnit```, ```Espresso``` and ```Mockito```
  - Functional reactive programming, with ```rxandroid```


### Current status

I am currently performing some heavy refactoring to the application. The status of the refactoring is the following:

  - ~~MVP-ize the MovieDetails screen~~
  - ~~Espresso tests for MovieDetails screen~~
  - ~~MVP-ize the MovieDetails screen~~
  - ~~Espresso tests for MovieDetails screen~~
  - Revisit the tablet layout, and specifically the configuration changes from two-pane mode to single-pane mode
  - ~~Switch from GridLayout to RecyclerView for the posters grid~~
  - ~~Change the database code from pure SQL to some framework (cupboard) and get rid of ```ContentResolvers```~~
  - ~~Introduce rxAndroid~~



   [Developing Android Apps]: <https://www.udacity.com/course/developing-android-apps--ud853>
   [The Movie Database]: <https://www.themoviedb.org/>
   [API]: <https://www.themoviedb.org/documentation/api>
   [personal portfolio webpage]: <http://www.app-we-go.com/portfolio/popular-movies-app/>