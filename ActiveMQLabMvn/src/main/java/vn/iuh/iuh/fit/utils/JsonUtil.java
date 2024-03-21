package vn.iuh.iuh.fit.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import vn.iuh.iuh.fit.dto.OrderDTO;
import vn.iuh.iuh.fit.models.Product;

import java.util.List;

public class JsonUtil {
    private static Gson gson = new Gson();

    public static String toJson(OrderDTO orderDTO) {
        return gson.toJson(orderDTO);
    }

    public static OrderDTO fromJson(String json) {
        return gson.fromJson(json, OrderDTO.class);
    }
}
