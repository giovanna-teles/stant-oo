package dao;

import model.Autor;
import util.ConexaoBD;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Autor.
 * Responsável por todas as operações de CRUD no banco de dados.
 */
public class AutorDAO {
    
    /**
     * Insere um novo autor no banco de dados.
     * @param autor O autor a ser inserido
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean inserir(Autor autor) {
        String sql = "INSERT INTO Autor (nome, dt_nascimento, biografia) VALUES (?, ?, ?)";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, autor.getNome());
            ps.setDate(2, Date.valueOf(autor.getDataNascimento()));
            ps.setString(3, autor.getBiografia());
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        autor.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return false;
    }
    
    /**
     * Busca um autor pelo ID.
     * @param id O ID do autor
     * @return O autor encontrado ou null se não existir
     */
    public Autor buscarPorId(int id) {
        String sql = "SELECT * FROM Autor WHERE id = ?";
        Autor autor = null;
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    autor = new Autor();
                    autor.setId(rs.getInt("id"));
                    autor.setNome(rs.getString("nome"));
                    autor.setDataNascimento(rs.getDate("dt_nascimento").toLocalDate());
                    autor.setBiografia(rs.getString("biografia"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return autor;
    }
    
    /**
     * Lista todos os autores cadastrados.
     * @return Lista de autores
     */
    public List<Autor> listarTodos() {
        String sql = "SELECT * FROM Autor";
        List<Autor> autores = new ArrayList<>();
        
        try (Connection conexao = ConexaoBD.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Autor autor = new Autor();
                autor.setId(rs.getInt("id"));
                autor.setNome(rs.getString("nome"));
                autor.setDataNascimento(rs.getDate("dt_nascimento").toLocalDate());
                autor.setBiografia(rs.getString("biografia"));
                
                autores.add(autor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return autores;
    }
    
    /**
     * Atualiza os dados de um autor.
     * @param autor O autor com os dados atualizados
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean atualizar(Autor autor) {
        String sql = "UPDATE Autor SET nome = ?, dt_nascimento = ?, biografia = ? WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, autor.getNome());
            ps.setDate(2, Date.valueOf(autor.getDataNascimento()));
            ps.setString(3, autor.getBiografia());
            ps.setInt(4, autor.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return false;
    }
    
    /**
     * Remove um autor do banco de dados.
     * @param id O ID do autor a ser removido
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean deletar(int id) {
        String sql = "DELETE FROM Autor WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return false;
    }
}