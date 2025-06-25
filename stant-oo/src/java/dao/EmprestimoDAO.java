package dao;

import model.Emprestimo;
import model.Administrador;
import model.Pessoa;
import model.Livro;
import util.ConexaoBD;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para a entidade Emprestimo. Responsável por todas as
 * operações de CRUD no banco de dados.
 */
public class EmprestimoDAO {

    /**
     * Insere um novo empréstimo no banco de dados.
     *
     * @param emprestimo O empréstimo a ser inserido
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO Emprestimo (livro_isbn, pessoa_id, administrador_id, data_prevista_dev, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, emprestimo.getLivro().getIsbn());
            ps.setInt(2, emprestimo.getPessoa().getId());
            ps.setInt(3, emprestimo.getAdministrador().getId());
            ps.setDate(4, Date.valueOf(emprestimo.getDataPrevistaDev()));
            ps.setString(5, emprestimo.getStatus());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        emprestimo.setId(rs.getInt(1));
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
     * Busca um empréstimo pelo ID.
     *
     * @param id O ID do empréstimo
     * @return O empréstimo encontrado ou null se não existir
     */
    public Emprestimo buscarPorId(int id) {
        String sql = "SELECT e.*, l.titulo as livro_titulo, p.nome as pessoa_nome, a.nome as admin_nome "
                + "FROM Emprestimo e "
                + "JOIN Livro l ON e.livro_isbn = l.ISBN "
                + "JOIN Pessoa p ON e.pessoa_id = p.id "
                + "JOIN Administrador a ON e.administrador_id = a.id "
                + "WHERE e.id = ?";
        Emprestimo emprestimo = null;

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emprestimo = new Emprestimo();
                    emprestimo.setId(rs.getInt("id"));

                    // Criar objeto Livro mínimo (apenas com ISBN e título)
                    Livro livro = new Livro();
                    livro.setIsbn(rs.getInt("livro_isbn"));
                    livro.setTitulo(rs.getString("livro_titulo"));
                    emprestimo.setLivro(livro);

                    // Criar objeto Pessoa mínimo (apenas com id e nome)
                    Pessoa pessoa = new Pessoa();
                    pessoa.setId(rs.getInt("pessoa_id"));
                    pessoa.setNome(rs.getString("pessoa_nome"));
                    emprestimo.setPessoa(pessoa);

                    // Criar objeto Administrador mínimo (apenas com id e nome)
                    Administrador admin = new Administrador();
                    admin.setId(rs.getInt("administrador_id"));
                    admin.setNome(rs.getString("admin_nome"));
                    emprestimo.setAdministrador(admin);

                    emprestimo.setDataEmprestimo(rs.getTimestamp("data_emprestimo").toLocalDateTime());
                    emprestimo.setDataPrevistaDev(rs.getDate("data_prevista_dev").toLocalDate());

                    if (rs.getDate("data_devolucao") != null) {
                        emprestimo.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
                    }

                    emprestimo.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return emprestimo;
    }

    /**
     * Lista todos os empréstimos cadastrados.
     *
     * @return Lista de empréstimos
     */
    public List<Emprestimo> listarTodos() {
        String sql = "SELECT e.*, l.titulo as livro_titulo, p.nome as pessoa_nome, a.nome as admin_nome "
                + "FROM Emprestimo e "
                + "JOIN Livro l ON e.livro_isbn = l.ISBN "
                + "JOIN Pessoa p ON e.pessoa_id = p.id "
                + "JOIN Administrador a ON e.administrador_id = a.id";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection conexao = ConexaoBD.getConexao(); Statement stmt = conexao.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Emprestimo emprestimo = new Emprestimo();
                emprestimo.setId(rs.getInt("id"));

                // Criar objeto Livro mínimo (apenas com ISBN e título)
                Livro livro = new Livro();
                livro.setIsbn(rs.getInt("livro_isbn"));
                livro.setTitulo(rs.getString("livro_titulo"));
                emprestimo.setLivro(livro);

                // Criar objeto Pessoa mínimo (apenas com id e nome)
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("pessoa_id"));
                pessoa.setNome(rs.getString("pessoa_nome"));
                emprestimo.setPessoa(pessoa);

                // Criar objeto Administrador mínimo (apenas com id e nome)
                Administrador admin = new Administrador();
                admin.setId(rs.getInt("administrador_id"));
                admin.setNome(rs.getString("admin_nome"));
                emprestimo.setAdministrador(admin);

                emprestimo.setDataEmprestimo(rs.getTimestamp("data_emprestimo").toLocalDateTime());
                emprestimo.setDataPrevistaDev(rs.getDate("data_prevista_dev").toLocalDate());

                if (rs.getDate("data_devolucao") != null) {
                    emprestimo.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
                }

                emprestimo.setStatus(rs.getString("status"));

                emprestimos.add(emprestimo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return emprestimos;
    }

    /**
     * Atualiza os dados de um empréstimo.
     *
     * @param emprestimo O empréstimo com os dados atualizados
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean atualizar(Emprestimo emprestimo) {
        String sql = "UPDATE Emprestimo SET data_prevista_dev = ?, data_devolucao = ?, status = ? WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(emprestimo.getDataPrevistaDev()));

            if (emprestimo.getDataDevolucao() != null) {
                ps.setDate(2, Date.valueOf(emprestimo.getDataDevolucao()));
            } else {
                ps.setNull(2, Types.DATE);
            }

            ps.setString(3, emprestimo.getStatus());
            ps.setInt(4, emprestimo.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return false;
    }

    /**
     * Remove um empréstimo do banco de dados.
     *
     * @param id O ID do empréstimo a ser removido
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
    public boolean deletar(int id) {
        String sql = "DELETE FROM Emprestimo WHERE id = ?";

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

    /**
     * Lista empréstimos por status.
     *
     * @param status O status dos empréstimos a serem listados
     * @return Lista de empréstimos com o status especificado
     */
    public List<Emprestimo> listarPorStatus(String status) {
        String sql = "SELECT e.*, l.titulo as livro_titulo, p.nome as pessoa_nome, a.nome as admin_nome "
                + "FROM Emprestimo e "
                + "JOIN Livro l ON e.livro_isbn = l.ISBN "
                + "JOIN Pessoa p ON e.pessoa_id = p.id "
                + "JOIN Administrador a ON e.administrador_id = a.id "
                + "WHERE e.status = ?";
        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection conexao = ConexaoBD.getConexao(); PreparedStatement ps = conexao.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Emprestimo emprestimo = new Emprestimo();
                    emprestimo.setId(rs.getInt("id"));

                    // Criar objeto Livro mínimo (apenas com ISBN e título)
                    Livro livro = new Livro();
                    livro.setIsbn(rs.getInt("livro_isbn"));
                    livro.setTitulo(rs.getString("livro_titulo"));
                    emprestimo.setLivro(livro);

                    // Criar objeto Pessoa mínimo (apenas com id e nome)
                    Pessoa pessoa = new Pessoa();
                    pessoa.setId(rs.getInt("pessoa_id"));
                    pessoa.setNome(rs.getString("pessoa_nome"));
                    emprestimo.setPessoa(pessoa);

                    // Criar objeto Administrador mínimo (apenas com id e nome)
                    Administrador admin = new Administrador();
                    admin.setId(rs.getInt("administrador_id"));
                    admin.setNome(rs.getString("admin_nome"));
                    emprestimo.setAdministrador(admin);

                    emprestimo.setDataEmprestimo(rs.getTimestamp("data_emprestimo").toLocalDateTime());
                    emprestimo.setDataPrevistaDev(rs.getDate("data_prevista_dev").toLocalDate());

                    if (rs.getDate("data_devolucao") != null) {
                        emprestimo.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
                    }

                    emprestimo.setStatus(rs.getString("status"));

                    emprestimos.add(emprestimo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexaoBD.fecharConexao();
        }
        return emprestimos;
    }
}
