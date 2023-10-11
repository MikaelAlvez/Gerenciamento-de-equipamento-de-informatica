package br.edu.ufersa.minhacasatech.model.dao;

import br.edu.ufersa.minhacasatech.model.entity.Equipamento;
import br.edu.ufersa.minhacasatech.model.entity.Local;
import br.edu.ufersa.minhacasatech.model.entity.Responsavel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class EquipamentoDAO extends BaseDAOImp<Equipamento> {

	@Override
	public Long inserir(Equipamento eq) {
		String sql = "INSERT INTO equipamento (nome, numserie, preco, quantidade, local, responsavel) values (?, ?, ?, ?, ?, ?)";
		Long id = null;
		try {
			Connection con = BaseDAOImp.getConnection();
			PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, eq.getNome());
			ps.setString(2, eq.getNumSerie());
			ps.setDouble(3, eq.getPreco());
			ps.setInt(4, eq.getQuantidade());
			ps.setLong(5, eq.getLocal().getId());
			ps.setLong(6, eq.getResponsavel().getId());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}

			JOptionPane.showMessageDialog(null, "Equipamento cadastrado com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void deletar(Equipamento eq) {
		String sql = "DELETE FROM equipamento WHERE id = ?";
		try {
			Connection con = BaseDAOImp.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.execute();

			JOptionPane.showMessageDialog(null, "Equipamento deletado com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void alterar(Equipamento eq) {
		String sql = "UPDATE equipamento SET nome = ?, numserie = ?, preco = ?, quantidade = ?, local = ?, responsavel = ? WHERE id = ?";
		try {
			Connection con = BaseDAOImp.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, eq.getNome());
			ps.setString(2, eq.getNumSerie());
			ps.setDouble(3, eq.getPreco());
			ps.setInt(4, eq.getQuantidade());
			ps.setLong(5, eq.getLocal().getId());
			ps.setLong(6, eq.getResponsavel().getId());
			ps.setLong(7, eq.getId());
			ps.execute();

			JOptionPane.showMessageDialog(null, "Dados editado com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Equipamento buscar(Equipamento eq) {
		String sql = "SELECT * FROM equipamento WHERE id = ?";
		Equipamento equipamento = null;
		Local local = new Local();
		Responsavel responsavel = new Responsavel();
		try {
			Connection con = BaseDAOImp.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, eq.getId());
			ps.execute();
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				local.setId(rs.getLong("local"));
				responsavel.setId(rs.getLong("responsavel"));

				LocalDAO locdao = new LocalDAO();
				ResponsavelDAO respdao = new ResponsavelDAO();
				local = locdao.buscar(local);
				responsavel = respdao.buscar(responsavel);

				equipamento = new Equipamento(rs.getString("nome"), rs.getString("numserie"), rs.getDouble("preco"),
						rs.getInt("quantidade"), local, responsavel);
				equipamento.setId(rs.getLong("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return equipamento;
	}

	@Override
	public List<Equipamento> listar() {
		String sql = "SELECT * FROM equipamento";
		List<Equipamento> lista = new ArrayList<>();
		try {
			Connection con = BaseDAOImp.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Local local = new Local();
				Responsavel responsavel = new Responsavel();
				local.setId(rs.getLong("local"));
				responsavel.setId(rs.getLong("responsavel"));

				LocalDAO locdao = new LocalDAO();
				ResponsavelDAO respdao = new ResponsavelDAO();
				local = locdao.buscar(local);
				responsavel = respdao.buscar(responsavel);

				Equipamento equipamento = new Equipamento(rs.getString("nome"), rs.getString("numserie"),
						rs.getDouble("preco"), rs.getInt("quantidade"), local, responsavel);
				equipamento.setId(rs.getLong("id"));
				lista.add(equipamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
