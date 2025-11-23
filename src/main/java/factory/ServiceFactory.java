package factory;

import services.*;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {}

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public UserService getUserService() {
        return new UserService();
    }

    public FlightService getFlightService() {
        return new FlightService();
    }

    public HotelService getHotelService() {
        return new HotelService();
    }

    public BookingService getBookingService() {
        return new BookingService();
    }

    public MessageService getMessageService() {
        return new MessageService();
    }
}