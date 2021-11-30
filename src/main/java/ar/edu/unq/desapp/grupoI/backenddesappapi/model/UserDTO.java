package ar.edu.unq.desapp.grupoI.backenddesappapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "username is required")
    private String username;

    @JsonIgnoreProperties
    private String password;

    @NotBlank(message = "first name is required")
    @Size(min = 4, max = 30, message = "firstname length invalid")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 4, max = 30, message = "lastname length invalid")
    private String lastName;

    @NotBlank(message = "Address is required")
    @Size(min = 10, max = 30, message = "address length invalid")
    private String address;

    @NotBlank(message = "cvu mercado pago is required")
    @Size(min = 22, max = 22, message = "invalid cvu number")
    private String cvumercadoPago;

    @NotBlank(message = "walletAddress is required")
    @Size(min = 8, max = 8, message = "invalid wallet address number")
    private String walletAddress;

    @NotBlank(message = "email is required")
    @Email(message= "invalid email")
    private String email;

}
