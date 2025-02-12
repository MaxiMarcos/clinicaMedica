package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.entity.Medico;
import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.repository.MedicoRepository;
import com.clinica.gestionMedica.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService implements IMedicoService {

    @Autowired
    MedicoRepository medicoRepo;

    @Override
    public Medico crearMedico(Medico medico) {
        return medicoRepo.save(medico);
    }

    @Override
    public Medico editarMedico(Long id, Medico medico) {
        Medico nuevoMedico = this.traerMedico(id);
        nuevoMedico.setApellido(medico.getApellido());
        nuevoMedico.setNombre(medico.getNombre());
        nuevoMedico.setFecha_nacimiento(medico.getFecha_nacimiento());
        nuevoMedico.setDni(medico.getDni());
        nuevoMedico.setTelefono(medico.getTelefono());
        nuevoMedico.setDireccion(medico.getDireccion());
        nuevoMedico.setEmail(medico.getEmail());
        nuevoMedico.setEspecialidadMedica(medico.getEspecialidadMedica());
        nuevoMedico.setSueldo(medico.getSueldo());
        nuevoMedico.setListaReservas(medico.getListaReservas());

        return medicoRepo.save(nuevoMedico);

    }

    @Override
    public Medico traerMedico(Long id) {
        return medicoRepo.findById(id).orElse(null);
    }

    @Override
    public List<Medico> traerMedicos() {
        return medicoRepo.findAll();
    }

    @Override
    public void eliminarMedico(Long id) {
        medicoRepo.deleteById(id);
    }
}
