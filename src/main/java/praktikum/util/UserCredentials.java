package praktikum.util;

import com.github.javafaker.Faker;
import praktikum.model.User;

public class UserCredentials {
    private String email;
    private String password;
    private String name;
    private static Faker faker = new Faker();

    public UserCredentials() {
    }

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredentials(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public UserCredentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserCredentials setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "email='" + email + '\'' +
                '}';
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

    public static UserCredentials authorizeWithEmail(User user) {
        return new UserCredentials().setEmail(faker.internet().emailAddress());
    }

    public static UserCredentials authorizeWithPassword(User user) {
        return new UserCredentials().setPassword(faker.internet().password());
    }

    public static UserCredentials authorizeWithRandomCredentials(User user) {
        return new UserCredentials()
                .setEmail(faker.bothify("????####@gmail.com"))
                .setPassword(faker.bothify("?????#####"));
    }

    public static UserCredentials changeEmail(User user) {
        return new UserCredentials().setEmail(user.getEmail());
    }

    public static UserCredentials changePassword(User user) {
        return new UserCredentials().setPassword(user.getPassword());
    }

    public static UserCredentials changeName(User user) {
        return new UserCredentials().setName(user.getName());
    }

    public static UserCredentials changeCredentials() {
        return new UserCredentials()
                .setEmail(faker.bothify("????####@gmail.com"))
                .setPassword(faker.internet().password())
                .setName(faker.artist().name());

    }
}
