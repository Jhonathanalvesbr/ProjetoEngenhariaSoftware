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
   
    public ResultSet getModalidade() {
        String select = "select * from modalidade";
        PreparedStatement in;
        try {
            in = conexao.prepareStatement(select);
            return in.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean consultaLogin(String login, String senha) {
        String select = "select * from login";
        try {
            PreparedStatement in = conexao.prepareStatement(select);

            ResultSet resultadoSelect = in.executeQuery();

            if (resultadoSelect.first()) {
                do {
                    if (login.equals(resultadoSelect.getString("login").toLowerCase())) {
                        do {
                            if (senha.equals(resultadoSelect.getString("senha"))) {
                                in.close();
                                resultadoSelect.close();
                                return true;
                            }
                        } while (resultadoSelect.next());
                    }
                } while (resultadoSelect.next());
            }
            in.close();
            resultadoSelect.close();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public String getNome(String login, int tipoUser) {
        if (tipoUser == 0) {
            String select = "select a.nomeAluno from aluno a, login l where l.idLogin = (select idLogin from login where login = ?) and a.idLogin = (select idLogin from login where login = ?)";
            try {
                PreparedStatement in = conexao.prepareStatement(select);
                in.setString(1, login);
                in.setString(2, login);
                ResultSet resultadoSelect = in.executeQuery();
                resultadoSelect.first();
                String nome = resultadoSelect.getString(1);
                resultadoSelect.close();
                in.close();
                return nome;
            } catch (SQLException ex) {
                Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return null;
    }

    public int consultaTipoUsuario(String login, String senha) {
        String select = "select * from login";
        PreparedStatement in;
        try {
            in = conexao.prepareStatement(select);
            ResultSet resultadoSelect = in.executeQuery();
            if (resultadoSelect.first()) {
                do {
                    if (login.equals(resultadoSelect.getString("login").toLowerCase())) {
                        int tipo = resultadoSelect.getInt("tipo");
                        in.execute();
                        resultadoSelect.close();
                        in.close();
                        return tipo;
                    }
                } while (resultadoSelect.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public void setModalidade(String nomeModalidade, String numeroHoraMaxima, String observacao) {
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
    
    int getIdAluno(String aluno)
    {
        String select = "select idAluno from aluno where nomeAluno = ?;";
        try {
            PreparedStatement in = conexao.prepareStatement(select);
            in.setString(1, aluno);
            ResultSet resultadoSelect = in.executeQuery();
            resultadoSelect.first();
            int idAluno = resultadoSelect.getInt("idAluno");
            in.close();
            return idAluno;
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    int getIdModalidade(String modalidade)
    {
        String select = "select idModalidade from modalidade where nomeModalidade = ?;";
        try {
            PreparedStatement in = conexao.prepareStatement(select);
            in.setString(1, modalidade);
            ResultSet resultadoSelect = in.executeQuery();
            resultadoSelect.first();
            int idAluno = resultadoSelect.getInt("idModalidade");
            in.close();
            return idAluno;
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public void setCertificado(int idAluno, int idModalidade, String horas, String data) {
        String insert = "insert into certificado (idAluno, idModalidade, horasModalidade, dataEnvio, StatusProcesso) values(?,?,?,?,?);";
        try {
            PreparedStatement in = conexao.prepareStatement(insert);
            in.setInt(1, idAluno);
            in.setInt(2, idModalidade);
            in.setString(3, horas);
            in.setString(4, data);
            in.setString(5,"0");
            in.execute();
            in.close();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void setModalidadeAluno(int idAluno, int idModalidade) {
        String insert = "insert into modalidadealuno (idAluno, idModalidade) values(?,?);";
        try {
            PreparedStatement in = conexao.prepareStatement(insert);
            in.setInt(1, idAluno);
            in.setInt(2, idModalidade);
            in.execute();
            in.close();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateModalidade(String nomeModalidadeAntigo, String nomeModalidadeNovo, String numeroHoraMaxima, String observacao) {
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
    
    public void deleteModalidade(String nomeModalidade) {
        String delete = "delete from modalidade where nomeModalidade = '"+nomeModalidade+"';";
        try {
            PreparedStatement in = conexao.prepareStatement(delete);
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
            JOptionPane.showMessageDialog(null, "Não foi possivel se conectar com o banco de dados!\n\n" + e.getMessage());
        }
    }

    public ResultSet getBusca(String busca) {
        if (busca.equals("")) {
            String select = "select * from modalidade";
            PreparedStatement in;
            try {
                in = conexao.prepareStatement(select);
                return in.executeQuery();
                
            } catch (SQLException ex) {
                Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            String select = "select * from modalidade where nomeModalidade like('".concat(busca + "%')");
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
}
