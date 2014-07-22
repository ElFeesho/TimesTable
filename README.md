TimesTable
==========

Android Times Table App

TimesTableTest
==============
The test project is a Java project which can be opened with Eclipse which uses junit-4.11 and robolectric-2.4.2 to exercise the functionality of the TimesTable project.

All the *.placeholder files need to be replaced with a jar file represented by it, e.g. junit-4.11.jar.placeholder should be removed and replaced with junit-4.11.jar (without the .placeholder suffix)

The *.symlink files should be replaced with a library symlinked from another location on your dev machine. E.g. android-16.jar.symlink should be a symlink to $ANDROID_HOME/platforms/android-16/android.jar. 

This is obviously a bit tedious and I apologise. 

Running Tests
=============
To run tests, first use the 'android' program to generate a build.xml for the TimesTable project by issuing the following command:

(in TimesTable directory you cloned)
android update project -p .

Once a build.xml has been generated, it will now be possible to compile and execute the unit tests.

Use ant and the 'test' target. This will compile the android project and execute the tests and generate a coverage report using Jacoco.
