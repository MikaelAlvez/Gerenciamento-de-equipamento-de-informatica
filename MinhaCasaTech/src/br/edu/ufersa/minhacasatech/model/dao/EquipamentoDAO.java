package br.edu.ufersa.minhacasatech.model.dao;

import br.edu.ufersa.minhacasatech.exception.InvalidInsertException;
import br.edu.ufersa.minhacasatech.model.entity.Equipamento;
import br.edu.ufersa.minhacasatech.model.entity.Local;
import br.edu.ufersa.minhacasatech.model.entity.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EquipamentoDAO extends BaseDAOImp<Equipamento> {

    @Override
    public Long inserir(Equipamento eq) {
	String sql = "INSERT INTO equipamento (nome, serial, preco, quantidade, local, responsavel) values (?, ?, ?, ?, ?, ?)";
	Long id = null;
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	    ps.setString(1, eq.getNome());
	    ps.setString(2, eq.getSerial());
	    ps.setDouble(3, eq.getPreco());
	    ps.setInt(4, eq.getQuantidade());
	    ps.setLong(5, eq.getLocal().getId());
	    ps.setLong(6, eq.getResponsavel().getId());
	    ps.execute();
	    ResultSet rs = ps.getGeneratedKeys();
	    if (rs.next()) {
		id = rs.getLong("id");
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return id;
    }

    @Override
    public void deletar(Equipamento eq){
	String sql = "DELETE FROM equipamento WHERE id = ?";
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.execute();
	} catch (SQLException ex) {
	    Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
    }

    @Override
    public void alterar(Equipamento eq){
	String sql = "UPDATE equipamento SET nome = ?, serial = ?, preco = ?, quantidade = ?, local = ?, responsavel = ? WHERE id = ?";
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, eq.getNome());
	    ps.setString(2, eq.getSerial());
	    ps.setDouble(3, eq.getPreco());
	    ps.setInt(4, eq.getQuantidade());
	    ps.setLong(5, eq.getLocal().getId());
	    ps.setLong(6, eq.getResponsavel().getId());
	    ps.setLong(7, eq.getId());
	    ps.execute();
	} catch (SQLException ex) {
	    Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
    }
    
    @Override
    public Equipamento buscarPorId(Equipamento eq){
	String sql = "SELECT * FROM equipamento WHERE id = ?";
	Equipamento equipamento = null;
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setLong(1, eq.getId());
	    ps.execute();
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
                Local local = new Local();
		LocalDAO locdao = new LocalDAO();
                local.setId(rs.getLong("local"));
                local = locdao.buscarPorId(local);
		
                Funcionario responsavel = new Funcionario();
                FuncionarioDAO funcdao = new FuncionarioDAO();
                responsavel.setId(rs.getLong("responsavel"));
		responsavel = funcdao.buscarPorId(responsavel);
		
		equipamento = new Equipamento(rs.getString("nome"), rs.getString("serial"), rs.getDouble("preco"), rs.getInt("quantidade"));
                equipamento.setLocal(local);
                equipamento.setResponsavel(responsavel);
		equipamento.setId(rs.getLong("id"));
                equipamento.setDataCadastro(rs.getDate("data_cadastro"));
	    }
	} catch (SQLException | InvalidInsertException ex) {
	    Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return equipamento;
    }
    
    public Equipamento buscarPorSerial(Equipamento eq){
	String sql = "SELECT * FROM equipamento WHERE serial = ?";
	Equipamento equipamento = null;
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, eq.getSerial());
	    ps.execute();
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
                Local local = new Local();
		LocalDAO locdao = new LocalDAO();
                local.setId(rs.getLong("local"));
                local = locdao.buscarPorId(local);
		
                Funcionario responsavel = new Funcionario();
                FuncionarioDAO funcdao = new FuncionarioDAO();
                responsavel.setId(rs.getLong("responsavel"));
		responsavel = funcdao.buscarPorId(responsavel);
		
		equipamento = new Equipamento(rs.getString("nome"), rs.getString("serial"), rs.getDouble("preco"), rs.getInt("quantidade"));
                equipamento.setLocal(local);
                equipamento.setResponsavel(responsavel);
		equipamento.setId(rs.getLong("id"));
                equipamento.setDataCadastro(rs.getDate("data_cadastro"));
	    }
	} catch (SQLException | InvalidInsertException ex) {
	    Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return equipamento;
    }
    
    @Override
    public List<Equipamento> listar(){
	String sql = "SELECT * FROM equipamento ORDER BY id";
	List<Equipamento> lista = new ArrayList<>();
	try {
	    Connection con = BaseDAOImp.getConnection();
	    PreparedStatement ps = con.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();
	    while (rs.next()) {
                Local local = new Local();
		LocalDAO locdao = new LocalDAO();
		local.setId(rs.getLong("local"));
                local = locdao.buscarPorId(local);
                
		Funcionario responsavel = new Funcionario();
                FuncionarioDAO funcdao = new FuncionarioDAO();
                responsavel.setId(rs.getLong("responsavel"));
		responsavel = funcdao.buscarPorId(responsavel);
		
		Equipamento equipamento = new Equipamento(rs.getString("nome"), rs.getString("serial"), rs.getDouble("preco"), rs.getInt("quantidade"));
                equipamento.setLocal(local);
                equipamento.setResponsavel(responsavel);
		equipamento.setId(rs.getLong("id"));
                equipamento.setDataCadastro(rs.getDate("data_cadastro"));
                
		lista.add(equipamento);
	    }
	} catch (SQLException | InvalidInsertException ex) {
	    Logger.getLogger(EquipamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
            BaseDAOImp.closeConnection();
        }
	return lista;
    }
}