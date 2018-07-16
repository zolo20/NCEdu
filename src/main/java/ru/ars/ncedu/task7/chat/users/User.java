package ru.ars.ncedu.task7.chat.users;

public class User {
    private String nickname;
    private String massage;
    private String to;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + nickname + '\'' +
                ", massage='" + massage + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
