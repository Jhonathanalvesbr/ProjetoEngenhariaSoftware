/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class ProjetoEngenhariaSoftware {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        TelaLogin tela = new TelaLogin();
        tela.setVisible(true);
        /*Teste t = new Teste();
         try {
         t.main(args);
         } catch (SQLException ex) {
         Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
         }*/
    }

}
