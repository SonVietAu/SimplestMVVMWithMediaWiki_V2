# Simplest MVVM

This is the simplest implementation of MVVM for Android. The project was created as a part to the goal of mastering MVVM for Android. <BR>This project is a migrated version from Android legacy architect of 'com.android.support:appcompat-v7' to 'androidx.appcompat:appcompat' as the older version no longer compile on Android Studio 4.

The basic component structures of an MVVM implementation are called Models, ViewModels and Views. Following the Android [Guide to App Architecture](https://developer.android.com/topic/libraries/architecture/guide.html), the class MediaWikiRepository was created as the model component, SimplestMVVMViewModel as the view model and the MainActivity as the view component. It is intended that only these three classes were created to demonstrate MVVM in its simplest form. <BR>

Special thanks to [RayWenderlich.com](https://www.raywenderlich.com), Joe Howard and Dario Coletto for the resources to MediaWiki. The resources to MediaWiki was extracted from the Android tutorial [Dependency Injection in Android with Dagger 2 and Kotlin](https://www.raywenderlich.com/171327/dependency-injection-android-dagger-2). (Yes, this project does not have DI, but the exclusion ensured the **simplest** implementation of MVVM)<BR>

Also special thanks to MediaWiki for making their API available. The document to MediaWiki can be found [here](https://www.mediawiki.org/wiki/API:Main_page)
