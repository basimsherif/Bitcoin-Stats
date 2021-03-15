
### Bitcoin Stats using Blockchain Charts API:

### Project Architecture:

* I have used Kotlin as the development language and MVVM as an architecture so that there won't be any tight coupling between components and we can unit test the code easily
* Blockchain Chart API is used as backend
* Hilt is used for Dependency Injection to inject all the required modules
* The app is designed as a single Activity architecture
* A Repository class which act as a bridge between View model and API
* RemoteDataSource class to communicate with API and provide results to Repository class
* A Resource class is used to wrap API response and to handle the response with LiveData
* Data Binding is used to bind data to Recyclerview
* Memory leaks are handled properly
* KTX library is used for avoiding lots of boiler plate codes

### UI Design

* All UI elements are custom designed
* Added Light and Dark theme support
* A dedicated layout for landscape
* Animated loading using Lottie library

### Code Quality

* SOLID and clean code principles are followed
* Lint check is performed and cleared the issues
* Proper Unit tests and Instrumentation tests are added and gradle task for generating unified jacoco code coverage also added

### Testing Architecture:

* Unit test cases are developed using JUnit
* Viewmodel and Livedata are unit tested using Mockito
* Instrumentation test cases are added for testing Room Database
* UI Automation test cases are added using Espresso and followed Page Object Model
* Counting Idling resource is used to wait for asyn operation when running Espresso tests


### More Testing Strategy:

* Need to mock the API and add more test cases to test the data displayed in Chart