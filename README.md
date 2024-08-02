# Fill-The-Squares

This is a classic time-passing game played by children in Bangladesh. In this turn-based game, players try to make as many squares as they can by picking the lines that represents square borders. In each move, a player picks a line. If all four lines of a square is picked, that square becomes filled and the player gets a point. Whoever makes the most squares becomes the winner.

## Features

This game uses Java Swing to design the UI of the game and Spring Boot to desing the server side of the game. For database, it uses MySQL. It contains the following feature:

1. Six different sizes of boards are designed
2. Two-player mode is the default mode
3. Three different bots are programmed
4. Bot-1 is made using simple AI logic
5. Bot-2 implements minmax algorithm with alpha-beta pruning
6. Bot-3 applies extra optimization on bot-2, making this bot the hardest one to beat
7. For online multiplayer mode, the server and the APIs are designed

## Rules

Rules are simple. They are:

1. Each player gets a turn
2. In each turn, he picks a line
3. If all four lines of a square is picked, current player gets a point
4. If the current player gets a square in current move, he gets a free turn
5. Two squares can also be acquired in one move
6. If a player gets more squares than the half of the total squares, he wins

## Screenshots

![Annotation 2024-08-02 123026](https://github.com/user-attachments/assets/4e12f0c2-329c-4208-9854-0ebf7d7a7410)

![Annotation 2024-08-02 123126](https://github.com/user-attachments/assets/5e780176-5f63-40a6-8e66-c4c9abf4a7c4)

![Annotation 2024-08-02 123233](https://github.com/user-attachments/assets/eab4c26d-a5a1-4db5-a512-3f6a020c0bf9)




