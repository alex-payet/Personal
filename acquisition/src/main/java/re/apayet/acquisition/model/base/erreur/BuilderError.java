package re.apayet.acquisition.model.base.erreur;

public class BuilderError {

    public static BuilderError ABSTRACT = builderMessageError(TextualException.ABSTRACT);
    public static BuilderError FRONT = builderMessageError(TextualException.FRONT);
    public static BuilderError BACK = builderMessageError(TextualException.BACK);

    public BuilderError PARAM = builderMessageError(TextualException.PARAM);

    public String CALL = builderMessageError(TextualException.CALL).compile();
    public String NULL = builderMessageError(TextualException.NULL).compile();
    public String EMPTY = builderMessageError(TextualException.BACK).compile();

    private static BuilderError messageBuilder = new BuilderError();
    private static StringBuilder message;

    private static BuilderError builderMessageError(TextualException value) {
        if (!ABSTRACT.equals(value)
                && !FRONT.equals(value)
                && !FRONT.equals(value)) {
            message.append(" ");
        } else {
            message = new StringBuilder();
        }
        message.append(value);
        return messageBuilder;
    }

    private String compile() {
        String returnedMessage = message.toString();
        message = new StringBuilder();
        return returnedMessage;
    }
}

