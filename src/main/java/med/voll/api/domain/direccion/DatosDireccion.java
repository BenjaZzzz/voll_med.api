package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

//Bean Validation(Estudiar)
public record DatosDireccion(
        @NotBlank String calle,
        String numero,
        String complemento,
        @NotBlank String barrio,
        @NotBlank String ciudad,
        @NotBlank @Pattern(regexp = "\\d{4,8}") String codigo_postal,
        @NotBlank String estado
) {}

