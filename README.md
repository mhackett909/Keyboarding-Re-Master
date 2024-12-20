# Keyboarding (Re)Master

## Introduction
The keyboard master provides its users with the ability to custom configure their input devices for specific applications within a unified configuration environment. The main unique feature for input devices is the keymap concept. Each application can utilize 8 unique keymaps in which the user can toggle through via user assigned shortcuts. This enables a user to maintain a more ergonomic hand position when one hand is required for other duties such as mouse or tablet. 

Additional documentation can be found [here](https://kbmaster.atlassian.net/wiki/display/KBM/Home)

## Compiling
Keyboarding (Re)Master uses Gradle 7.6.1 as the build tool.  Issue the following commands:

* *./gradlew build*: Builds The project
* *./gradlew run*: Runs the project


Alternatively, you may try the following (requires Gradle installation):

* *gradle build*: Builds The project
* *gradle run*: Runs the project


Additional developer documentation is [here](https://kbmaster.atlassian.net/wiki/display/KBM/Developer+Documentation)

## Support
* If your device cannot be read, try updating permissions: sudo chmod -R o+r /dev/input
  * You may also try running the application as sudo: sudo ./gradlew run
* Please see /docs/Keyboarding (Re)Master.doc for technical assistance (including information about setting up support for your own device). The original Keyboarding Master documentation is also available there. 
* Additionally, you may email me at mhackett909@gmail.com with any questions or comments.

## Technical
* *Runtime*: Java 17
* *IDE*: IntelliJ Community 2024.3
* *OS*: Ubuntu 22.04


