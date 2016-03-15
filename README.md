[![Build Status](https://travis-ci.org/wlk/workout-tracker-akka-http.svg?branch=master)](https://travis-ci.org/wlk/workout-tracker-akka-http)


This is the server-side part of the simple web project based on akka-http.

The intention here is to provide a reasonably sized project written in akka-http for everyone to take a look at.

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)


## Objective

Create an application that tracks workouts (jogging) for logged in users:
* Users have to create accounts and login.
* Logged in users can manage their workouts - creating, editing, deleting.
* Each workout log has a date, time and distance
* Each entry has an average speed
* Users should be able to filter events and weekly reports by date range
* Users should see a report that sums weekly distance, time and shows average speed in that week.
* Prepare REST API for workout management.
* All user actions are done via AJAX.

## Technologies used:
- akka-http
- Scala
- nscala-time (Scala wrapper for Joda Time)

## Parts of akka-http that are covered

* routing
* handling GET, POST, DELETE requests
* exception handling
* testing with `ScalatestRouteTest`
* JSON formatting
* Basic Authentication


## TODO

Following things are left TODO (or some of them will not be done because it's just a demo project')
* better error handling (returning correct HTTP codes + helpful messages)
* stop throwing exceptions (although there are not many places where this happens)
* {User,Workout}Services control logic in which new Ids are created (probably should be moved to Daos)
* date marshalling (don't transfer it as timestamp)
* switch from Basic Authentication to custom header (for example `X-Api-Token` or similar)