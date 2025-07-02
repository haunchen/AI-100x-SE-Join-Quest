package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderStepDefinitions {

    private OrderService orderService;
    private Order order;
    private OrderSummary orderSummary;
    private List<Promotion> promotions = new ArrayList<>();

    @Given("no promotions are applied")
    public void no_promotions_are_applied() {
        orderService = new OrderService();
    }

    @When("a customer places an order with:")
    public void a_customer_places_an_order_with(DataTable dataTable) {
        List<OrderItem> items = DataTableTransformer.toOrderItems(dataTable);
        order = new Order(new Customer(), items);
        orderService = new OrderService(promotions);
        orderSummary = orderService.calculateTotal(order);
    }

    @Then("the order summary should be:")
    public void the_order_summary_should_be(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> expectedSummary = rows.get(0);
        assertEquals(Integer.parseInt(expectedSummary.get("totalAmount")), orderSummary.getTotalAmount());
        if (expectedSummary.containsKey("originalAmount")) {
            assertEquals(Integer.parseInt(expectedSummary.get("originalAmount")), orderSummary.getOriginalAmount());
        }
        if (expectedSummary.containsKey("discount")) {
            assertEquals(Integer.parseInt(expectedSummary.get("discount")), orderSummary.getDiscount());
        }
    }

    @Then("the customer should receive:")
    public void the_customer_should_receive(DataTable dataTable) {
        List<Map<String, String>> expectedItems = dataTable.asMaps(String.class, String.class);
        List<OrderItem> actualItems = orderSummary.getReceivedItems();

        assertEquals(expectedItems.size(), actualItems.size());

        for (int i = 0; i < expectedItems.size(); i++) {
            Map<String, String> expectedItem = expectedItems.get(i);
            OrderItem actualItem = actualItems.get(i);
            assertEquals(expectedItem.get("productName"), actualItem.getProduct().getName());
            assertEquals(Integer.parseInt(expectedItem.get("quantity")), actualItem.getQuantity());
        }
    }

    @Given("the threshold discount promotion is configured:")
    public void the_threshold_discount_promotion_is_configured(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> config = rows.get(0);
        if (config.get("threshold").startsWith("same product")) {
            // This is for the double 11 feature, do nothing for now.
        } else {
            promotions.add(new ThresholdDiscountPromotion(
                Integer.parseInt(config.get("threshold")),
                Integer.parseInt(config.get("discount"))
            ));
        }
    }

    @Given("the buy one get one promotion for cosmetics is active")
    public void the_buy_one_get_one_promotion_for_cosmetics_is_active() {
        promotions.add(new BuyOneGetOnePromotion("cosmetics"));
    }
}