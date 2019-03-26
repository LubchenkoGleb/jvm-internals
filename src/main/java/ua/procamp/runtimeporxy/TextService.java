package ua.procamp.runtimeporxy;

public class TextService implements TextServiceInterface {

    public String staticText() {
        return "Some static 	text";
    }

    @Override
    public String variable(String variable) {
        return variable;
    }

    public String exception(String text) throws RuntimeException {
        //TODO throw your custom exception
        return text;
    }
}
