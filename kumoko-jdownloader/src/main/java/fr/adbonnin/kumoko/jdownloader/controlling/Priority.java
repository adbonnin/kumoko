package fr.adbonnin.kumoko.jdownloader.controlling;

/**
 * @see <a href="https://github.com/mirror/jdownloader/blob/master/src/org/jdownloader/controlling/Priority.java">Priority.java</a>
 */
public enum Priority {
    HIGHEST(3),
    HIGHER(2),
    HIGH(1),
    DEFAULT(0),
    LOW(-1),
    LOWER(-2),
    LOWEST(-3);

    private final int id;

    public final int getId() {
        return id;
    }

    Priority(int p) {
        id = p;
    }

    public static Priority getPriority(int p) {

        if (p > 3) {
            p = 3;
        }

        if (p < -3) {
            p = -3;
        }

        switch (p) {
            case 3:
                return HIGHEST;
            case 2:
                return HIGHER;
            case 1:
                return HIGH;
            case -1:
                return LOW;
            case -2:
                return LOWER;
            case -3:
                return LOWEST;
            case 0:
            default:
                return DEFAULT;
        }
    }
}