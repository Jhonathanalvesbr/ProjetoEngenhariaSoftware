/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class Sair {
    
    public static void bye()
    {
        int sair = JOptionPane.showConfirmDialog(null, "Deseja realmente sair?");
        
        if(sair == 0)
        {
            System.exit(0);
        }
    }
    
    public static void fechar(JInternalFrame frame)
    {
        frame.setVisible(false);
        frame.dispose();
    }
}
