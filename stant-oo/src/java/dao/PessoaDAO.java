package dao;

import util.ConexaoBD;
import model.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Pessoa. Responsável por todas as
 * operações de CRUD no banco de dados.
 */
public class PessoaDAO {

    /**
     * Insere uma nova pessoa no banco de dados.
     *
     * @param pessoa A pessoa a ser inserida
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean inserir(Pessoa pessoa) {
        String sql = "INSERT INTO Pessoa (nome, email, telefone) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getEmail());
            ps.setString(3, pessoa.getTelefone());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        pessoa.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return false;
    }

    /**
     * Busca uma pessoa pelo ID.
     *
     * @param id O ID da pessoa
     * @return A pessoa encontrada ou null se não existir
     */
    public Pessoa buscarPorId(int id) {
        String sql = "SELECT * FROM Pessoa WHERE id = ?";
        Pessoa pessoa = null;

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pessoa = new Pessoa();
                    pessoa.setId(rs.getInt("id"));
                    pessoa.setNome(rs.getString("nome"));
                    pessoa.setEmail(rs.getString("email"));
                    pessoa.setTelefone(rs.getString("telefone"));
                    pessoa.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return pessoa;
    }

    /**
     * Lista todas as pessoas cadastradas.
     *
     * @return Lista de pessoas
     */
    public List<Pessoa> listarTodos() {
        String sql = "SELECT * FROM Pessoa";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conexao = ConexaoBD.getConexao(); Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setEmail(rs.getString("email"));
                pessoa.setTelefone(rs.getString("telefone"));
                pessoa.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());

                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return pessoas;
    }

    /**
     * Atualiza os dados de uma pessoa.
     *
     * @param pessoa A pessoa com os dados atualizados
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean atualizar(Pessoa pessoa) {
        String sql = "UPDATE Pessoa SET nome = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getEmail());
            ps.setString(3, pessoa.getTelefone());
            ps.setInt(4, pessoa.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return false;
    }

    /**
     * Remove uma pessoa do banco de dados.
     *
     * @param id O ID da pessoa a ser removida
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean deletar(int id) {
        String sql = "DELETE FROM Pessoa WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return false;
    }
}
