package tech.devinhouse.aviation.model.enums;

public enum ClassificationEnum {
    VIP(100),
    GOLD(80),
    SILVER(50),
    BRONZE(30),
    ASSOCIATED(10);

    private Integer balance;

    ClassificationEnum(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return balance;
    }
}
