package re.apayet.acquisition.model.base.configuration;

public enum ConfigurationValues {

    NATIVE_CONTAINER_INSTANCE_UNIQUE("native.container.instance.unique"),

    KEYBOARD("native.keyboard"),
    MOUSE("native.mouse"),
    PROCESS("native.process"),
    SCREEN("native.screen"),
    CAM("native.cam"),

    JSON_WRITER("native.json.writer"),
    JSON_WRITER_PATH("json.writer.path"),

    MEMORY_WRITER("native.memory.writer"),
    EVENT_MEMORY_WRITER("event.memory.writer"),

    THREAD_SLEEP("thread.sleep"),

    DEFAULT_FLUSH_TIME("default.flush.time"),
    DEFAULT_LIVE_TIME("default.live.time"),

    KEY_FLUSH_TIME("key.flush.time"),
    KEY_LIVE_TIME("key.live.time"),

    MOUSE_FLUSH_TIME("mouse.flush.time"),
    MOUSE_LIVE_TIME("mouse.live.time"),

    SCREEN_FLUSH_TIME("screen.flush.time"),
    SCREEN_LIVE_TIME("screen.live.time"),

    PROCESS_FLUSH_TIME("process.flush.time"),
    PROCESS_LIVE_TIME("process.live.time"),

    CAM_FLUSH_TIME("cam.flush.time"),
    CAM_LIVE_TIME("cam.live.time");

    private String name;

    ConfigurationValues(String name) {
        this.name = name;
    }

    public String value() {
        return name;
    }
}
