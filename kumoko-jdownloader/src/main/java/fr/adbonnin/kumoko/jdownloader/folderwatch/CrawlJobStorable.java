package fr.adbonnin.kumoko.jdownloader.folderwatch;

import fr.adbonnin.kumoko.jdownloader.controlling.Priority;
import fr.adbonnin.kumoko.jdownloader.extensions.extraction.BooleanStatus;

/**
 * @see <a href="https://github.com/mirror/jdownloader/blob/master/src/org/jdownloader/extensions/folderwatchV2/CrawlJobStorable.java">CrawlJobStorable.java</a>
 */
public class CrawlJobStorable {
    private String        filename;
    private int           chunks;
    private BooleanStatus autoConfirm;
    private boolean       addOfflineLink = true;

    public boolean isAddOfflineLink() {
        return addOfflineLink;
    }

    public void setAddOfflineLink(boolean addOfflineLink) {
        this.addOfflineLink = addOfflineLink;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public BooleanStatus getAutoConfirm() {
        return BooleanStatus.get(autoConfirm);
    }

    public void setAutoConfirm(BooleanStatus autoConfirm) {
        this.autoConfirm = autoConfirm;
    }

    public BooleanStatus getAutoStart() {
        return BooleanStatus.get(autoStart);
    }

    public void setAutoStart(BooleanStatus autoStart) {
        this.autoStart = autoStart;
    }

    public BooleanStatus getForcedStart() {
        return BooleanStatus.get(forcedStart);
    }

    public void setForcedStart(BooleanStatus forcedStart) {
        this.forcedStart = forcedStart;
    }

    public BooleanStatus getEnabled() {
        return enabled;
    }

    public void setEnabled(BooleanStatus enabled) {
        this.enabled = enabled;
    }

    private BooleanStatus autoStart;
    private BooleanStatus forcedStart;
    private BooleanStatus enabled;
    private String        text;
    private Boolean       deepAnalyseEnabled = false;
    private String        packageName;
    private Priority      priority;
    private BooleanStatus extractAfterDownload;

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public BooleanStatus getExtractAfterDownload() {
        return BooleanStatus.get(extractAfterDownload);
    }

    public void setExtractAfterDownload(BooleanStatus extractAfterDownload) {
        this.extractAfterDownload = extractAfterDownload;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDownloadFolder() {
        return downloadFolder;
    }

    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    public String[] getExtractPasswords() {
        return extractPasswords;
    }

    public void setExtractPasswords(String[] extractPasswords) {
        this.extractPasswords = extractPasswords;
    }

    public String getDownloadPassword() {
        return downloadPassword;
    }

    public void setDownloadPassword(String downloadPassword) {
        this.downloadPassword = downloadPassword;
    }

    public boolean isOverwritePackagizerEnabled() {
        return overwritePackagizerEnabled;
    }

    public void setOverwritePackagizerEnabled(boolean overwritePackagizerEnabled) {
        this.overwritePackagizerEnabled = overwritePackagizerEnabled;
    }

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String   downloadFolder;
    private String[] extractPasswords;
    private String   downloadPassword;
    private boolean  overwritePackagizerEnabled = true;
    private boolean  setBeforePackagizerEnabled = false;

    public boolean isSetBeforePackagizerEnabled() {
        return setBeforePackagizerEnabled;
    }

    public void setSetBeforePackagizerEnabled(boolean setBeforePackagizerEnabled) {
        this.setBeforePackagizerEnabled = setBeforePackagizerEnabled;
    }

    public Boolean isDeepAnalyseEnabled() {
        return deepAnalyseEnabled;
    }

    public void setDeepAnalyseEnabled(Boolean deepAnalyseEnabled) {
        this.deepAnalyseEnabled = deepAnalyseEnabled;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public JobType getType() {
        return type == null ? JobType.NORMAL : type;
    }

    public void setType(JobType type) {
        this.type = type;
    }

    public enum JobType {
        NORMAL
    }

    private JobType type = JobType.NORMAL;
}
