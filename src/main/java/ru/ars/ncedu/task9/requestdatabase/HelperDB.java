package ru.ars.ncedu.task9.requestdatabase;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.ars.ncedu.task9.entity.UserBalance;
import ru.ars.ncedu.task9.entity.UserDate;

import java.math.BigDecimal;
import java.util.List;

public class HelperDB {

    public static void addValue(UserDate userDate, UserBalance userBalance) {
        try (Session session = SessionHibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(userDate);
            session.save(userBalance);
            session.getTransaction().commit();
        }
    }

    public static boolean checkLogin(String login) {
        List list;
        try (Session session = SessionHibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("SELECT ub.balance FROM UserBalance ub WHERE ub.login = :login");
            query.setParameter("login", login);
            list = query.list();
            session.getTransaction().commit();
        }
        return list.size() == 0;
    }

    public static void updateBalance(String login, BigDecimal balance){
        try (Session session = SessionHibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "update UserBalance ub set ub.balance = :newBalance where ub.login = :oldLogin"
            );
            query.setParameter("newBalance", balance);
            query.setParameter("oldLogin", login);

            query.executeUpdate();
            session.getTransaction().commit();
        }
    }

    public static BigDecimal getBalanceDB(String login) {
        BigDecimal prevBalance;
        try (Session session = SessionHibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("SELECT ub.balance FROM UserBalance ub WHERE ub.login = :login");
            query.setParameter("login", login);
            prevBalance = (BigDecimal) query.list().get(0);
            session.getTransaction().commit();
        }
        return prevBalance;
    }

    public static boolean correctPassword(String login, String password) {
        boolean isCorrect;
        try (Session session = SessionHibernate.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("SELECT ud.password FROM UserDate ud WHERE ud.login = :login");
            query.setParameter("login", login);
            session.getTransaction().commit();
            isCorrect = query.list().get(0).equals(password);
        }
        return isCorrect;
    }
}
