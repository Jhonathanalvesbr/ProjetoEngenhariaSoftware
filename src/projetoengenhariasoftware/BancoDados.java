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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author aluno
 */
public class BancoDados {

    private Connection conexao;
    private ResultSet dados;
    private boolean on;

    public boolean getOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

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

    public void getValidar(String nomeCertificado, ArrayList validar) {
        String select = "select * from aluno a, certificado c join modalidade m \n" +
"where c.idModalidade = (select idModalidade from modalidade m where m.nomeModalidade = ?) and \n" +
"m.idModalidade = (select idModalidade from modalidade m where m.nomeModalidade = ?) and\n" +
"a.idAluno = c.idAluno;";
        try {
            PreparedStatement in = conexao.prepareStatement(select);
            in.setString(1, nomeCertificado);
            in.setString(2, nomeCertificado);
            ResultSet resultadoSelect = in.executeQuery();
            if (resultadoSelect.first()) {
                Validar temp;
                do {
                    temp = new Validar();
                    temp.setDataEnvio(resultadoSelect.getString("dataEnvio"));
                    temp.setCurso(resultadoSelect.getString("curso"));
                    temp.setHoras(resultadoSelect.getString("horasModalidade"));
                    temp.setStatus(resultadoSelect.getString("statusProcesso"));
                    temp.setId(resultadoSelect.getString("id"));
                    temp.setNome(resultadoSelect.getString("nomeAluno"));
                    validar.add(temp);
                } while (resultadoSelect.next());

                resultadoSelect.close();
                in.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
        ;
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

    int getIdAluno(String aluno) {

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

    public void getHorasModalidade(String modalidade, ArrayList<Visualizar> visualizar, String nome) {
        String select = "select * from certificado c, modalidade m, aluno a\n"
                + "where c.idModalidade = \n"
                + "(select idModalidade from modalidade where nomeModalidade = ?) and\n"
                + "c.idModalidade = m.idModalidade and\n"
                + "(select idAluno from aluno where nomeAluno = ?) = c.idaluno and\n"
                + "a.idAluno = (select idAluno from aluno where nomeAluno = ?);";
        try {
            PreparedStatement in = conexao.prepareStatement(select);
            in.setString(1, modalidade);
            in.setString(2, nome);
            in.setString(3, nome);
            ResultSet resultadoSelect = in.executeQuery();
            if (resultadoSelect.first()) {
                Visualizar temp;
                do {
                    temp = new Visualizar();
                    temp.setHoras(resultadoSelect.getString("horasModalidade"));
                    temp.setDataEnvio(resultadoSelect.getString("dataEnvio"));
                    temp.setStatus(resultadoSelect.getString("statusProcesso"));
                    temp.setHorasModalidade(resultadoSelect.getString("numeroHoraMaxima"));
                    visualizar.add(temp);
                } while (resultadoSelect.next());
            }
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    int getIdModalidade(String modalidade) {
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
        String insert = "insert into certificado (idAluno, idModalidade, horasModalidade, dataEnvio, statusProcesso) values(?,?,?,?,?);";
        try {
            PreparedStatement in = conexao.prepareStatement(insert);
            in.setInt(1, idAluno);
            in.setInt(2, idModalidade);
            in.setString(3, horas);
            in.setString(4, data);
            in.setString(5, "0");
            in.execute();
            in.close();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getCertificado(int idAluno, int idModalidade, String horas, String data) {
        String insert = "insert into certificado (idAluno, idModalidade, horasModalidade, dataEnvio, statusProcesso) values(?,?,?,?,?);";
        try {
            PreparedStatement in = conexao.prepareStatement(insert);
            in.setInt(1, idAluno);
            in.setInt(2, idModalidade);
            in.setString(3, horas);
            in.setString(4, data);
            in.setString(5, "0");
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
        String delete = "delete from modalidade where nomeModalidade = '" + nomeModalidade + "';";
        try {
            PreparedStatement in = conexao.prepareStatement(delete);
            in.execute();
            in.close();
        } catch (SQLException ex) {
            Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setValidar(ArrayList<Validar> validar) {
        for (int i = 0; i < validar.size(); i++) {
            String update = "update certificado set statusProcesso = ?"
                    + "where id = ?;";
            try {
                PreparedStatement in = conexao.prepareStatement(update);
                in.setString(1, validar.get(i).getStatus());
                in.setString(2, validar.get(i).getId());
                in.execute();
                in.close();
            } catch (SQLException ex) {
                Logger.getLogger(BancoDados.class.getName()).log(Level.SEVERE, null, ex);
            }
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
