package application;

import entities.Client;
import entities.Order;
import entities.OrderItem;
import entities.Product;
import entities.enums.OrderStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        Locale.setDefault(Locale.US);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter client data: ");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Birth date (DD/MM/YYYY): ");
        String birthDate = scanner.next();
        SimpleDateFormat formatBirthDate = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("\nEnter order data: ");

        scanner.nextLine();

        System.out.print("Status: ");
        String orderStatus = scanner.nextLine();
        OrderStatus convertedOrderStatus = OrderStatus.valueOf(orderStatus);

        System.out.print("How many items to this order?: ");
        Integer quantityOfItems = scanner.nextInt();

        Date moment = new Date();
        SimpleDateFormat formatMoment = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        Client client = new Client(name, email, formatBirthDate.parse(birthDate));

        Order order = new Order(moment, convertedOrderStatus, client);

        for (int i = 0; i < quantityOfItems; i++) {
            System.out.printf("\nEnter #%d item data: ", i + 1);

            scanner.nextLine();

            System.out.print("\nProduct name: ");
            String productName = scanner.nextLine();

            System.out.print("Product price: ");
            Double productPrice = scanner.nextDouble();

            System.out.print("Quantity: ");
            Integer quantity = scanner.nextInt();

            Product product = new Product(productName, productPrice);
            OrderItem item = new OrderItem(quantity, productPrice, product);

            order.addItem(item);
        }

        System.out.println("\nORDER SUMMARY: ");

        System.out.printf("\nOrder moment: %s", formatMoment.format(moment));
        System.out.printf("\nOrder status: %s", order.getStatus());
        System.out.printf("\nClient: %s (%s) - %s", order.getClient().getName(), formatBirthDate.format(order.getClient().getBirthDate()), order.getClient().getEmail());

        System.out.println("\nOrder items: ");

        for (OrderItem item : order.getItems()) {
            System.out.printf("\n%s, $%.2f, Quantity: %d, Subtotal: $%.2f", item.getProduct().getName(), item.getProduct().getPrice(), item.getQuantity(), item.subTotal());
        }

        System.out.printf("\n\nTotal price: %.2f", order.total());

        scanner.close();
    }
}