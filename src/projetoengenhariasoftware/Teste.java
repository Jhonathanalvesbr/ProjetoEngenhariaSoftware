/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.regex.Pattern;


/**
 *
 * @author aluno
 */
public class Teste {
public static String str(String str) {
    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    return pattern.matcher(nfdNormalizedString).replaceAll("");
}
    public static void main(String[] args) throws SQLException {
        /*BancoDados conexao = new BancoDados();

        if (conexao.consultaLogin("adm", "adm")) {
            System.out.println("Login!");
        }*/

        Ftp ftp = new Ftp();
    }
}


