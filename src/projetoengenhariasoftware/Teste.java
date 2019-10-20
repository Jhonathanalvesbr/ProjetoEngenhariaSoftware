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
        ArrayList<Validar> validar = new ArrayList();
        
        conexao.getValidar("Jhonathan Alves", "Curso", validar);
        for(int i = 0; i < validar.size(); i++)
        {
            System.out.println(validar.get(i).getNome());
            System.out.println(validar.get(i).getCurso());
            System.out.println(validar.get(i).getHoras());
            System.out.println(validar.get(i).getStatus());
            System.out.println(validar.get(i).getDataEnvio());
            System.out.println(i);
        }
        
        
        

    }
}


