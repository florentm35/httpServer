package fr.florent.httpserver.request;

public enum EnumAction {
    GET, POST, PUT, DELETE;

    /**
     * Return {@link fr.florent.httpserver.request.EnumAction} by str gived
     *
     * @param str
     * @return {@link fr.florent.httpserver.request.EnumAction}
     */
    public static EnumAction getAction(String str) {
        for (var action : EnumAction.values()) {
            if (action.name().equals(str)) {
                return action;
            }
        }
        return null;
    }
}
