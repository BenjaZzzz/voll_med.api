package med.voll.api.domain.consulta;

import jakarta.validation.Valid;
import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaDeConsultas {
    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorDeConsultas> validadores;

    public DatosDetalleConsulta reservar(DatosReservaConsulta datos) {

        if (!pacienteRepository.existsById(datos.idPaciente())) {
            throw new ValidationException("No existe en paciente con el id informado");
        }

        if (datos.idMedico() != null && !medicoRepository.existsById(datos.idMedico())) {
            throw new ValidationException("No existe un medico con el id informado");
        }

        // Patron Strategy - investigar
        // Principios SOLID - Investigar
        // OPEN CLOSE PRINCIPAL - Investigar
        // PRINCIPIO DE INSERCION DE DEPENDENCIAS - investigar
        /**
            Single Responsibility Principle (Princípio de Responsabilidad Única)
            Open-Closed Principle (Princípio Abierto-Cerrado)
            Liskov Substitution Principle (Princípio de Substitución de Liskov)
            Interface Segregation Principle (Princípio de Segregación de Interface)
            Dependency Inversion Principle (Princípio de Inversión de Dependencia)
        * */
        validadores.forEach(v -> v.validar(datos));

        var medico = elegirMedico(datos);

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();

        var consulta = new Consulta(null, medico, paciente, datos.fecha(), null);

        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    public Medico elegirMedico(DatosReservaConsulta datos) {
        if (datos.idMedico() != null) {
            return medicoRepository.findById(datos.idMedico()).get();
        }

        if (datos.especialidad() == null) {
            throw new ValidationException("Es necesario elegir una especialidad cuando no se elige un medico");
        }

        return medicoRepository.elegirMedicoAleatorioDisponibleEnLaFecha(datos.especialidad(), datos.fecha());
    }

    public void cancelar(@Valid DatosCancelamientoConsulta datos) {
        if (!consultaRepository.existsById(datos.idConsulta())) {
            throw new ValidationException("Id de la consulta informada no existe");
        }
        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
