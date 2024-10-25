import java.util.concurrent.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// A parallelized automaton simulation using ForkJoin framework
public class AutomatonSimulationParallel extends RecursiveTask<Boolean> {

    // Cutoff dimension to switch to a sequential execution
    private static int CUTTOFF_Dimension = 100; 

    // Flag to track if any change has occurred during the update
    private boolean change = false;

    // Indices for rows or columns to be accessed from start to end
    private int start;
    private int end;

    // Shared grid and update grid between tasks
    static int[][] grid;
    static int[][] updateGrid;

    // Constructor to initialize the task with grid, updateGrid, and range of rows/columns
    public AutomatonSimulationParallel(int[][] grid, int[][] updateGrid, int start, int end) {
        this.start = start;
        this.end = end;
        this.grid = grid;
        this.updateGrid = updateGrid;
    }

    // Method to update a portion of the grid in parallel
    private boolean update(int start, int end) {
        change = false; // Reset the change flag

        // Iterate through the specified range, skipping the border
        for (int i = start; i < end; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                // Calculate the new value for each cell based on neighboring cells
                updateGrid[i][j] = (grid[i][j] % 4) +
                                   (grid[i - 1][j] / 4) +
                                   (grid[i + 1][j] / 4) +
                                   (grid[i][j - 1] / 4) +
                                   (grid[i][j + 1] / 4);

                // Check if the value has changed
                if (grid[i][j] != updateGrid[i][j]) {
                    change = true;
                }
            }
        } 

        return change; // Return whether any change has occurred
    }

    // Method to apply the changes from updateGrid to grid for the next time step
    void nextTimeStep() {
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[0].length - 1; j++) {
                grid[i][j] = updateGrid[i][j];
            }
        }
    }

    @Override
    protected Boolean compute() {

        // If the task is small enough, execute sequentially
        if ((end - start) < CUTTOFF_Dimension) {
            return update(start, end);
        } else {
            // Split the task into two smaller tasks
            int Middle = (end + start) / 2;

            AutomatonSimulationParallel firstTask = new AutomatonSimulationParallel(grid, updateGrid, start, Middle);
            AutomatonSimulationParallel secondTask = new AutomatonSimulationParallel(grid, updateGrid, Middle, end);

            // Fork the first task to run in parallel
            firstTask.fork();

            // Execute the second task sequentially
            boolean second = secondTask.compute();

            // Wait for the first task to complete and get its result
            boolean first = firstTask.join();

            // Combine the results of both tasks
            return first || second;
        }
    }
}
