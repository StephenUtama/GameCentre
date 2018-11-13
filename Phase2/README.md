
# Game Centre

A Game Centre that contains several simple games.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You will need to have the following software:

```
Android Studio
Android 8.1 API 27
```

### Installing

These instructions should help you get a development env running on Android Studio.

Clone from the Game Centre repository;

```
git clone https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0519
```

Enter the correct directory;

```
cd group_0519/Phase1/
```

Open the project named *GameCentre* with Android Studio.

Run *app* to launch the Game Centre application in an emulator. Game Centre should be able to run on most Android emulators running Android 8.1.

Install Build Tools if necessary, and sync with project. 

Wait patiently for Gradle to build. This may take a while.

You may need to clean project if errors occur. 

## Features
We have implemented all the required field, such as:
GameCentre itself
Sign up/Sign in
Save/Load
Scoreboard
AutoSave
Undo
Multiple complexities

We also implemented the bonuses including:
Unlimited undos
Change background of tiles
Extra feature: Change the theme of the application

## How to test
Most of the things we have implemented are straight forward to test.
In order to test Sign up/Sign in, please enter a username and password, and press the signup button if this is your first time launching the app. Press login to access the starting menu once you have done so with the sane username and password.

The multiple complexities can be tested by following the prompt from the app.
There is also an option of "Image" in complexity.
Paste https://developer.android.com/_static/0d76052693/images/android/touchicon-180.png?hl=ja to the URL prompt; this will enable you to use the android logo as the background to the sliding tile game. Other URLs of images from the internet will work as well (uploading local files is still under development).

During the game, you will be able to undo unlimited times and save the game.
This saved data can be loaded by selecting the save desired on the StartingActivityPage (the page after selecting complexity). The saves are named based on the date and time at which they were saved. The game is automatically saved after every move and saved under the name "autosave".

After the game is completed, please go to the score board and check if the local ranking is updated or not, then the global ranking. (Must click on either button to display).

Lastly, in order to change the theme of the app, please go to the settings menu from the main menu, and pick any theme you like from there. This changes the background of all the screens except for the actual game.

Thank you

## Warnings
In order to test these codes properly, please use the drawable file from our submission. The themes are kept there, hence we need this file instead of the general ones.

## Authors

* **Kelvin Yao Fan**
* **Yuwa Yokohama**
* **Kevin Alitirto Lie**
* **Kailong Huang**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Special thanks to Suguru Seo for briefly being in our group for about 24 hours.
