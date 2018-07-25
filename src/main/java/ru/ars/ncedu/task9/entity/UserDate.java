package ru.ars.ncedu.task9.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_date", schema = "public", catalog = "userdata")
public class UserDate {
    private String login;
    private String password;
    private UserBalance userBalanceByLogin;

    @Id
    @Column(name = "login", nullable = false, length = -1)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDate userDate = (UserDate) o;
        return Objects.equals(login, userDate.login) &&
                Objects.equals(password, userDate.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, password);
    }

    @OneToOne(mappedBy = "userDateByLogin")
    public UserBalance getUserBalanceByLogin() {
        return userBalanceByLogin;
    }

    public void setUserBalanceByLogin(UserBalance userBalanceByLogin) {
        this.userBalanceByLogin = userBalanceByLogin;
    }
}
