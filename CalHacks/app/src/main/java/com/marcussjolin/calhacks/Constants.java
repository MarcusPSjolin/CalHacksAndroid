package com.marcussjolin.calhacks;

public class Constants {
    public static class Address {
        public static String first_name = "first_name";
        public static String last_name = "last_name";
        public static String city = "city";
        public static String address = "address";
        public static String phone_number = "phone_number";
        public static String state = "state";
    }

    public static class Item {
        public static String title = "title";
        public static String description = "description";
        public static String facility = "facility";
        public static String state = "state";
        public static String images = "images";
    }

    public static class User {
        public static String user_id = "user_id";
    }

    public static class Response {
        public static String id = "_id";
    }

    public static class PostmatesResponse {
        public static String id = "id";
        public static String fee = "fee";
        public static String currency = "currency";
        public static String duration = "duration";
    }

    public static class PostmatesUpdates {
        public static String courier = "courier";

        public static class Courier {
            public static String phone_number = "phone_number";
            public static String name = "name";
        }

        public static String lat = "lat";
        public static String lng = "lng";
        public static String pickup_eta = "pickup_eta";
        public static String dropoff_eta = "dropoff_eta";
        public static String pickup_millis = "pickup_millis";
        public static String dropoff_millis = "dropoff_millis";
    }
}
