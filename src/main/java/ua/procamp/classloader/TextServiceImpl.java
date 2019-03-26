package ua.procamp.classloader;

public class TextServiceImpl implements TextService {

    @Override
    public String staticText() {
        return "DDD";
    }

    public String variable(String variable) {
        return variable;
    }

    public String exception(String text) throws RuntimeException {
        //TODO throw your custom exception
        return text;
    }
}
