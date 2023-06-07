package Entidades;
import java.time.LocalDate;

public class Turno {
	private int id;
	private String especialidad;
	private int idMed;
	private int estado;
	private int dni;
	private LocalDate fachaInicio;
	private String hrInicio;
	private String hrFin;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	public int getIdMed() {
		return idMed;
	}
	public void setIdMed(int idMed) {
		this.idMed = idMed;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	public LocalDate getFachaInicio() {
		return fachaInicio;
	}
	public void setFachaInicio(LocalDate fachaInicio) {
		this.fachaInicio = fachaInicio;
	}
	public String getHrInicio() {
		return hrInicio;
	}
	public void setHrInicio(String hrInicio) {
		this.hrInicio = hrInicio;
	}
	public String getHrFin() {
		return hrFin;
	}
	public void setHrFin(String hrFin) {
		this.hrFin = hrFin;
	}
}
