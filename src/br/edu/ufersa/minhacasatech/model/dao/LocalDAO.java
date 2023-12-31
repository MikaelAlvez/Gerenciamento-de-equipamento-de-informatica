package br.edu.ufersa.minhacasatech.model.dao;

import br.edu.ufersa.minhacasatech.exception.InvalidInsertException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.edu.ufersa.minhacasatech.model.entity.Local;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalDAO extends BaseDAOImp<Local> {
    
    @Override
    public Long inserir(Local loc) {
	String sql = "INSERT INTO local (nome, compartimento) values (?, ?)";
	Long id = null;
	try {
            Connection con = BaseDAOImp.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, loc.getNome());
            ps.setString(2, loc.getCompartimento());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong("id");
            }
	} catch (SQLException ex) {
	    Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return id;
    }
    
    @Override
    public void deletar(Local loc){
	String sql = "DELETE FROM local WHERE id = ?";
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setLong(1, loc.getId());
	    ps.execute();
	} catch (SQLException ex) {
	    Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
    }
    
    @Override
    public void alterar(Local loc){
	String sql = "UPDATE local SET nome = ?, compartimento = ? WHERE id = ?";
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, loc.getNome());
	    ps.setString(2, loc.getCompartimento());
	    ps.setLong(3, loc.getId());
	    ps.execute();
	} catch (SQLException ex) {
	    Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
    }
    
    @Override
    public Local buscarPorId(Local loc){
	String sql = "SELECT * FROM local WHERE id = ?";
	Local local = null;
	try {
            Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setLong(1, loc.getId());
	    ps.execute();
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
		local = new Local(rs.getString("nome"), rs.getString("compartimento"));
		local.setId(rs.getLong("id"));
                local.setDataCadastro(rs.getDate("data_cadastro"));
	    }
	} catch (SQLException | InvalidInsertException ex) {
	    Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return local;
    }
    
    public Local buscarPorData(Local loc, Date inicio, Date fim){
	String sql = "SELECT * FROM local WHERE data_cadastro BETWEEN ? and ?";
	Local local = null;
	try {
            Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setDate(1, inicio);
            ps.setDate(2, fim);
	    ps.execute();
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
		local = new Local(rs.getString("nome"), rs.getString("compartimento"));
		local.setId(rs.getLong("id"));
                local.setDataCadastro(rs.getDate("data_cadastro"));
	    }
	} catch (SQLException | InvalidInsertException ex) {
	    Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return local;
    }
    
    public ArrayList<String> listarLocais() {
        String sql = "SELECT nome, compartimento FROM local ORDER BY id";
	ArrayList<String> nomes = new ArrayList<>();
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
                nomes.add(rs.getString("nome") + ", " + rs.getString("compartimento"));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return nomes;
    }
    
    @Override
    public List<Local> listar(){
	String sql = "SELECT * FROM local ORDER BY id";
	List<Local> lista = new ArrayList<>();
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
		Local loc = new Local(rs.getString("nome"), rs.getString("compartimento"));
		loc.setId(rs.getLong("id"));
		loc.setDataCadastro(rs.getDate("data_cadastro"));
		lista.add(loc);
	    }
	} catch (SQLException | InvalidInsertException ex) {
	    Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return lista;
    }
    
    public Local existeLocal(Local loc) {
	String sql = "SELECT * FROM local WHERE nome ILIKE ? AND compartimento ILIKE ?";
	Local local = null;
	try {
	    Connection con = BaseDAOImp.getConnection();
	    if (conn != null) {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, loc.getNome());
                ps.setString(2, loc.getCompartimento());
                ps.execute();
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    local = new Local(rs.getString("nome"), rs.getString("compartimento"));
                    local.setId(rs.getLong("id"));
                    local.setDataCadastro(rs.getDate("data_cadastro"));
                }
            }
	} catch (SQLException | InvalidInsertException ex) {	
	    Logger.getLogger(LocalDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return local;
    }
}
