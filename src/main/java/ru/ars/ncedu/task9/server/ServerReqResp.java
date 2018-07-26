package ru.ars.ncedu.task9.server;

import ru.ars.ncedu.task9.Crypto;
import ru.ars.ncedu.task9.entity.UserBalance;
import ru.ars.ncedu.task9.entity.UserDate;
import ru.ars.ncedu.task9.req_resp.Request;
import ru.ars.ncedu.task9.req_resp.Response;
import ru.ars.ncedu.task9.requestdatabase.HelperDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class ServerReqResp extends HttpServlet {
    private static final String PHONE_NUMBER = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
    private static final String PASSWORD_VALIDATION = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Request request = JAXB.unmarshal(req.getReader(), Request.class);
        String clientOrigin = req.getHeader("origin");
        resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
        try {
            if (request.getType().equals("registerCustomer")) {
                registerCustomer(request, resp);
            } else if (request.getType().equals("setBalance")) {
                setBalance(request, resp);
            } else if (request.getType().equals("getBalance")) {
                getBalance(request, resp);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static void registerCustomer(Request request, HttpServletResponse resp) throws IOException, JAXBException {
        Response response = new Response();
        PrintWriter printWriter = resp.getWriter();
        JAXBContext context = JAXBContext.newInstance(response.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        if (request.getLogin().matches(PHONE_NUMBER) && request.getPassword().matches(PASSWORD_VALIDATION)
                && HelperDB.checkLogin(request.getLogin())) {
            UserDate userDate = new UserDate();
            userDate.setLogin(request.getLogin());
            userDate.setPassword(Crypto.encode(request.getPassword()));

            UserBalance userBalance = new UserBalance();
            userBalance.setLogin(request.getLogin());
            userBalance.setBalance(new BigDecimal(0));
            userDate.setUserBalanceByLogin(userBalance);
            HelperDB.addValue(userDate, userBalance);

            response.setCode(0);
            marshaller.marshal(response, printWriter);
        } else if (!request.getLogin().matches(PHONE_NUMBER)) {
            response.setCode(2);
            marshaller.marshal(response, printWriter);
        } else if (!request.getPassword().matches(PASSWORD_VALIDATION)) {
            response.setCode(3);
            marshaller.marshal(response, printWriter);
        } else if (!HelperDB.checkLogin(request.getLogin())) {
            response.setCode(1);
            marshaller.marshal(response, printWriter);
        } else {
            response.setCode(4);
            marshaller.marshal(response, printWriter);
        }
    }

    private static void setBalance(Request request, HttpServletResponse resp) throws JAXBException, IOException {
        Response response = new Response();
        PrintWriter printWriter = resp.getWriter();
        JAXBContext context = JAXBContext.newInstance(response.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        if (request.getBalance() == null || request.getLogin().equals("")
                || request.getLogin().equals("undefined")) {
            response.setCode(4);
            marshaller.marshal(response, printWriter);
        } else if (!HelperDB.checkLogin(request.getLogin())) {
            HelperDB.updateBalance(request.getLogin(), request.getBalance());
            response.setCode(0);
            response.setBalance(HelperDB.getBalanceDB(request.getLogin()));
            marshaller.marshal(response, printWriter);
        } else if (HelperDB.checkLogin(request.getLogin())) {
            response.setCode(1);
            marshaller.marshal(response, printWriter);
        } else {
            response.setCode(4);
            marshaller.marshal(response, printWriter);
        }
    }

    private static void getBalance(Request request, HttpServletResponse resp) throws JAXBException, IOException {
        Response response = new Response();
        PrintWriter printWriter = resp.getWriter();
        JAXBContext context = JAXBContext.newInstance(response.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        if (request.getLogin().equals("") || request.getPassword().equals("")
                || request.getLogin().equals("undefined") || request.getPassword().equals("undefined")) {
            response.setCode(4);
            marshaller.marshal(response, printWriter);
        } else if (HelperDB.checkLogin(request.getLogin())) {
            response.setCode(1);
            marshaller.marshal(response, printWriter);
        } else if (!HelperDB.correctPassword(request.getLogin(), Crypto.encode(request.getPassword()))) {
            response.setCode(3);
            marshaller.marshal(response, printWriter);
        } else if (!HelperDB.checkLogin(request.getLogin()) &&
                HelperDB.correctPassword(request.getLogin(), Crypto.encode(request.getPassword()))) {
            response.setCode(0);
            response.setBalance(HelperDB.getBalanceDB(request.getLogin()));
            marshaller.marshal(response, printWriter);
        } else {
            response.setCode(4);
            marshaller.marshal(response, printWriter);
        }
    }
}
