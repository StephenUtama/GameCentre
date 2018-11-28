
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
https://markus.teach.cs.toronto.edu/git/csc207-2018-09-reg/group_0601
```

Enter the correct directory;

```
cd group_0601/Phase1/
```

Open the project named *GameCentre* with Android Studio.

Run *app* to launch the Game Centre application in an emulator. Game Centre should be able to run on most Android emulators running Android 8.1.

Install Build Tools if necessary, and sync with project. 

Wait patiently for Gradle to build. This may take a while.

You may need to clean project if errors occur. 

## Features of Phase 1
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

## Features of Phase 2
Made sliding tiles always solvable
Pong Game
Cold War Game
Unit Tests

## Code Tour of Sliding Tiles
* **Class Board:** It keeps track of Tile objects in a 2D array. The constructor is given a 1D List of Tiles,
 and these are used to populate the 2D array. This class is also Observable, which means that other classes 
 can sign up to be alerted when the contents change. This happens, as you'll see, when swapTiles is called. 
 The call to notifyAll ends up letting the GameActivity know that the user interface needs to be updated.
 
* **Class SlidingTilesManager:** SlidingTilesManager manipulates the Board, figuring out whether a tap is legal, checking 
whether the puzzle has been solved, performing a move the user has made, undoing a previous move, and 
calculating the user's score using the number of moves and time taken.

* **Class SlidingTilesComplexity Activity:** This screen allows a user to choose between the provided 3x3, 4x4, 5x5 game 
modes for the number tiles puzzle game as well as allowing the user to change the number of steps they wish 
to undo when they are playing the game(Bonus). A user may also choose to play download an image and slide
images instead of sliding number tiles.

* **Class SlidingTilesInfo:** This class keeps track of the state of the board, the board complexity, the user's
score, as well as making sure the board is always solvable. 

* **Class GameActivity:** This is the main sliding tile puzzle. It uses a SlidingTilesInfo to keep track of the 
board, and also manages all the user interface components. In it is also an undo button that allows
the user to return to their previous position.

* **Class SlidingTilesFilerSaverModel:** This class is responsible for keeping a record of all users' game states by saving
to a file should a user decide to save their game. A user may choose to load a previous saved game at a later time.

* **Class SlidingTilesGameController:** This class contains all the logic involving processing a user's 
touch for the game.

* **Class SignUpActivity:** New users are required to make an account before playing. It uses 
SharedPreferences to store the user's username and password.

* **Class SignUpSignInActivity** This is the interface where users have to sign in into their accounts before 
getting access to the sliding tiles puzzle game. New users can click on register to create an account. Account
holders may choose to change their password as well.

* **Class PasswordChangeActivity:** A user may choose to change their password at any given time. It uses 
SharedPreferences delete the old password and store the new password.(Bonus)

* **Class Session:** A singleton model that stores a specific user's information such as their username, 
password and game scores. There can only be one user logged in at a time. A session ends when a user sign out.

* **Class UserManager:** A class that uses SharedPreferences to store users' usernames and passwords. It
can then use this information to check whether a user has provided the correct information when signing in,
whether a username has already been taken when a new user creates an account and if a user is eligible to 
change their password.

* **Class ScoreActivity:** This screen shows a user's game score after completing a game of sliding tiles. 
It also shows their best score as well as a ranking board that lists the top three people with the highest 
scores in the game.

* **Class ScoreBoardActivity:** This screen shows the ranking of the top 10 players of the game at all time with their
username, top score and ranking. Note that if two players have the same scores, the player who achieved that score
earlier is ranked higher.

* **Class ScoreBoardManager:** Extends the BoardManager class and uses HashMaps to keep record of users' 
best scores. Additional features include getting a list of high scores paired with the respective users who 
obtained the scores. 

* **Class DownloadingActivity:** This screen allows the user copy and paste an URL link to download an images as the tiles 
background when playing the sliding tiles game.(Bonus)  

* **Class StartingActivity:** This screen is where users can start a new game or load a previously saved game.
 
## Code Tour of GeneralClasses

* **Class GameSelectionActivity:** This screen allows the user to pick which game they wish to play.

* **Class MenuActivity:** This screen allows the user to logout, configure their settings, or proceed 
to choosing a game to play.

* **Class LoginActivity:** This is the interface where users have to sign in into their accounts before 
getting access to the menu selection screen. New users are required to sign up first.

* **Class ScoreBoardActivity:** This screen allows the user to display the global rankings which 
shows the ranking of the top players of the game at all time with their username, top score and ranking.
The user can also choose to display local rankings which shows their personal achievements.

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
* **Stephen Utama**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Special thanks to Suguru Seo for briefly being in our group for about 24 hours.
