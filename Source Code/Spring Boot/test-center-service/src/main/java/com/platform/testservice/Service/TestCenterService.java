package com.platform.testservice.Service;

import com.platform.testservice.DAO.TestCenterDAO;
import com.platform.testservice.DTO.CredentialsDTO;
import com.platform.testservice.DTO.TestCenterUpdateDTO;
import com.platform.testservice.Entity.Branch;
import com.platform.testservice.Entity.TestCenter;
import com.platform.testservice.Persistence.DBConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TestCenterService implements TestCenterDAO {


    @Override
    public TestCenter getByName(String name) {
        String sql = "SELECT * FROM testcenter WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractTestCenterFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private TestCenter extractTestCenterFromResultSet(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        return new TestCenter(name, password);
    }

    @Override
    public int getIdByName(String name) {
        String sql = "SELECT center_id FROM testcenter WHERE name = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("center_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String addGeneratedCredentials(CredentialsDTO credentialsDTO) {

        String sql = "INSERT INTO generated_credentials (name, password) VALUES (?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, credentialsDTO.getName());
            statement.setString(2, credentialsDTO.getPassword());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Adding Generated Credentials Failed, no rows affected.";
            }
            return "Adding Generated Credentials Successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error in Adding Generated Credentials: " + e.getMessage();
        }

    }

    @Override
    public CredentialsDTO getGeneratedCredential() {
        CredentialsDTO credentialsDTO = new CredentialsDTO();
        String sql = "Select * from generated_credentials LIMIT 1";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) { // resultSet.next function used to iterate for each row in database
                    credentialsDTO.setName(resultSet.getString("name"));
                    credentialsDTO.setPassword(resultSet.getString("password"));
                }
                if (credentialsDTO.getName() == null)
                    return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting Generated Credentials: " + e.getMessage());
            return null;
        }
        return credentialsDTO;
    }

    @Override
    public String removeGeneratedCredentials(CredentialsDTO credentialsDTO) {
        String sql = "DELETE FROM generated_credentials WHERE name = ? AND password = ?";
        String MSG = "";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, credentialsDTO.getName());
            statement.setString(2, credentialsDTO.getPassword());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                MSG = "Generated Credentials Removed Successfully";
            } else {
                MSG = "Generated Credentials is not found";
            }

        } catch (SQLException e) {
            MSG = "Error Removing Generated Credentials: " + e.getMessage();
        }
        return MSG;
    }

    @Override
    public String addTestCenterCredentials(CredentialsDTO credentialsDTO) {
        String sql = "INSERT INTO testcenter (name, password) VALUES (?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, credentialsDTO.getName());
            statement.setString(2, credentialsDTO.getPassword());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Adding Test Center Credentials Failed, no rows affected.";
            }
            return "Adding Test Center Credentials Successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error in Adding Test Center Credentials: " + e.getMessage();
        }
    }

    @Override
    public String editTestCenterInfo(int testCenterId, TestCenterUpdateDTO testCenterUpdateDTO) {
        String sql = "UPDATE testcenter " +
                "SET name = ?, email = ?, bio = ?" +
                "WHERE center_id = ?";

        String MSG = "";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, testCenterUpdateDTO.getName());
            statement.setString(2, testCenterUpdateDTO.getEmail());
            statement.setString(3, testCenterUpdateDTO.getBio());
            statement.setInt(4, testCenterId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                MSG = "Test Center Info Updated Successfully";
            } else {
                MSG = "Test Center Info is not Updated";
            }
        } catch (SQLException e) {
            MSG = "Error Updating TestCenter Info: " + e.getMessage();
        }
        return MSG;
    }

    @Override
    public String addTestCenterBranchInfo(Branch branch) {

        String sql = "INSERT INTO branch (center_id, name, address, location, center_name) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, branch.getCenter_id());
            statement.setString(2, branch.getName());
            statement.setString(3, branch.getAddress());
            statement.setString(4, branch.getLocation());
            statement.setString(5, branch.getCenter_name());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                return "Creating Branch Failed, no rows affected.";
            }

            return "Branch Created Successfully.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error Creating Branch: " + e.getMessage();
        }

    }

    @Override
    public Branch getTestCenterByLocation(String locationName) {
        Branch branch = new Branch();
        String sql = "SELECT branch.name, branch.address, branch.location, testcenter.bio " +
                "FROM branch " +
                "JOIN testcenter ON branch.center_id = testcenter.center_id " +
                "WHERE location = ?";
        try (Connection connection = DBConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, locationName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) { // resultSet.next function used to iterate for each row in database
                    branch.setName(resultSet.getString("name"));
                    branch.setName(resultSet.getString("address"));
                    branch.setName(resultSet.getString("location"));
                    branch.setName(resultSet.getString("name"));
                    branch.setCenter_bio(resultSet.getString("bio"));
                }
                if (branch.getName() == null)
                    return null;
            }
        } catch (SQLException e) {
            System.err.println("Error getting test center branch by location: " + e.getMessage());
        }
        return branch;
    }

}
