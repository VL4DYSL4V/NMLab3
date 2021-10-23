package state;

import framework.state.AbstractApplicationState;
import framework.state.StateHelper;
import framework.utils.ConsoleUtils;
import lombok.Getter;

@Getter
public class ApplicationState extends AbstractApplicationState {

    private final String system = "\n  /\u203E sin(x - 0.6) - y = 1.6\n"
                                +"<\n"
                                +"  \\_3x - cos(y) = 0.9";

    private double precision = 0.00001;

    @Override
    protected void initVariableNameToSettersMap() {
        variableNameToSetter.put("precision", StateHelper
                .getDefaultSetter("precision", Double.class, o -> (Double) o, this::setPrecision));
    }

    @Override
    protected void initVariableNameToGettersMap() {
        variableNameToGetter.put("precision", this::getPrecision);
        variableNameToGetter.put("system", this::getSystem);
    }

    public void setPrecision(double precision) {
        if (precision < 0) {
            ConsoleUtils.println(variableHolder.getVariable("precision").getConstraintViolationMessage());
        }
        this.precision = precision;
    }
}
