package edu.stanford.cardinalkit.common

object Config {

    /**
     * Names of files in the assets folder to
     * extract tasks and items from.
     */
    const val TASKS_FILE = "tasks.json"
    const val CONTACTS_FILE = "contacts.json"

    /**
     * Determines whether tasks and contacts should be retrieved
     * from the database or files in the assets folder.
     */
    const val REMOTE_TASKS = false
    const val REMOTE_CONTACTS = false

    const val DEBUG = true
}
