package ru.ars.ncedu.task7.exeception;

public class UserExistException extends Exception{

    public UserExistException() {super();}

    public UserExistException(String message) {
        super(message);
    }
}
