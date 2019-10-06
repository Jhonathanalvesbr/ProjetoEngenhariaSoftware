/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import java.sql.SQLException;

/**
 *
 * @author aluno
 */
public class Teste {
    
    public static void main (String[] args) throws SQLException
    {
       BancoDados conexao = new BancoDados();
        
       if(conexao.consultaLogin("login", "jhonathan", "32106478"))
            System.out.println("Login!");
    }
    
}
