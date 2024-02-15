package nl.youngcapital.backend.model;

import java.io.Serializable;

public class SessionDTO implements Serializable {
    private final long userId;
    private final long accountId;


    public SessionDTO(long userId, long accountId) {
        this.userId = userId;
        this.accountId = accountId;
    }


    public long getUserId() {
        return userId;
    }

    public long getAccountId() {
        return accountId;
    }

}
