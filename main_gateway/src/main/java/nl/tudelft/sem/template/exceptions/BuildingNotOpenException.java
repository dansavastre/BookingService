package nl.tudelft.sem.template.exceptions;

public class BuildingNotOpenException extends Exception {

    private static final long serialVersionUID = 1129521006696985258L;

    public BuildingNotOpenException(String errorMessage) {
        super(errorMessage);
    }

}
