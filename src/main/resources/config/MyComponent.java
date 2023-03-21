package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.activation.DataSource;

@Component
public class MyComponent {
    @Autowired
    private DataSource dataSource;

    public void executeQuery() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM mytable");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // process result set
            }
        }
    }
}
