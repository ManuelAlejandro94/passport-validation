package app.netlify.maav.passportvalidation.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passport {

    @NotNull(message = "Field is required")
    @NotBlank(message = "Can't be empty")
    @Size(max = 1, message = "Has to be length 1")
    private String type;

    @NotBlank
    @Size(min = 1, max = 3, message = "Length has to be between 1 and 3")
    private String country;

    @NotBlank
    @Size(min = 1, max = 9, message = "Length has to be between 1 and 9")
    private String passportNumber;

    @NotNull(message = "Field is required")
    private String firstName;

    @NotNull(message = "Field is required")
    private String lastName;

    @Pattern(regexp = "^(((0[1-9]|[12][0-9]|3[01])[- /.](0[13578]|1[02])|(0[1-9]|[12][0-9]|30)[- /.](0[469]|11)|(0[1-9]|1\\d|2[0-8])[- /.]02)[- /.]\\d{4}|29[- /.]02[- /.](\\d{2}(0[48]|[2468][048]|[13579][26])|([02468][048]|[1359][26])00))$", 
                message = "Format incorrect (dd/MM/yyyy)")
    private String dateOfBirth;  //Format dd/MM/yyyy

    @NotNull(message = "Field is required")
    @NotBlank
    @Size(max = 1, message = "Length has to be 1" )
    private String sex;

    @NotNull(message = "Field is required")
    @Pattern(regexp = "^(((0[1-9]|[12][0-9]|3[01])[- /.](0[13578]|1[02])|(0[1-9]|[12][0-9]|30)[- /.](0[469]|11)|(0[1-9]|1\\d|2[0-8])[- /.]02)[- /.]\\d{4}|29[- /.]02[- /.](\\d{2}(0[48]|[2468][048]|[13579][26])|([02468][048]|[1359][26])00))$", 
                message = "Format incorrect (dd/MM/yyyy)")
    private String dateIssue;  //Format dd/MM/yyyy

    @NotNull(message = "Field is required")
    @Pattern(regexp = "^(((0[1-9]|[12][0-9]|3[01])[- /.](0[13578]|1[02])|(0[1-9]|[12][0-9]|30)[- /.](0[469]|11)|(0[1-9]|1\\d|2[0-8])[- /.]02)[- /.]\\d{4}|29[- /.]02[- /.](\\d{2}(0[48]|[2468][048]|[13579][26])|([02468][048]|[1359][26])00))$", 
                message = "Format incorrect (dd/MM/yyyy)")
    private String dateExpiry;  //Format dd/MM/yyyy
}
