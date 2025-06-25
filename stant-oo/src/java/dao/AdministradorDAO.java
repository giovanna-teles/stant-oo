package dao;

import model.Administrador;
import util.ConexaoBD;
import util.Criptografia;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Administrador.
 * Responsável por todas as operações de CRUD no banco de dados.
 */
public class AdministradorDAO {
    
    /**
     * Insere um novo administrador no banco de dados.
     * @param administrador O administrador a ser inserido
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean inserir(Administrador administrador) {
        String sql = "INSERT INTO Administrador (nome, email, senha_hash) VALUES (?, ?, ?)";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Criptografa a senha antes de armazenar
            String senhaCriptografada = Criptografia.hashSenha(administrador.getSenhaHash());
            
            ps.setString(1, administrador.getNome());
            ps.setString(2, administrador.getEmail());
            ps.setString(3, senhaCriptografada);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        administrador.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Busca um administrador pelo email.
     * @param email O email do administrador
     * @return O administrador encontrado ou null se não existir
     */
    public Administrador buscarPorEmail(String email) {
        String sql = "SELECT * FROM Administrador WHERE email = ?";
        Administrador administrador = null;
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    administrador = new Administrador();
                    administrador.setId(rs.getInt("id"));
                    administrador.setNome(rs.getString("nome"));
                    administrador.setEmail(rs.getString("email"));
                    administrador.setSenhaHash(rs.getString("senha_hash"));
                    administrador.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();//throw new exception
        }
        return administrador;
    }
    
    /**
     * Verifica as credenciais de login de um administrador.
     * @param email O email do administrador
     * @param senha A senha fornecida
     * @return O administrador se as credenciais estiverem corretas, null caso contrário
     */
    public Administrador verificarLogin(String email, String senha) {
        Administrador administrador = buscarPorEmail(email);
        
        if (administrador != null) {
            if (Criptografia.verificarSenha(senha, administrador.getSenhaHash())) {
                return administrador;
            }
        }
        return null;
    }
    
    /**
     * Lista todos os administradores cadastrados.
     * @return Lista de administradores
     */
    public List<Administrador> listarTodos() {
        String sql = "SELECT * FROM Administrador";
        List<Administrador> administradores = new ArrayList<>();
        
        try (Connection conexao = ConexaoBD.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Administrador administrador = new Administrador();
                administrador.setId(rs.getInt("id"));
                administrador.setNome(rs.getString("nome"));
                administrador.setEmail(rs.getString("email"));
                administrador.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                
                administradores.add(administrador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administradores;
    }
    
    /**
     * Atualiza os dados de um administrador.
     * @param administrador O administrador com os dados atualizados
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean atualizar(Administrador administrador) {
        String sql = "UPDATE Administrador SET nome = ?, email = ? WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, administrador.getNome());
            ps.setString(2, administrador.getEmail());
            ps.setInt(3, administrador.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Remove um administrador do banco de dados.
     * @param id O ID do administrador a ser removido
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean deletar(int id) {
        String sql = "DELETE FROM Administrador WHERE id = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}