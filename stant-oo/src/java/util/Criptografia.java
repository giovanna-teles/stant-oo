package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Classe utilitária para criptografia de senhas usando SHA-256 com salt.
 */
public class Criptografia {
    private static final String ALGORITMO = "SHA-256";
    
    /**
     * Gera um hash seguro para a senha usando SHA-256 com salt.
     * @param senha A senha a ser criptografada
     * @return String contendo o hash da senha concatenado com o salt
     */
    public static String hashSenha(String senha) {
        try {
            // Gerar salt aleatório
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            
            // Combinar senha com salt
            MessageDigest md = MessageDigest.getInstance(ALGORITMO);
            md.update(salt);
            byte[] hashedSenha = md.digest(senha.getBytes());
            
            // Converter para representação em Base64
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hashedSenha);
            
            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }
    
    /**
     * Verifica se uma senha corresponde ao hash armazenado.
     * @param senha A senha a ser verificada
     * @param hashArmazenado O hash armazenado (contendo salt e hash)
     * @return true se a senha corresponder ao hash, false caso contrário
     */
    public static boolean verificarSenha(String senha, String hashArmazenado) {
        try {
            // Separar salt e hash do hash armazenado
            String[] parts = hashArmazenado.split(":");
            if (parts.length != 2) {
                return false;
            }
            
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] storedHash = Base64.getDecoder().decode(parts[1]);
            
            // Calcular hash da senha fornecida com o mesmo salt
            MessageDigest md = MessageDigest.getInstance(ALGORITMO);
            md.update(salt);
            byte[] hashedSenha = md.digest(senha.getBytes());
            
            // Comparar os hashes
            return MessageDigest.isEqual(storedHash, hashedSenha);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao verificar senha", e);
        }
    }
}