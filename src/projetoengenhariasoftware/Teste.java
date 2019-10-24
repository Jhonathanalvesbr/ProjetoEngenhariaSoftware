/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import static javax.print.attribute.Size2DSyntax.MM;
import javax.swing.JLabel;

/**
 *
 * @author aluno
 */
public class Teste {

    public static void main(String[] args) throws SQLException {
        BancoDados conexao = new BancoDados();

        if (conexao.consultaLogin("adm", "adm")) {
            System.out.println("Login!");
        }

        Ftp ftp = new Ftp();
        
        ftp.conectar();

    }
}


