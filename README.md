# Brunch

It is an example app using an open public APIs provided for an interview test.
I used multi-modules architecture, dividing View, Business and Sources.
It still has some improvements to add, like gradle dependencies handling. Will be good
to review some of the dependencies and change `implementation` to `api`.

##View
I used `DataBinding` to link the business/view objects with the UI. This way the UI reacts
when the data is provided/changed. Making use of findByView completely disappear,
and making the app more readable.
For the Details screen I used a ScrollView instead of a RecyclerView because I want to show
how I use includes and reuse xml components. I would also take more time to use a RecyclerView
and sync all the different types. So I chose to balance between performance and UI building/time.
I created a basic custom view to handle the rate. 
I didn't add interaction to the buttons or the toolbar actions.
I use `Navigation` as my main way to move between screens and passing data.

##Architecture
`ViewModel` is the perfect partner for `DataBinding`. That's why MVVM is 
applied + Repository pattern.
As dependency injection I used `Koin`, it is very well integrated with Kotlin and `ViewModel`.
For async calls and handling, `Coroutines` + `Flow` was the perfect fit. It lets me 
manipulate and transform the flow.
I just used a simple cache source to keep the whole object so I don't have to move it through
the whole app. If not I would need to implement a local DB, but for a demo app this works.

##Test
Made some basic tests, I didn't cover all the cases. I created
a Factory data provider so I know if the tests work no matter the values I use, again will
be good to add more asserts and test cases.

