package med.voll.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void registrar(@RequestBody @Valid DatosRegistroPaciente datos) {
        repository.save(new Paciente(datos));
    }

    @GetMapping
    public Page<DatosListaPaciente> listar(@PageableDefault(page = 0, size = 10, sort = { "nombre" }) Pageable paginacion) {
        return repository.findAllByActivoTrue(paginacion).map(DatosListaPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DatosActualizacionPaciente datos) {
        var paciente = repository.getReferenceById(datos.id());
        paciente.actualizarInformacion(datos);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void eliminar(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.desactivar();
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity detallar(@PathVariable Long id){
        var paciente = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        return ResponseEntity.ok(new DatosDetallePaciente(paciente));
    }
}
