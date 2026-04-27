package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteActivo implements ValidadorDeConsultas {
    @Autowired
    private PacienteRepository repository;

    @Override
    public void validar(DatosReservaConsulta datos) {
        var pacienteEstaActivo = repository.findActivoById(datos.idPaciente());
        if(!pacienteEstaActivo){
            throw new ValidationException("Consulta no puedes ser reservada con paciente existente");
        }
    }
}
