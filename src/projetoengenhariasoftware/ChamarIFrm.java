/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 *
 * @author Administrador
 */
public class ChamarIFrm {
    
     public void chamarFrame(JInternalFrame iFrame,  JDesktopPane desk) {
        centralizaForm(iFrame);
        iFrame.setVisible(true);
        desk.add(iFrame);
        try {
            iFrame.setSelected(false);
            iFrame.setSelected(true);
        } catch (PropertyVetoException ex) {
        }
    }

    private void centralizaForm(JInternalFrame iFrame) {
        Dimension paneSize = iFrame.getSize();
        Dimension screenSize = iFrame.getToolkit().getScreenSize();
        iFrame.setLocation((screenSize.width - paneSize.width) / 2, (int) ((screenSize.height - paneSize.height) / 2.5));
    }       
}
