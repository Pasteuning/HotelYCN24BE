package nl.youngcapital.backend.model;

public class SessionDTO {
    private long userId;
    private long accountId;


    public SessionDTO(long userId, long accountId) {
        this.userId = userId;
        this.accountId = accountId;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
