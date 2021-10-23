package state;

import framework.state.AbstractApplicationState;
import framework.state.StateHelper;
import framework.utils.ConsoleUtils;
import framework.utils.ValidationUtils;
import lombok.Getter;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

@Getter
public class ApplicationState extends AbstractApplicationState {

    private final String system = "\n  /\u203E  sin(x - 0.6) - y = 1.6\n"
                                +"<\n"
                                +"  \\_  3x - cos(y) = 0.9";

    private RealVector initialApproximation = new ArrayRealVector(new double[] {1.4, 0.5});

    private double precision = 0.00001;

    @Override
    protected void initVariableNameToSettersMap() {
        variableNameToSetter.put("precision", StateHelper
                .getDefaultSetter("precision", Double.class, this::setPrecision));
        variableNameToSetter.put("initial-approximation", StateHelper
                .getDefaultSetter("initial-approximation", RealVector.class, this::setInitialApproximation));
    }

    @Override
    protected void initVariableNameToGettersMap() {
        variableNameToGetter.put("precision", this::getPrecision);
        variableNameToGetter.put("initial-approximation", this::getInitialApproximation);
        variableNameToGetter.put("system", this::getSystem);
    }

    public void setInitialApproximation(RealVector initialApproximation) {
        ValidationUtils.requireNonNull(initialApproximation);
        this.initialApproximation = initialApproximation;
    }

    public void setPrecision(double precision) {
        if (precision < 0) {
            ConsoleUtils.println(variableHolder.getVariable("precision").getConstraintViolationMessage());
        }
        this.precision = precision;
    }
}
