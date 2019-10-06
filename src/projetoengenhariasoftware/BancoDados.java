/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author aluno
 */
public class BancoDados {

    Connection conexao;
    ResultSet dados;
    boolean on;
    
    public ResultSet getDados(String select) throws SQLException {
        PreparedStatement s = conexao.prepareStatement(select);

        return s.executeQuery();
    }
    
    public boolean consultaLogin(String tabela, String login, String senha) throws SQLException {
        tabela = "select * from "+tabela;
        dados = getDados(tabela);
        if (dados.first()) {
            do {
                if (login.equals(dados.getString("login").toLowerCase())) {
                    do {
                        if (senha.equals(dados.getString("senha"))) {
                            dados.close();
                            return true;
                        }
                    } while (dados.next());
                }
            } while (dados.next());
        }
        dados.close();
        return false;
    }
    
    public int consultaTipoUsuario(String tabela, String login, String senha) throws SQLException {
        tabela = "select * from "+tabela;
        dados = getDados(tabela);
        if (dados.first()) {
            do {
                if (login.equals(dados.getString("login").toLowerCase())) {
                    return dados.getInt("tipo");
                }
            } while (dados.next());
        }
        dados.close();
        return 0;
    }
    
    public ResultSet getModalidade() throws SQLException
    {
        dados = getDados("select * from modalidade");
        
        return dados;
    }
    
    public BancoDados() {
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/eg?useTimezone=true&serverTimezone=UTC",
                    "root", "");
            on = true;
        } catch (HeadlessException | SQLException e) {
            on = false;
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar!\n" + e.getMessage());
        }
    }
    
    public boolean t() {
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/eg?useTimezone=true&serverTimezone=UTC",
                    "root", "");
            System.out.println("Conectado!");
            return true;
        } catch (HeadlessException | SQLException e) {
            
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar!\n" + e.getMessage());
            return false;
        }
    }

    boolean getDados(String select__from_nome, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
