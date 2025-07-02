package com.example;

import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataTableTransformer {

    public static List<OrderItem> toOrderItems(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<OrderItem> items = new ArrayList<>();
        for (Map<String, String> columns : rows) {
            Product product = new Product(
                columns.get("productName"),
                columns.get("category"),
                Integer.parseInt(columns.get("unitPrice"))
            );
            items.add(new OrderItem(product, Integer.parseInt(columns.get("quantity"))));
        }
        return items;
    }
}