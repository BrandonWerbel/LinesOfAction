# Lines of Action - Java Application

Java application of Claude Soucie's Lines of Action game.

## Description

Welcome to the **Lines of Action** game, implemented in Java and built using **Gradle**. This repository contains the code for a two-player abstract strategy game, *Lines of Action*, where the objective is to connect all of your pieces into a single contiguous group.

## Table of Contents

- [About the Game](#about-the-game)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Dependencies](#dependencies)
  - [Installation](#installation)
  - [Executing program](#executing-program)
- [How to Play](#how-to-play)
- [Built With](#built-with)
- [Acknowledgments](#acknowledgments)
- [License](#license)

## About the Game

*Lines of Action* (LOA) is a classic two-player abstract strategy board game where the goal is to connect all of your pieces into one contiguous group while preventing your opponent from doing the same. The game is played on an 8x8 board, and each player starts with 12 pieces.


## Features

- Full implementation of the *Lines of Action* game in Java.
- Two-player mode (local).
- Rule enforcement and valid move checking.
- Graphical User Interface (GUI) for gameplay.

## Getting Started

### Dependencies

To run this application, you will need the following installed on your system:

- **Java JDK** (version 8 or higher)
- **Gradle** (version 7.0 or higher) is recommended, but not required

### Installation

Follow these steps to clone the repository and set up the project:

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/BrandonWerbel/LinesOfAction.git
   ```

2. Navigate into the project directory:
   ```bash
   cd LinesOfAction
   ```

3. Ensure that the required dependencies are downloaded by Gradle:
   ```bash
   ./gradlew build
   ```

### Executing program
To run the game, execute the following command from the project's home directory:

```bash
gradle run
```

This will launch the game in a graphical user interface where two players can take turns.

Alternatively, running `./gradlew build` will generate a JAR file at `./app/build/libs/lines-of-action.jar`. This JAR file can be exported anywhere, and run with the command `java -jar lines-of-action.jar` in the directory containing the file.

## How to Play

1. The game starts with each player's pieces placed on the edges of the board.
2. Players alternate turns, moving one piece at a time.
3. A piece moves in straight lines (horizontal, vertical, or diagonal), and the distance moved is equal to the number of pieces (both yours and your opponent’s) in the line of movement.
4. You cannot jump over your opponent's pieces, but you can capture them by landing on them.
5. You may not land on your own pieces, but you can jump over them.
6. The goal is to connect all of your pieces into a single contiguous group, and the first player to do so wins the game.

## Built With

- **Java**: The programming language used for the game logic.
- **Gradle**: Build automation tool used for compiling and managing dependencies.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new feature branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m "Add some feature"`).
4. Push the branch (`git push origin feature-branch`).
5. Open a pull request.

Please make sure the project builds successfully before submitting a pull request.

## Acknowledgments

- Inspired by the original *Lines of Action* board game.
- [Gradle Documentation](https://gradle.org/docs/) for build automation support.

## License

This project is licensed under the [NAME HERE] License - see the LICENSE.md file for details