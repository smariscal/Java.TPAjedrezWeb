package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entidades.Ficha;
import entidades.Jugador;
import entidades.Partida;

public class CatalogoPartidas {

	// devuelve partidas anteriores de dos jugadores
	public Partida getPartidaAnterior(Jugador j1, Jugador j2) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = FactoriaConexiones.getInstancia().getConn().prepareStatement("select * from partidas "
				+ "where dni1=? and dni2=? and turno <> 3");
		ps.setInt(1, j1.getDni());
		ps.setInt(2, j2.getDni());
		ResultSet rs = ps.executeQuery();
		
		boolean empty = true;
		Partida p = new Partida();
		
		while (rs.next()){
			p.setJ1(j1);
			p.setJ2(j2);
			p.setNroPartida(rs.getInt("nropartida"));
			p.setTurno(rs.getShort("turno"));
			empty = false;
		}
		
		rs.close();
		ps.close();
		FactoriaConexiones.getInstancia().releaseConn();
		
		if (empty){
			return null;
		}
		else {
			return p;
		}
		
	}

	// borra una partida anterior de dos jugadores
	public void borrarPartida(Partida p) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = FactoriaConexiones.getInstancia().getConn().prepareStatement("delete from partidas where nropartida=?");
		ps.setInt(1, p.getNroPartida());
		ps.execute();
		ps.close();
		FactoriaConexiones.getInstancia().releaseConn();
	}

	
	// crea una partida nueva de dos jugadores
	public Partida nuevaPartida(Jugador j1, Jugador j2) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = FactoriaConexiones.getInstancia().getConn().prepareStatement("insert into partidas(dni1,dni2,turno) "
				+ "values (?,?,1)", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, j1.getDni());
		ps.setInt(2, j2.getDni());
		ps.execute();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		Partida p = new Partida();
		p.setNroPartida(rs.getInt(1));
		p.setJ1(j1);
		p.setJ2(j2);
		p.setTurno(1);
		
		rs.close();
		ps.close();
		FactoriaConexiones.getInstancia().releaseConn();
		
		return p;
		
	}

	
	public void updatePartida(Partida p) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = FactoriaConexiones.getInstancia().getConn().prepareStatement("update partidas "+
		"set turno=? where nropartida=? and dni1=? and dni2=?");
		ps.setInt(1, p.getTurno());
		ps.setInt(2, p.getNroPartida());
		ps.setInt(3, p.getJ1().getDni());
		ps.setInt(4, p.getJ2().getDni());
		ps.executeUpdate();
		ps.close();
		FactoriaConexiones.getInstancia().releaseConn();
	}

	
}
