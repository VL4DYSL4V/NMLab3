package command;

import framework.command.AbstractRunnableCommand;
import framework.utils.ConsoleUtils;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public class RunCommand extends AbstractRunnableCommand {

    public RunCommand() {
        super("run");
    }

    @Override
    public void execute(String[] strings) {
        RealVector initialApproximation = (RealVector) applicationState.getVariable("initial-approximation");
        double norm = getFirstNormOfJacobian(initialApproximation.getEntry(0), initialApproximation.getEntry(1));
        if (norm < 1) {
            double precision = (Double) applicationState.getVariable("precision");
            RealVector solution = solve(initialApproximation, precision);
            ConsoleUtils.printVector(solution);
        } else {
            ConsoleUtils.println("Norm of Jacobian must be < 1. Bad initial approach:");
            ConsoleUtils.printVector(initialApproximation);
        }
    }

    private double getFirstNormOfJacobian(double x1, double x2) {
        double firstColumnSum = Math.abs(Math.cos(x1 - 0.6));
        double secondColumnSum = Math.abs((-1.0 / 3.0) * Math.sin(x2));
        return Math.max(firstColumnSum, secondColumnSum);
    }

    private RealVector solve(RealVector initialApproximation, double precision) {
        RealVector current = initialApproximation.copy();
        RealVector previous;
        int iterationCount = 0;
        do {
            RealVector newSolution = computeNewSolution(current);
            previous = current;
            current = newSolution;
            iterationCount++;
        } while (current.subtract(previous).getNorm() >= precision);
        ConsoleUtils.println(String.format("Iteration count: %d", iterationCount));
        return current;
    }

    private RealVector computeNewSolution(RealVector currentSolution) {
        double x0 = 0.3 + (1.0 / 3.0) * Math.cos(currentSolution.getEntry(1));
        double x1 = Math.sin(currentSolution.getEntry(0) - 0.6) - 1.6;
        return new ArrayRealVector(new double[]{x0, x1});
    }
}
