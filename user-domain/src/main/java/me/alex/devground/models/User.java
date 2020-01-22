package me.alex.devground.models;

public class User {

    private final int id;

    private final String name;

    private final String contactEmail;

    public User(int id, String name, String contactEmail) {
        this.id = id;
        this.name = name;
        this.contactEmail = contactEmail;
    }

    public User(User user, int id) {
        this(id, user.getName(), user.getContactEmail());
    }

    public User(User user, String name) {
        this(user.getId(), name, user.getContactEmail());
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public static class UserBuilder { // Choice to avoid Lombok
        private User user;

        private UserBuilder() {
            this.user = new User(0, "", "");
        }

        public UserBuilder id(int id) {
            user = new User(id, user.getName(), user.getContactEmail());
            return this;
        }

        public UserBuilder name(String name) {
            user = new User(user.getId(), name, user.getContactEmail());
            return this;
        }

        public UserBuilder contactEmail(String contactEmail) {
            user = new User(user.getId(), user.getName(), contactEmail);
            return this;
        }

        public User build() {
            return user;
        }
    }
}
