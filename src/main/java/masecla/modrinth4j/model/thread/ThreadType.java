package masecla.modrinth4j.model.thread;

/**
 * The type of a thread.
 */
public enum ThreadType {
    /** A thread for a report */
    REPORT("report"),
    
    /** A thread for a project */
    PROJECT("project"),
    
    /** A direct message thread */
    DIRECT_MESSAGE("direct_message");
    
    private final String value;
    
    ThreadType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
