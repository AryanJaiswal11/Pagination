package com.CodeVector.DemoTask.Config;

import com.CodeVector.DemoTask.Enums.Category;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Async // Spawns a background task so the REST endpoint can return instantly
@Component
public class ProductSeeder {

    @Autowired
    private DataSource dataSource;

    private static final String INSERT_SQL =
            "INSERT INTO products (name, category, price, quantity, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";

    private static final int TOTAL_RECORDS = 200_000;
    private static final int BATCH_SIZE = 5000;

    public void seedProducts() {
        Faker faker = new Faker();
        long startTime = System.currentTimeMillis();
        System.out.println("Starting data generation and database seeding...");

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            connection.setAutoCommit(false);
            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

            for (int i = 1; i <= TOTAL_RECORDS; i++) {
                // 1. Generate realistic fake product details
                String productName = faker.commerce().productName();
                // Assumes Category is an Enum. Picks a random enum constant.
                Category categoryName = faker.options().option(Category.class);
                double price = faker.number().randomDouble(2, 5, 1000); // 2 decimals, min $5, max $1000
                int quantity = faker.number().numberBetween(1, 500);

                // 2. Bind parameters to PreparedStatement
                preparedStatement.setString(1, productName);
                preparedStatement.setString(2, categoryName.name()); // Or use .setInt() if Enum is mapped to Ordinal
                preparedStatement.setDouble(3, price);
                preparedStatement.setInt(4, quantity);
                preparedStatement.setTimestamp(5, now); // Simulating @CreationTimestamp
                preparedStatement.setTimestamp(6, now); // Simulating @UpdateTimestamp

                preparedStatement.addBatch();

                // 3. Batch processing execution chunks
                if (i % BATCH_SIZE == 0) {
                    preparedStatement.executeBatch();
                }
            }

            // Flush out remaining rows
            if (TOTAL_RECORDS % BATCH_SIZE != 0) {
                preparedStatement.executeBatch();
            }

            connection.commit();
            long duration = System.currentTimeMillis() - startTime;
            System.out.println("✅ Successfully seeded " + TOTAL_RECORDS + " products in " + duration + " ms.");

        } catch (SQLException e) {
            System.err.println("❌ Seeding failed. Executing safety rollback.");
            e.printStackTrace();
        }
    }
}
