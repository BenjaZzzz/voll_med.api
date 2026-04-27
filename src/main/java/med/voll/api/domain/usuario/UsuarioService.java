package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(DatosAutenticacionUsuario datos){
        System.out.println("Contrasenia: " + datos.clave());
        String passwordHasheado = passwordEncoder.encode(datos.clave());

        Usuario nuevoUsuario = new Usuario(datos.login(), passwordHasheado);
        repository.save(nuevoUsuario);
    }
}
