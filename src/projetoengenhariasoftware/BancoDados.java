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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
        tabela = "select * from " + tabela;
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
    
    public String getNome(String login, int tipoUser)
    {
       if(tipoUser == 0)
       {
           String select = "select a.nomeAluno from aluno a, login l where l.idLogin = (select idLogin from login where login = ?) and a.idLogin = (select idLogin from login where login = ?)";
        try {
            PreparedStatement in = conexao.prepareStatement(select);
            in.setString(1, login);
            in.setString(2, login);
            ResultSet r = in.executeQuery();
            r.first();
            String nome = r.getString(1);
            r.close();
            in.close();
            System.out.println(nome);
            return nome;
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }

       }
       
       return null;
    }
    
    
    public int consultaTipoUsuario(String tabela, String login, String senha) throws SQLException {
        tabela = "select * from " + tabela;
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

    public ResultSet getModalidade() throws SQLException {
        dados = getDados("select * from modalidade");

        return dados;
    }
            
    public void setModalidade(String nomeModalidade, String numeroHoraMaxima, String observacao)
    {
        String insert = "insert into modalidade (nomeModalidade, numeroHoraMaxima, observacao) values (?,?,?);";
        try {
            PreparedStatement in = conexao.prepareStatement(insert);
            in.setString(1, nomeModalidade);
            in.setString(2, numeroHoraMaxima);
            in.setString(3, observacao);
            in.execute();
            in.close();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateModalidade(String nomeModalidadeAntigo, String nomeModalidadeNovo, String numeroHoraMaxima, String observacao)
    {
        String update = "update modalidade set nomeModalidade = ?, numeroHoraMaxima = ?, observacao = ? "
                + "where nomeModalidade = ?;";
        try {
            PreparedStatement in = conexao.prepareStatement(update);
            in.setString(1, nomeModalidadeNovo);
            in.setString(2, numeroHoraMaxima);
            in.setString(3, observacao);
            in.setString(4, nomeModalidadeAntigo);
            in.execute();
            in.close();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public BancoDados() {
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/engenhariasoftware?useTimezone=true&serverTimezone=UTC",
                    "root", "");
            on = true;
        } catch (HeadlessException | SQLException e) {
            on = false;
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar!\n" + e.getMessage());
        }
    }
    
    public ResultSet getBusca(String busca)
    {
        if(busca.equals(""))
        {
        String select = "select * from modalidade";
            try {
                dados = getDados(select);
            } catch (SQLException ex) {
                Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
            }
            try { 
                if (dados.first())
                    return dados;
            } catch (SQLException ex) {
                Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else
        {
            String select = "select * from modalidade where nomeModalidade = '".concat(busca+"'");
        try {
            PreparedStatement in = conexao.prepareStatement(select);

            in.execute();
            
            
            return in.getResultSet();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        return null;
    }
    
    public boolean t() {
        try {
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/engenhariasoftware?useTimezone=true&serverTimezone=UTC",
                    "root", "");
            System.out.println("Conectado!");
            return true;
        } catch (HeadlessException | SQLException e) {

            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar!\n" + e.getMessage());
            return false;
        }
    }





}
