package net.jodaci.empleosApp.repository;

import net.jodaci.empleosApp.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

}
