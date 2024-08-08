package lk.ijse.gdse.pos.dao;

import lk.ijse.gdse.pos.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse.pos.dao.custom.impl.ItemDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOTypes daoTypes) {

        switch (daoTypes) {
            case CUSTOMER -> {
                return new CustomerDAOImpl();
            }
            case ITEM -> {
                return new ItemDAOImpl();
            }
//            case ORDER -> {
//                return new OrderDAOImpl();
//            }
//            case ORDER_DETAILS -> {
//                return new OrderDetailDAOImpl();
//            }
            default -> {
                return null;
            }
        }
    }

    public enum DAOTypes {
        CUSTOMER, ITEM
    }

}
