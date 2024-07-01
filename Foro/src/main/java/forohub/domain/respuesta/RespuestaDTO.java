package forohub.domain.respuesta;

import forohub.domain.topico.Topico;
import forohub.domain.usuario.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RespuestaDTO(
        @NotBlank
        String solution,
        @NotNull
        @Valid
        Long usuario_Id,
        @NotNull
        @Valid
        Long topico_Id,
        LocalDateTime creationDate) {
}