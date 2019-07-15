package re.apayet.acquisition.model.data;

public enum ActionKey {

    KEY("key"),
    MOUSE("mouse"),
    MOUSE_PRESSED("mousePressed"),
    MOUSE_RELEASE("mouseRelease"),
    MOUSE_ACTION("mouseAction"),
    KEY_PRESSED("KeyPressed"),
    KEY_RELEASE("KeyRelease"),
    KEY_ACTION("KeyAction");

    private String name = "";

    ActionKey(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
