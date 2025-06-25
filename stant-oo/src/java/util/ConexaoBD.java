package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe utilitária para gerenciar conexões com o banco de dados MySQL.
 */
public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_gerenciamento";
    private static final String USUARIO = "root";
    private static final String SENHA = "12345678";
    private static Connection conexao = null;
    
    /**
     * Obtém uma conexão com o banco de dados.
     * @return Objeto Connection
     */
    public static Connection getConexao() {
        if (conexao == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("Conexão estabelecida com sucesso!");
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Erro ao conectar ao banco de dados", ex);
            }
        }
        return conexao;
    }
    
    /**
     * Fecha a conexão com o banco de dados.
     */
    public static void fecharConexao() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoBD.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Erro ao fechar conexão com o banco de dados", ex);
            }
        }
    }
}