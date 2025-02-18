package com.clinica.gestionMedica.service.impl;

import com.clinica.gestionMedica.dto.PrestacionDto;
import com.clinica.gestionMedica.dto.ReservaDto;
import com.clinica.gestionMedica.entity.Prestacion;
import com.clinica.gestionMedica.entity.Reserva;
import com.clinica.gestionMedica.enums.PresenciaEnum;
import com.clinica.gestionMedica.enums.PrestacionEstadoEnum;
import com.clinica.gestionMedica.enums.ReservaEstadoEnum;
import com.clinica.gestionMedica.mapper.ReservaMapper;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.repository.PrestacionRepository;
import com.clinica.gestionMedica.repository.ReservaRepository;
import com.clinica.gestionMedica.service.IReservaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {

    private final ReservaRepository reservaRepo;
    private final PrestacionRepository prestacionRepo;
    private final ReservaMapper reservaMapper;

    public ReservaService(ReservaRepository reservaRepo, PacienteService pacienteService, PacienteRepository pacienteRepo, PrestacionRepository prestacionRepo, ReservaMapper reservaMapper) {
        this.reservaRepo = reservaRepo;
        this.prestacionRepo = prestacionRepo;
        this.reservaMapper = reservaMapper;
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {

        //Paciente paciente = pacienteRepo.findByDni(reserva.getPaciente().getDni());

        return reservaRepo.save(reserva);
    }

    public ReservaDto agregarPrestacionEnReserva(Long pacienteId, Long prestacionId) {

        // Busco si un paciente tiene reserva asignada y en Pendiente
        // Busco una prestacion y si está disponible (es decir, no agregada en otra reserva) la agrego
        // agrego la prestacion a la reserva
        // cambio la prestacion a NO DISPONIBLE, reserva a PAGADO y guardo

        Reserva reserva = reservaRepo.findByPaciente_IdAndEstadoPago(pacienteId, ReservaEstadoEnum.PENDIENTE);
        Prestacion prestacion = prestacionRepo.findByIdAndEstado(prestacionId, PrestacionEstadoEnum.DISPONIBLE);

        reserva.setEstadoPago(ReservaEstadoEnum.PAGADO);
        prestacion.setReserva(reserva);
        prestacion.setEstado(PrestacionEstadoEnum.NODISPONIBLE);

        ReservaDto reservaDto = reservaMapper.conversionADto(reserva);

        prestacionRepo.save(prestacion);
        reservaRepo.save(reserva);
        return reservaDto;
    }

    @Override
    public Reserva editarReserva(Long id, Reserva reserva) {
        Reserva nuevaReserva = this.traerReserva(id);
        nuevaReserva.setEstadoPago(reserva.getEstadoPago());
        nuevaReserva.setPaciente(reserva.getPaciente());
        nuevaReserva.setPrecioTotal(reserva.getPrecioTotal());
        nuevaReserva.setEstadoPresencia(reserva.getEstadoPresencia());
        nuevaReserva.setPrestaciones(reserva.getPrestaciones());

        return reservaRepo.save(nuevaReserva);
    }

    @Override
    public Reserva traerReserva(Long id) {
        return reservaRepo.findById(id).orElse(null);
    }

   // @Override
   // public List<ReservaDto> traerHistorialPaciente(String dni) {

     //   List<Reserva> historial = reservaRepo.findByPaciente_Dni(dni);
     //   return reservaMapper.conversionAListaDto(historial);
   // }

    @Override
    public List<Reserva> traerReservas() {
        return reservaRepo.findAll();
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepo.deleteById(id);
    }
}
