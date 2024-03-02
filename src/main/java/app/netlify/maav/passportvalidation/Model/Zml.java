package app.netlify.maav.passportvalidation.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zml {
    
    @NotNull
    @NotBlank
    @Size(max = 44, message = "The length must be 44")
    private String firstLine;

    @NotNull
    @NotBlank
    @Size(max = 44, message = "The length must be 44")
    private String secondLine;
}
