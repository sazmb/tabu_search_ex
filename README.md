# Tabu Search Scheduling Example

A simple Java application that demonstrates a Tabu Search heuristic for scheduling jobs and minimizing weighted tardiness cost. The project includes a graphical Swing interface that displays the solution path across multiple iterations and highlights the best schedule found.

## Project Overview

This example implements a basic Tabu Search algorithm for a static scheduling problem with a fixed set of jobs. The algorithm:

- Builds an initial solution by sorting jobs by due date
- Generates a neighborhood by moving one job to a new position
- Evaluates each candidate schedule using weighted tardiness cost
- Uses tabu lists to prevent cycling and encourage exploration
- Tracks solution data for each iteration
- Shows results in a Swing-based GUI table display

## Key Features

- Tabu Search metaheuristic for combinatorial optimization
- Weighted tardiness cost calculation
- Neighborhood exploration and move selection
- Iteration-by-iteration solution tracking
- Swing user interface for visualizing job schedules and costs

## Project Structure

- `tabu_search_3/src/pack/Main.java` — entry point and GUI setup
- `tabu_search_3/src/pack/TabuSearch.java` — Tabu Search algorithm implementation
- `tabu_search_3/src/pack/SolutionData.java` — tracks solutions, costs, and cumulative totals per iteration
- `tabu_search_3/src/pack/Job.java` — job model with processing time, due date, weight, and identifier

## Requirements

- Java Development Kit (JDK) 8 or newer

## Run the Project

From the repository root, compile and run the application with:

```powershell
cd tabu_search_3
javac src\pack\*.java
java -cp src pack.Main
```

The application opens a Swing window showing each iteration and the best solution found.

## Notes

- The current data set includes 6 predefined jobs.
- The implementation is intended as a teaching example for Tabu Search and scheduling.
- No build tool or dependency manager is required.

## License

This project is provided as-is for learning and experimentation.
