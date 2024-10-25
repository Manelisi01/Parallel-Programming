BIN = bin
SRC = src

SEQUENTIAL = Grid AutomatonSimulation
PARALLEL = Grid AutomatonSimulationParallel RunParallel
ALL = Grid AutomatonSimulation AutomatonSimulationParallel RunParallel

Parallel_Run = RunParallel
Serial = AutomatonSimulation

${BIN}/%.class:${SRC}/%.java
	javac -d ${BIN} -cp ${BIN} $<


default: ${ALL:%=${BIN}/%.class}

parallel: ${PARALLEL:%=${BIN}/%.class}

sequential: ${SEQUENTIAL:%=${BIN}/%.class}

run: ${ALL:%=${BIN}/%.class}
	java -classpath bin $(Parallel_Run) $(ARGS)
	
run_serial: ${ALL:%=${BIN}/%.class}
	java -classpath bin $(Serial) $(ARGS)
