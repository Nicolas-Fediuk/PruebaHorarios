package Diminio;
import java.sql.ResultSet;
import java.util.ArrayList;

import Entidades.Turno;
import Entidades.Horarios;
import java.util.Locale;
import java.time.LocalDate;
import java.time.LocalTime;


public class Datos {
	
	private Conexion cn;
	
	public ArrayList<Horarios> cargarHorariosMed() {
		
		ArrayList<Horarios> list = new ArrayList<Horarios>();
		cn = new Conexion();
		cn.Open();
		
		try {
			ResultSet rs = cn.query("SELECT NOMBRE_MED, DIA_DIAXMED,HRINICO_HRXMED AS HORAINICIO, HRFIN_HRXMED AS HORAFIN FROM MEDICOS JOIN DIASXMEDICOS ON MEDICOS.ID_MED = DIASXMEDICOS.ID_DIAXMED JOIN HORARIOMEDICOS ON MEDICOS.ID_MED = IDMED_HRXMED");
			
			while (rs.next()) {
				
				Horarios horario = new Horarios();

				horario.setNombreMed(rs.getString(1));
				horario.setDia(rs.getString(2));
				horario.setHrInicio(rs.getString(3));
				horario.setHrFin(rs.getString(4));

				list.add(horario);

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		
		return list;
	}
	
	public boolean verDiaDelMedico(String dia) {
		
		cn = new Conexion();
		cn.Open();
		
		boolean diaOk = false;
		
		try {
			ResultSet rs = cn.query("SELECT NOMBRE_MED, DIA_DIAXMED,HRINICO_HRXMED AS HORAINICIO, HRFIN_HRXMED AS HORAFIN FROM MEDICOS JOIN DIASXMEDICOS ON MEDICOS.ID_MED = DIASXMEDICOS.ID_DIAXMED JOIN HORARIOMEDICOS ON MEDICOS.ID_MED = IDMED_HRXMED where dia_diaxmed = '"+dia+"'");
			
			while (rs.next()) {
				
				diaOk = true;

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		
		return diaOk;
	}
	
	public String verDiaConHorarioInicial(String dia) {
		cn = new Conexion();
		cn.Open();
		
		String hora = "";
		
		try {
			ResultSet rs = cn.query("SELECT HRINICO_HRXMED AS HORAINICIO FROM MEDICOS JOIN DIASXMEDICOS ON MEDICOS.ID_MED = DIASXMEDICOS.ID_DIAXMED JOIN HORARIOMEDICOS ON MEDICOS.ID_MED = IDMED_HRXMED where dia_diaxmed = '"+dia+"'");
			
			while (rs.next()) {
				
				hora = rs.getString(1);

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		
		return hora;
	}
	
	public String verDiaConHorarioFinal(String dia) {
		cn = new Conexion();
		cn.Open();
		
		String hora = "";
		
		try {
			ResultSet rs = cn.query("SELECT HRFIN_HRXMED AS HORAFIN FROM MEDICOS JOIN DIASXMEDICOS ON MEDICOS.ID_MED = DIASXMEDICOS.ID_DIAXMED JOIN HORARIOMEDICOS ON MEDICOS.ID_MED = IDMED_HRXMED where dia_diaxmed = '"+dia+"'");
			
			while (rs.next()) {
				
				hora = rs.getString(1);

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		
		return hora;
	}
	
	public boolean verDisponibilidadDelHorarioSeleccionado(LocalDate fecha,LocalTime horaSelect) {
		
		boolean hayHorario = true;
		
		cn = new Conexion();
		cn.Open();
		
		try {
			ResultSet rs = cn.query("select HRINICIO_TURNO from TURNOS WHERE FECALT_TURNO = '"+fecha+"' order by HRINICIO_TURNO ASC");
			
			while (rs.next()) {
				
				String horaInicioCargada = rs.getString(1);
				LocalTime horaInicioCargadaObj = LocalTime.parse(horaInicioCargada);
				
				LocalTime horaInicioCargadaObjMenor = horaInicioCargadaObj.minusHours(1);
				LocalTime horaInicioCargadaObjMayor = horaInicioCargadaObj.plusHours(1);
				
				if(horaSelect.isBefore(horaInicioCargadaObjMenor) || horaSelect.equals(horaInicioCargadaObjMenor) || horaSelect.isAfter(horaInicioCargadaObjMayor) || horaSelect.equals(horaInicioCargadaObjMayor)) {
					hayHorario = true;
				}
				else {
					hayHorario = false;
					break;
				}
				/*if(horaSelect.isAfter(horaInicioCargadaObjMayor)) {
					hayHorario = true;
				}*/
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		finally {
			cn.close();
		}
		
		return hayHorario;
		
	}
	
	public void cargarTurno(String fechaSeleccionada,String hora,LocalTime horaSeleccionadaObj) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String newhoraSeleccionadaObj = horaSeleccionadaObj.plusHours(1).toString();
		
		
		try
		{	
			cn.Open();
			
			String query = "insert into TURNOS(IDESP_TURNO,DNIMED_TURNO,IDESTADO_TURNO,DNIPAC_TURNO,FECALT_TURNO,HRINICIO_TURNO,HRFIN_TURNO) value('odontologo',12345,2,67898,'"+fechaSeleccionada+"','"+hora+"','"+newhoraSeleccionadaObj+"')";
		    cn.execute(query);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			cn.close();
		}
	}
}
