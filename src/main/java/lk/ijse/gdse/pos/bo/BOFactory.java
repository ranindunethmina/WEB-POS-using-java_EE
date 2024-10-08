package lk.ijse.gdse.pos.bo;

import lk.ijse.gdse.pos.bo.custom.impl.CustomerBOImpl;
import lk.ijse.gdse.pos.bo.custom.impl.ItemBOImpl;
import lk.ijse.gdse.pos.bo.custom.impl.OrderBOImpl;
import lk.ijse.gdse.pos.bo.custom.impl.OrderDetailBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public SuperBO getBO(BOTypes boTypes) {
        switch (boTypes) {
            case CUSTOMER -> {
                return new CustomerBOImpl();
            }
            case ITEM -> {
                return new ItemBOImpl();
            }
            case ORDER -> {
                return new OrderBOImpl();
            }
            case ORDER_DETAIL -> {
                return new OrderDetailBOImpl();
            }
            default -> {
                return null;
            }
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, ORDER, ORDER_DETAIL
    }
}