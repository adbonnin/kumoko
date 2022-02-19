package fr.adbonnin.kumoko.jdownloader.extensions.extraction;

/**
 * @see <a href="https://github.com/mirror/jdownloader/blob/master/src/org/jdownloader/extensions/extraction/BooleanStatus.java">BooleanStatus.java</a>
 */
public enum BooleanStatus {
    UNSET,
    TRUE,
    FALSE;

    public final Boolean getBoolean() {
        switch (this) {
            case FALSE:
                return Boolean.FALSE;
            case TRUE:
                return Boolean.TRUE;
            default:
                return null;
        }
    }

    public static BooleanStatus convert(Boolean status) {
        return status == null ? UNSET : (status ? TRUE : FALSE);
    }

    public static BooleanStatus get(BooleanStatus status) {
        return status == null ? UNSET : status;
    }

    public static boolean isSet(BooleanStatus status) {
        return status != null && status.getBoolean() != null;
    }

    public static Boolean convert(BooleanStatus status) {
        if (status != null) {
            return status.getBoolean();
        }
        else {
            return null;
        }
    }
}
