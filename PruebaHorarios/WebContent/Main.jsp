<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="Diminio.Datos" %>
<%@page import="java.util.ArrayList"%>
<%@ page import="Entidades.Horarios" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.time.LocalTime" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%Datos dato = new Datos();
	  ArrayList<Horarios> horario = dato.cargarHorariosMed();
	%>
	<table border="2">
				<tr>
					<td><b>NOMBRE MEDICO</b></td>
					<td><b>DIA DE ATENCION</b></td>
					<td><b>HORARIO INICIO</b></td>
					<td><b>HORARIO FIN</b></td>
		       </tr>
		       <%
		         for (Horarios hr : horario) {
		         %>
		         <tr>
		           <td><%= hr.getNombreMed()%></td>
		           <td><%= hr.getDia() %></td>
		           <td><%= hr.getHrInicio() %></td>
		           <td><%= hr.getHrFin()%></td>
		         </tr>
		       <% } %>
	</table>
	
	<form>
		<input type="date" name="fecha">
		<input type="time" name="hora">
		<input type="submit" name="btnConsultar" value="Agregar">
	</form>
	<br>
	
	<%if(request.getParameter("btnConsultar")!=null){
		
		//para obtener el dia selecciondo
		String fechaSeleccionada = request.getParameter("fecha");
		//convierto de strin a LocalDate
	    LocalDate fecha = LocalDate.parse(fechaSeleccionada);
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES"));
	    //Lo formateo para tener solo el dia seleccionado
	    String diaSemana = fecha.format(formatter);
	    ////////
	    
	    //para ver que la fecha sea mayor o igual a la fecha de hoy
	    LocalDate fechaActual = LocalDate.now();
	    LocalDate fechaSeleccionadaObj = LocalDate.parse(fechaSeleccionada);
	    //para que el dia seleccionado sea mayor o igual al dia de hoy
	    boolean esFechaMayorOigual = fechaSeleccionadaObj.isAfter(fechaActual) || fechaSeleccionadaObj.isEqual(fechaActual);
	    ///////
	    
	    
	    //para ver si es un dia que trabaja el medico
		boolean diaQueTranaja = dato.verDiaDelMedico(diaSemana);
	    ///////
	    
	    
	    //para ver el rango del horario
	    String hora = request.getParameter("hora");
	    LocalTime horaSeleccionadaObj = LocalTime.parse(hora);
	    //para entre los horarios del medico con respecto al dia seleccionado
	    String horaIncial = dato.verDiaConHorarioInicial(diaSemana);
	    String horaFinal = (String)dato.verDiaConHorarioFinal(diaSemana);
	    
	    //paso la hora de string a LocalTime
	    LocalTime horaInicialObj = LocalTime.parse(horaIncial);
	    LocalTime horaFinalObj = LocalTime.parse(horaFinal);
	    
	    //Esto es para ver si el horario seleccionado esta dentro del rango que trabaja el medico
	    boolean estaEnRango = horaSeleccionadaObj.isAfter(horaInicialObj) || horaSeleccionadaObj.equals(horaInicialObj) && horaSeleccionadaObj.isBefore(horaFinalObj) || horaSeleccionadaObj.equals(horaFinalObj);
	    ///////
	    
		//checkeamos los boolean
	    if(esFechaMayorOigual && diaQueTranaja && estaEnRango){
	    	
	    	//para ver la disponibilidad del horario seleccionado del dia 
	    	boolean siHayHorario = dato.verDisponibilidadDelHorarioSeleccionado(fecha,horaSeleccionadaObj);
	    	
	    	if(siHayHorario){
	    		dato.cargarTurno(fechaSeleccionada,hora,horaSeleccionadaObj);
	    		%><p>se cargo we</p><%
	    	}
	    	else{
	    		%><p>Horario ocupado</p><%
	    	}
	    }
	    else{
	    	%><p>no se puede agregar</p><%
	    }
		 
		
	} %>
</body>
</html>