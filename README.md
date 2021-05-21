
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

## GooglePlay/Appstore Link
-

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

## Known bugs
- None
