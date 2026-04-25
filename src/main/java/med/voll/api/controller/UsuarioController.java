package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DatosAutenticacion;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosAutenticacion datos, UriComponentsBuilder uriBuilder) {
        usuarioService.registrarUsuario(datos);
        System.out.println("Registrado usuario: " + datos.login() + "\n" + datos.contrasena());
        var uri = uriBuilder.path("/usuarios/{login}").buildAndExpand(datos.login()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
