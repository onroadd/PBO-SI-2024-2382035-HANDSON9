import config.Database;
import entities.repositories.TodoListRepositoryDbImpl;
import repositories.TodoListRepository;
import services.TodoListService;
import services.TodoListServiceImpl;
import views.TodoListTerminalView;
import views.TodoListView;

public class Main {
    public static void main(String[] args) {
        // Setup database
        Database database = new Database("db_michael", "root", "", "localhost", "3306");
        database.setup();

        // Initialize repository, service, and view
        TodoListRepository todoListRepository = new TodoListRepositoryDbImpl(database);
        TodoListService todoListService = new TodoListServiceImpl(todoListRepository);
        TodoListView todoListView = new TodoListTerminalView(todoListService);

        // Run the application
        todoListView.run();
    }
}
