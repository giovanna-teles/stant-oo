package dao;

import model.*;
import util.ConexaoBD;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Livro.
 * Responsável por todas as operações de CRUD no banco de dados.
 */
public class LivroDAO {
    
    /**
     * Insere um novo livro no banco de dados.
     * @param livro O livro a ser inserido
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean inserir(Livro livro) {
        String sql = "INSERT INTO Livro (ISBN, titulo, autor_id, genero, edicao, status, nota, administrador_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, livro.getIsbn());
            ps.setString(2, livro.getTitulo());
            ps.setInt(3, livro.getAutor().getId());
            ps.setString(4, livro.getGenero());
            ps.setObject(5, livro.getEdicao(), Types.INTEGER);
            ps.setString(6, livro.getStatus());
            ps.setObject(7, livro.getNota(), Types.INTEGER);
            ps.setInt(8, livro.getAdministrador().getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return false;
    }
    
    /**
     * Busca um livro pelo ISBN.
     * @param isbn O ISBN do livro
     * @return O livro encontrado ou null se não existir
     */
    public Livro buscarPorIsbn(int isbn) {
        String sql = "SELECT l.*, a.nome as autor_nome FROM Livro l JOIN Autor a ON l.autor_id = a.id WHERE l.ISBN = ?";
        Livro livro = null;
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, isbn);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    livro = new Livro();
                    livro.setIsbn(rs.getInt("ISBN"));
                    livro.setTitulo(rs.getString("titulo"));
                    
                    // Criar objeto Autor mínimo (apenas com id e nome)
                    Autor autor = new Autor();
                    autor.setId(rs.getInt("autor_id"));
                    autor.setNome(rs.getString("autor_nome"));
                    livro.setAutor(autor);
                    
                    livro.setGenero(rs.getString("genero"));
                    livro.setEdicao(rs.getInt("edicao"));
                    livro.setStatus(rs.getString("status"));
                    livro.setNota(rs.getInt("nota"));
                    livro.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return livro;
    }
    
    /**
     * Lista todos os livros cadastrados.
     * @return Lista de livros
     */
    public List<Livro> listarTodos() {
        String sql = "SELECT l.*, a.nome as autor_nome FROM Livro l JOIN Autor a ON l.autor_id = a.id";
        List<Livro> livros = new ArrayList<>();
        
        try (Connection conexao = ConexaoBD.getConexao();
             Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Livro livro = new Livro();
                livro.setIsbn(rs.getInt("ISBN"));
                livro.setTitulo(rs.getString("titulo"));
                
                // Criar objeto Autor mínimo (apenas com id e nome)
                Autor autor = new Autor();
                autor.setId(rs.getInt("autor_id"));
                autor.setNome(rs.getString("autor_nome"));
                livro.setAutor(autor);
                
                livro.setGenero(rs.getString("genero"));
                livro.setEdicao(rs.getInt("edicao"));
                livro.setStatus(rs.getString("status"));
                livro.setNota(rs.getInt("nota"));
                livro.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                
                livros.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return livros;
    }
    
    /**
     * Atualiza os dados de um livro.
     * @param livro O livro com os dados atualizados
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean atualizar(Livro livro) {
        String sql = "UPDATE Livro SET titulo = ?, autor_id = ?, genero = ?, edicao = ?, status = ?, nota = ? WHERE ISBN = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getAutor().getId());
            ps.setString(3, livro.getGenero());
            ps.setObject(4, livro.getEdicao(), Types.INTEGER);
            ps.setString(5, livro.getStatus());
            ps.setObject(6, livro.getNota(), Types.INTEGER);
            ps.setInt(7, livro.getIsbn());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return false;
    }
    
    /**
     * Remove um livro do banco de dados.
     * @param isbn O ISBN do livro a ser removido
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean deletar(int isbn) {
        String sql = "DELETE FROM Livro WHERE ISBN = ?";
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setInt(1, isbn);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return false;
    }
    
    /**
     * Lista livros por status.
     * @param status O status dos livros a serem listados
     * @return Lista de livros com o status especificado
     */
    public List<Livro> listarPorStatus(String status) {
        String sql = "SELECT l.*, a.nome as autor_nome FROM Livro l JOIN Autor a ON l.autor_id = a.id WHERE l.status = ?";
        List<Livro> livros = new ArrayList<>();
        
        try (Connection conexao = ConexaoBD.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            
            ps.setString(1, status);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro();
                    livro.setIsbn(rs.getInt("ISBN"));
                    livro.setTitulo(rs.getString("titulo"));
                    
                    // Criar objeto Autor mínimo (apenas com id e nome)
                    Autor autor = new Autor();
                    autor.setId(rs.getInt("autor_id"));
                    autor.setNome(rs.getString("autor_nome"));
                    livro.setAutor(autor);
                    
                    livro.setGenero(rs.getString("genero"));
                    livro.setEdicao(rs.getInt("edicao"));
                    livro.setStatus(rs.getString("status"));
                    livro.setNota(rs.getInt("nota"));
                    livro.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                    
                    livros.add(livro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexaoBD.fecharConexao();
        }
        return livros;
    }
}