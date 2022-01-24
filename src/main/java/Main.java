import application.domain.User;
import dao.SqlUserRepository;

public class Main {
    public static void main(String[] args) {
        SqlUserRepository userRepository = new SqlUserRepository();
        userRepository.addUser(new User("MSN", "asd@asd", "+38050200212"));
    }
}
