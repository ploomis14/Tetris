# Tetris
an AI for Tetris

## Usage
### Running the AI
- make sure AI_MODE is set to true in TetrisDriver.java
- run GeneticProblem.java to generate a weights file, which contains a list of feature weights that the AI uses for scoring moves
- run TetrisDriver.java

### Running manual mode
- make sure AI_MODE is set to false in TetrisDriver.java
- run TetrisDriver.java
- control piece movements with the arrow keys (up arrow = rotate, right arrow = right move, left arrow = left move)

## Settings
- GeneticProblem.java contains settings for the population size (number of chromosomes), mutation rate, and number of
iterations. Changing these settings will affect how the weight vector is computed.
- You can set the game mode (AI or manual mode) and game speed in TetrisDriver.java
- You can change the grid width and height in Grid.java