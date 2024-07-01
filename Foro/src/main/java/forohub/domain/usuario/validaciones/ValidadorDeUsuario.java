package forohub.domain.usuario.validaciones;

import forohub.domain.usuario.RegistroUsuarioDTO;

public interface ValidadorDeUsuario {

    public void validate(RegistroUsuarioDTO RegistroUsuarioDTO);
}