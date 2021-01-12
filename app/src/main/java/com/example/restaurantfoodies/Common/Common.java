package com.example.restaurantfoodies.Common;


import com.example.restaurantfoodies.Model.Restaurant_Profile;
import com.example.restaurantfoodies.Model.User;

public class Common {

    public static Restaurant_Profile currentRestaurant;
    public static final String UPDATE="Update";
    public static final String DELETE="Delete";
//    public static Request currentRequest;

    public static String convertCodeToStatus(String status) {

        if (status.equals("0"))
            return "Placed";

        else if (status.equals("1"))
            return "On the Way";

        else
            return "Shipped";
    }
}
