package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.MedicoRequestDto;
import com.clinica.gestionMedica.dto.MedicoResponseDto;
import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.enums.ObraSocialEnum;
import com.clinica.gestionMedica.enums.TipoPrestacion;
import com.clinica.gestionMedica.excepciones.medico.FiltroInvalidoException;
import com.clinica.gestionMedica.excepciones.medico.MedicoNoEncontradoException;
import com.clinica.gestionMedica.excepciones.medico.MedicoYaExisteException;
import com.clinica.gestionMedica.excepciones.medico.MedicosNoEncontradosException;
import com.clinica.gestionMedica.excepciones.paciente.PacienteNoEncontradoException;
import com.clinica.gestionMedica.excepciones.prestacion.PrestacionNoCubiertaException;
import com.clinica.gestionMedica.mapper.MedicoMapper;
import com.clinica.gestionMedica.repository.MedicoRepository;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.service.IMedicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedicoService implements IMedicoService {

    private final MedicoRepository medicoRepo;
    private final MedicoMapper medicoMapper;
    private final PacienteRepository pacienteRepository;

    @Override
    public MedicoResponseDto crearMedico(MedicoRequestDto medicoRequest) {

        validarMedicoExistente(medicoRequest.getDni());

        Medico medico = medicoMapper.toEntity(medicoRequest);
        medicoRepo.save(medico);

        return medicoMapper.toResponse(medico);
    }

    @Override
    public MedicoResponseDto editarMedico(Long id, MedicoRequestDto medicoRequest) {
        Medico medico = medicoRepo.findById(id)
                .orElseThrow(MedicoNoEncontradoException::new);

        actualizarMedico(medico, medicoRequest);

        medicoRepo.save(medico);
        return medicoMapper.toResponse(medico);
    }

    @Override
    public MedicoResponseDto traerMedico(Long id) {
        Medico medico = medicoRepo.findById(id)
                .orElseThrow(MedicoNoEncontradoException::new);
        return medicoMapper.toResponse(medico);
    }

    @Override
    public List<MedicoResponseDto> traerMedicos() {
        return medicoMapper.toResponseList(medicoRepo.findAll());
    }

    @Override
    public void eliminarMedico(Long id) {
        Medico medico = medicoRepo.findById(id)
                .orElseThrow(MedicoNoEncontradoException::new);
        medicoRepo.deleteById(medico.getId());
    }

    @Override
    public List<MedicoResponseDto> filtrarMedicos(String apellido, String especialidad){

        List<Medico> medicos;

        if (especialidad != null) {
            try {
            TipoPrestacion especialidadEnum = TipoPrestacion.valueOf(especialidad.toUpperCase());
            medicos = medicoRepo.findByEspecializacion(especialidadEnum); }
            catch (IllegalArgumentException e) {
                throw new FiltroInvalidoException();
            }
        } else if (apellido != null)  {
            medicos = medicoRepo.findByApellido(apellido);
        } else {
            medicos = medicoRepo.findAll();
        }

        if (medicos.isEmpty()) {
            throw new MedicosNoEncontradosException(); // puedo agregar dif msje según si buscó por especialidad o apellido
        }

        return medicoMapper.toResponseList(medicos);
    };

    @Override
    public List<MedicoResponseDto> medicosPorEspecialidad (Long pacienteId, TipoPrestacion especialidad){

        Paciente paciente = pacienteRepository.findById(pacienteId).orElseThrow(PacienteNoEncontradoException::new);

        boolean cubierto = false;

        if (especialidad == null) {
            throw new FiltroInvalidoException();
        }

        if (paciente.getObraSocial() == ObraSocialEnum.IOSFA && (especialidad == TipoPrestacion.CONSULTA_GENERAL ||
                especialidad == TipoPrestacion.ECOGRAFIA)) {
            cubierto = true;
        } else if (paciente.getObraSocial() == ObraSocialEnum.OSDE) {
            cubierto = true;
        } else if (paciente.getObraSocial() == ObraSocialEnum.NINGUNA &&
                especialidad == TipoPrestacion.CONSULTA_GENERAL) {
            cubierto = true;
        }

        if (cubierto) {
            List<Medico> medicos = medicoRepo.findByEspecializacion(especialidad);

            return medicoMapper.toResponseList(medicos);
        } else {
            throw new PrestacionNoCubiertaException();
        }
    };


    private void validarMedicoExistente(String dni){

        Medico medicoExiste = medicoRepo.findByDni(dni);
        if (medicoExiste != null) {
            throw new MedicoYaExisteException("Ya existe un médico creado con este DNI.");
        }
    }

    private void actualizarMedico(Medico medico, MedicoRequestDto medicoRequest){


        if (medicoRequest.getApellido() != null) medico.setApellido(medicoRequest.getApellido());
        if (medicoRequest.getNombre() != null) medico.setNombre(medicoRequest.getNombre());
        if (medicoRequest.getFecha_nacimiento() != null) medico.setFecha_nacimiento(medicoRequest.getFecha_nacimiento());
        if (medicoRequest.getDni() != null) medico.setDni(medicoRequest.getDni());
        if (medicoRequest.getTelefono() != null) medico.setTelefono(medicoRequest.getTelefono());
        if (medicoRequest.getDireccion() != null) medico.setDireccion(medicoRequest.getDireccion());
        if (medicoRequest.getEmail() != null) medico.setEmail(medicoRequest.getEmail());
        if (medicoRequest.getSueldo() != null) medico.setSueldo(medicoRequest.getSueldo());
        if (medicoRequest.getEspecializacion() != null) medico.setEspecializacion(medicoRequest.getEspecializacion());
    }

}
