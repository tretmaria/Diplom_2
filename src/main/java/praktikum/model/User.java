package praktikum.model;

import com.github.javafaker.Faker;

public class User {
    public String email;
    public String password;
    public String name;
    static Faker faker = new Faker();

    public User() {
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                //", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static User getRandomUserCredentials() {
        final String email = faker.bothify("????####@gmail.com");
        final String password = faker.internet().password();
        final String name = faker.artist().name();
        return new User(email, password, name);
    }
    public static User getUserWithEmailOnly() {
        return new User().setEmail(faker.bothify("????####@gmail.com"));
    }
    public static User getUserWithPasswordOnly(){
        return new User().setPassword(faker.internet().password());
    }
    public static User getUserWithNameOnly(){
        return new User().setName(faker.artist().name());
    }
    public static User getUserWithoutName(){
        return new User()
            .setEmail(faker.bothify("????####@gmail.com"))
            .setPassword(faker.internet().password())
            .setEmail("");
    }
}
