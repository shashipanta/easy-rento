package com.tms.easyrento.util.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author barbosa
 * @version 1.0.0
 * @since 2025-07-18 11:40
 */

@Profile("dev")
@Component
public class I18MessageKeyGenerator implements CommandLineRunner {

    private final DataSource dataSource;

    private static final String OUTPUT_FILE = "src/main/resources/messages.generated.properties";

    private static final Set<String> EXCLUDED_TABLES = Set.of(
            "flyway_schema_history", "schema_version"
    );

    private static final Set<String> EXCLUDED_COLUMNS = Set.of(
            "created_by", "created_on", "last_modified_by", "last_modified_on", "status", "active"
    );

    public I18MessageKeyGenerator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> lines = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME").toLowerCase();

                if (EXCLUDED_TABLES.contains(tableName)) continue;

                // add table header
                lines.add("# Table: " + tableName);
                // add table name too
                lines.add(tableName  + "=" + prettify(tableName));

                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME").toLowerCase();

                    if (EXCLUDED_COLUMNS.contains(columnName)) {
                        System.out.println("Ignoring " + tableName + ": " + columnName);
                        continue;
                    }

                    String key = tableName.toLowerCase() + "." + columnName;
                    String prettyLabel = prettify(columnName);

                    lines.add(key + "=" + prettyLabel);
                }
                lines.add("\n");
                columns.close();
            }
            tables.close();
        }

        // Write to file
        Path outputPath = Path.of(OUTPUT_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath.toFile()))) {
            writer.write("# Auto-generated message keys for field labels");
            writer.newLine();
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }

        System.out.println("✅ messages.generated.properties created at " + outputPath.toAbsolutePath());

    }

    private String prettify(String columnName) {
        return Arrays.stream(columnName.split("_"))
                .map(part -> part.isEmpty() ? "":
                        Character.toUpperCase(part.charAt(0))
                        + part.substring(1).toLowerCase()
                )
                .collect(Collectors.joining(" "));
    }
}
