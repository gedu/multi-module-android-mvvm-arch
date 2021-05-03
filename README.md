# Brunch

It is an example app using some open public APIs provided for an interview test.
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

##Provided API
Was kind of hard to map the fields from the JSON to the given design. I tried to add as much
I could. Also, I came up with a mapping to show the menu items, it isn't the best way to send
items from a menu with its price. 
(I used a JSON-Class generator so if you see some weird Long/Double is because of that, I changed
some to Float).
I used 3 different ids, 2 of which have different data and null fields and one throws an error. My
approach was to load the 2 working ids without the failing id breaking the list fetching.

##Test
Made some basic tests, I didn't cover all the cases because of time constraints. I created
a Factory data provider so I know it the tests work no matter the values I use, again will
be good to add more asserts and test cases. I think the ones I did are the basic ones and show
how I would build them, making sure the logic works.
I just went the unit test path, because adding Integration testing with Robolectric or
end-to-end testing would have taken more time than the expected.

##Feelings
I like the final state of the code and the app. Made some really small changes on just the components I
used (like images with white background) so it doesn't looks ugly.
Gave a final look at the code and I think looks clean, and I get what is happening with just
reading it.
I was able to refresh on some forgotten components and improve on others that I already use.
I will be more than happy to receive feedback on ways I can improve my code,
architecture or how would you approach a particular feature, section, etc. 