package ar.edu.unq.desapp.grupoI.backenddesappapi.builders;

import ar.edu.unq.desapp.grupoI.backenddesappapi.model.User;
public class UserBuilder {
    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    private String firstName = "firstName";
    private String lastName = "lastName";
    private String email = "email";
    private String username = "username";
    private String address = "address";
    private String password = "password";
    private String CVUMercadoPago = "CVUMercadoPago";
    private String walletAddress = "walletAddress";

    public User build() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setAddress(address);
        user.setPassword(password);
        user.setCvumercadoPago(CVUMercadoPago);
        user.setWalletAddress(walletAddress);

        return user;
    }

    public UserBuilder withFirstName(final String aFirstName) {
        firstName = aFirstName;
        return this;
    }

    public UserBuilder withLastName(final String aLastName) {
        lastName = aLastName;
        return this;
    }

    public UserBuilder withEmail(final String anEmail) {
        email = anEmail;
        return this;
    }

    public UserBuilder withAddress(final String anAddress) {
        address = anAddress;
        return this;
    }

    public UserBuilder withPassword(final String aPassword) {
        password = aPassword;
        return this;
    }

    public UserBuilder withCVU(final String aCVU) {
        CVUMercadoPago = aCVU;
        return this;
    }

    public UserBuilder withWallet(final String aWalletAddress) {
        walletAddress = aWalletAddress;
        return this;
    }

    public UserBuilder withUsername(final String aUsername) {
        username = aUsername;
        return this;
    }
}
