import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RunParallel {

    // Variables to store the start and end time for measuring execution time
    static long startTime = 0;
    static long endTime = 0;

    // Method to read a 2D array from a CSV file
    public static int[][] readArrayFromCSV(String filePath) {
        int[][] array = null;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                // Read the dimensions of the grid (first line of the file)
                String[] dimensions = line.split(",");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                System.out.printf("Rows: %d, Columns: %d\n", width, height); // Do NOT CHANGE - required output

                // Initialize the grid with the specified dimensions
                array = new int[height][width];
                int rowIndex = 0;

                // Read the grid values row by row
                while ((line = br.readLine()) != null && rowIndex < height) {
                    String[] values = line.split(",");
                    for (int colIndex = 0; colIndex < width; colIndex++) {
                        array[rowIndex][colIndex] = Integer.parseInt(values[colIndex]);
                    }
                    rowIndex++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace(); // Handle file I/O errors
        }
        return array; // Return the populated grid
    }

    // Method to start the timer
    static void tick() {
        startTime = System.currentTimeMillis();
    }

    // Method to stop the timer
    static void tock() {
        endTime = System.currentTimeMillis();
    }

    public static void main(String[] args) throws IOException {

        Grid simulationGrid; // The cellular automaton grid
        int[][] grid;
        int[][] updateGrid;

        // Check if the correct number of command-line arguments is provided
        if (args.length != 2) {
            System.out.println("Incorrect number of command line arguments provided.");
            System.exit(0); // Exit if arguments are incorrect
        }

        // Read input and output file names from command-line arguments
        String inputFileName = args[0];
        String outputFileName = args[1];

        // Read the grid data from the input CSV file
        simulationGrid = new Grid(readArrayFromCSV(inputFileName));

        // Initialize the grid and updateGrid from the simulationGrid object
        grid = simulationGrid.getGrid();
        updateGrid = simulationGrid.getUpdateGrid();

        int counter = 0; // Time step counter

        // Create the initial task for the automaton simulation
        AutomatonSimulationParallel FinalAnswer = new AutomatonSimulationParallel(grid, updateGrid, 1, grid.length - 1);
        
        // Create a common ForkJoinPool to manage parallel tasks
        ForkJoinPool Pool = ForkJoinPool.commonPool();

        tick(); // Start the timer

        // Execute the simulation until no more changes occur in the grid
        while (Pool.invoke(FinalAnswer)) {

            // Apply the changes for the next time step
            FinalAnswer.nextTimeStep();

            counter++; // Increment the time step counter

            // Create a new task for the next iteration
            FinalAnswer = new AutomatonSimulationParallel(grid, updateGrid, 1, grid.length - 1);
        }

        tock(); // Stop the timer

        // Output the results and write the final grid to an image file
        System.out.println("Simulation complete, writing image...");
        simulationGrid.gridToImage(outputFileName); // Write grid as an image - required

        // Required output: Simulation details
        System.out.printf("Number of steps to stable state: %d \n", counter);
        System.out.printf("Time: %d ms\n", endTime - startTime); // Total computation time
    }
}

