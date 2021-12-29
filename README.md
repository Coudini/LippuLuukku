
# LippuLuukku

## Topic
 ###### basic functionality
- Mobile application using gps data and/or handwritten location input to show music events (possibly other events too?) in target city/area.
- For api-calls the application uses TicketMasters api-services.
 ###### other possible functionality
- Possibility to save venues and/or entertainers in a favorites-list.
- Search by artist -> show locations/dates
- Image/video data?
- Count down timer / ETA
- Weather conditions on given date? (given-date can't be too far in the future)

## Target
Android/Kotlin


## Draft of basic functionality diagram
<img width="827" alt="lippuluukku" src="https://user-images.githubusercontent.com/56744277/117538075-2662ba80-b00d-11eb-95d3-bf58f00656fe.png">

## Release 1: 2021-05-12 features
- Main window spinners(dropdown) with lists for Location and Keyword(search-word for api call)
- Preferences window which takes extras and returns edited lists (edit not implemented yet)
- Results window (not showing results yet)
- Changing animated gradient-wallpaper for all windows
- Util-class for returning urls based on Location and Keyword, test functions for api calls(commented out)
- get device-GPS -location on start-up

## Release 2: 2021-05-21 features
- MainActivity: User is able to pick Keywords and Locations from lists or input parameters by hand for search, Gps also possible
- PreferencesActivity: User is able to edit lists for Keywords and Locations (add / delete)
- PreferencesActivity: Creates permanent data on user device consisting of lists for Keywords and Locations, Save data, Load data
- ResultsActivity: Shows events from TicketMaster to user found according to search parameters
- ResultsActivity: OnClick shows user more info about the event in dropdown, redirects user to GoogleMaps event location or Ticketmaster event ticket sales
- ScreenCast: https://youtu.be/RaVMOUTEe8w 
- [![LippuLuukku](https://img.youtube.com/vi/RaVMOUTEe8w/0.jpg)](https://www.youtube.com/watch?v=RaVMOUTEe8w "LippuLuukku")

## Screenshots
<p float="left">
<img width="260" alt="ss1" src="https://user-images.githubusercontent.com/56744277/147662423-107333ef-c368-4fa3-8c2a-71eef77b2a97.jpg">
<img width="260" alt="ss" src="https://user-images.githubusercontent.com/56744277/147662438-811f97b8-e6de-44a8-a2fb-81229c8c0fed.jpg">
<img width="260" alt="ss" src="https://user-images.githubusercontent.com/56744277/147662453-dd8db61f-23dd-43df-b788-c112decb9d75.jpg">
<img width="260" alt="ss" src="https://user-images.githubusercontent.com/56744277/147662473-a348d145-d379-4613-9c62-91b4058d96ab.jpg">
<img width="260" alt="ss" src="https://user-images.githubusercontent.com/56744277/147662480-ea1457b1-612e-4c78-adcd-e735bcb4614c.jpg">
<img width="260" alt="ss" src="https://user-images.githubusercontent.com/56744277/147662492-597ce8f2-3fc9-4915-9d3e-cbb1204057be.jpg">
<img width="260" alt="ss" src="https://user-images.githubusercontent.com/56744277/147662498-f289792b-d0c9-4dca-a486-d43f4c084c5e.jpg">
 </p>

## Known bugs
- None
