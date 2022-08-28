package praktikum.util;

import com.github.javafaker.Faker;
import praktikum.model.User;

public class UserCredentials {
    public String email;
    public String password;
    public String name;
    static Faker faker = new Faker();

    public UserCredentials() {
    }
    public UserCredentials(String email, String password){
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
        return new UserCredentials(user.email, user.password);
    }
    public static UserCredentials authorizeWithEmail(User user) {
        return new UserCredentials().setEmail(user.email);
    }

    public static UserCredentials authorizeWithPassword(User user) {
        return new UserCredentials().setPassword(user.password);
    }
    public static UserCredentials authorizeWithRandomCredentials(User user) {
        return new UserCredentials()
                .setEmail(faker.bothify("????####@gmail.com"))
                .setPassword(faker.bothify("?????#####"));
    }
    public static UserCredentials changeEmail(User user){
        return new UserCredentials().setEmail(user.email);
    }
    public static UserCredentials changePassword(User user){
        return new UserCredentials().setPassword(user.password);
    }
    public static UserCredentials changeName(User user){
        return new UserCredentials().setName(user.name);
    }
    public static UserCredentials changeCredentials(){
        return new UserCredentials()
                .setEmail(faker.bothify("????####@gmail.com"))
                .setPassword(faker.internet().password())
                .setName(faker.artist().name());

    }
}
