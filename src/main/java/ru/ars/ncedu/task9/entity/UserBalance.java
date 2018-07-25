package ru.ars.ncedu.task9.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "user_balance", schema = "public", catalog = "userdata")
public class UserBalance {
    private String login;
    private BigDecimal balance;
    private UserDate userDateByLogin;

    @Id
    @Column(name = "login", nullable = false, length = -1)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "balance", nullable = false, precision = 2)
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBalance that = (UserBalance) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, balance);
    }

    @OneToOne
    @JoinColumn(name = "login", referencedColumnName = "login", nullable = false)
    public UserDate getUserDateByLogin() {
        return userDateByLogin;
    }

    public void setUserDateByLogin(UserDate userDateByLogin) {
        this.userDateByLogin = userDateByLogin;
    }
}
