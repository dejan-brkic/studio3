package org.craftercms.studio.impl.repository.mongodb;

/**
 * Holds all Constants.
 */
public final class MongoRepositoryDefaults {

    /**
     * Default Repository username.
     */
    public static final String SYSTEM_USER_NAME = "System";
    /**
     * MongoDB Collection ID key for document.
     */
    public static final String MONGODB_ID_KEY = "_id";
    /**
     * Default name of content folder.
     */
    public static final String REPO_DEFAULT_CONTENT_FOLDER = "content";
    /**
     * Default name of configuration folder.
     */
    public static final String REPO_DEFAULT_CONFIG_FOLDER = "config";
    /**
     * Default path separator.
     */
    public static final String REPO_DEFAULT_PATH_SEPARATOR_CHAR = "/";
    /**
     * Regex that validates a path.
     */
    public static final String PATH_VALIDATION_REGEX = "(?!.*//.*)(?!.*/ .*)/{1}([^\\\\(){}:\\*\\?<>\\|\\\"\\'])*";
    /**
     * If a path is not found, should the system created (like *ix mkdir -p).
     */
    public static final String REPO_MKDIRS = "studio.repocitory.mongodb.mkdirs";

    /**
     * Make sure nobody creates a instance of this class.
     */
    private MongoRepositoryDefaults() {
    }
}
