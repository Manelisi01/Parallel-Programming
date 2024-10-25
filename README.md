# Abelian Sandpile Simulation - Parallel Programming Assignment (CSC2002S)
Introduction
This project implements a parallel version of an Abelian Sandpile simulation using Java. The simulation models a 2D cellular automaton grid where cells containing sand grains topple when their count exceeds a threshold. The task involves developing a parallel solution that improves performance over a serial version by utilizing Java's Fork/Join framework.

Project Structure
Serial Solution: A Java implementation of the Abelian Sandpile, provided as reference code.
Parallel Solution: Your task is to create a parallel version based on the serial code, with proper synchronization and speed improvements.
Input Files: CSV files containing grid configurations.
Output Files: PNG files visualizing the final stable state of the simulation.
Dependencies
Java 11+
Make (for Linux/MacOS builds)
Setup and Execution
1. Compilation
To compile the project, ensure you are in the project directory and run:

bash
Copy code
make
2. Running the Serial Solution
bash
Copy code
make run
This will run the serial version with the default input:

lua
Copy code
input/65_by_65_all_4.csv output/65_by_65_all_4.png
3. Running the Parallel Solution
Use the following command:

bash
Copy code
make run ARGS="input/<your_input_file>.csv output/<your_output_file>.png"
Replace <your_input_file> and <your_output_file> with the appropriate file paths.

4. Debugging (Optional)
If you need to output intermediate grids for debugging, set the DEBUG flag to true in the code.

5. Sample Output
The simulation prints the following details upon completion:

Number of rows and columns
Simulation steps required to reach a stable state
Total execution time
Code Structure
AutomatonSimulation.java: Main simulation logic.
Grid.java: Represents the grid structure and handles cell operations.
Validation
Ensure the parallel version produces identical results to the serial version. Compare the output PNG and console messages to verify correctness.

Benchmarking
To assess performance, run the parallel version on multiple machines (e.g., your laptop and a departmental server). Collect timing data for varying grid sizes and plot speedup graphs (not raw timings). Include the following:

Grid sizes: Test a range of sizes.
Machines: Ensure at least one test is done on a multi-core machine.
Report
Include the following sections in your report:

Methods: Describe the parallelization approach and how you ensured correctness.
Benchmarking: Detail how you measured performance and include speedup graphs.
Discussion: Analyze trends, anomalies, and the effectiveness of parallelization.
Conclusions: State whether the parallel implementation was worth the effort.
