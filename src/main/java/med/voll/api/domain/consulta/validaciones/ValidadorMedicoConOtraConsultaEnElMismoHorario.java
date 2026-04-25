package med.voll.api.domain.consulta.validaciones;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosReservaConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConOtraConsultaEnElMismoHorario implements ValidadorDeConsultas{

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DatosReservaConsulta datos) {
        var medicoTieneOtraConsultaEnElMismoHorario = repository.existsByMedicoIdAndFecha(datos.idMedico(), datos.fecha());
        if (medicoTieneOtraConsultaEnElMismoHorario){
            throw new ValidationException("Medico ya tiene otra consulta en esta misma fecha y hora");
        }
    }
}
