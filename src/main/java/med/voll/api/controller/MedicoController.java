package med.voll.api.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    // Inyeccion de dependencias - estudiar
    @Autowired
    private MedicoRepository repository;

    // Investigar
    @Transactional
    // @RequestBody es para leer los datos del body
    // HTTP 201 - Investigar
    @PostMapping
    public ResponseEntity regitrar(@RequestBody @Valid datosRegistroMedico datos, UriComponentsBuilder uriComponentsBuilder) {
        var medico = new Medico(datos);
        repository.save(medico);

        // Investigar y estudiar
        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        // Investigar
        return ResponseEntity.created(uri).body(new DatosDetalleMedico(medico));
    }

    // Investigar Page / Pageable
    // Investigar Response entity
    @GetMapping
    public ResponseEntity <Page<DatosListaMedico>> listar(@PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion){
        var page = repository.findAllByActivoTrue(paginacion).map(DatosListaMedico::new);
        return ResponseEntity.ok(page);
    }

    // investigar
    @Transactional
    @PutMapping
    public ResponseEntity actualizar(@RequestBody @Valid datosActulizacionMedico datos){
        var medico =  repository.getReferenceById(datos.id());
        medico.actulizarInformaciones(datos);

        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }

    // Investigar
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.eliminar();

        return ResponseEntity.noContent().build();
    }


    @Transactional
    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){
        var medico = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Medico no encontrado"));

        return ResponseEntity.ok(new DatosDetalleMedico(medico));
    }
}
