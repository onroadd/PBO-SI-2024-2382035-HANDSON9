package entities.repositories;

import config.Database;
import entities.TodoList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoListRepositoryDbImpl implements repositories.TodoListRepository {
    private final Database database;

    public TodoListRepositoryDbImpl(Database database) {
        this.database = database;
    }

    @Override
    public TodoList[] getAll() {
        String sqlStatement = "SELECT * FROM todos";
        List<TodoList> todoLists = new ArrayList<>();
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                TodoList todoList = new TodoList();
                todoList.setId(resultSet.getInt("id"));
                todoList.setTodo(resultSet.getString("todo"));
                todoLists.add(todoList);
            }
        } catch (Exception e) {
            System.err.println("Error fetching todos: " + e.getMessage());
        }
        return todoLists.toArray(TodoList[]::new);
    }

    @Override
    public void add(TodoList todoList) {
        String sql = "INSERT INTO todos (todo) VALUES (?)";
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, todoList.getTodo());
            preparedStatement.executeUpdate();
            System.out.println("Todo added successfully!");
        } catch (Exception e) {
            System.err.println("Error adding todo: " + e.getMessage());
        }
    }

    @Override
    public Boolean remove(Integer id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error removing todo: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean edit(TodoList todoList) {
        String sql = "UPDATE todos SET todo = ? WHERE id = ?";
        try (Connection connection = database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, todoList.getTodo());
            preparedStatement.setInt(2, todoList.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Error updating todo: " + e.getMessage());
        }
        return false;
    }
}
