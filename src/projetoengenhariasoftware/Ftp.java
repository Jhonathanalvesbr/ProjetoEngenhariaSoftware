/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoengenhariasoftware;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jhonathan Alves
 */
public class Ftp {

    FTPClient ftp = new FTPClient();
    
    public void conectar(String diretorio){
        try {
            ftp.connect("192.168.1.137", 2221);
            ftp.login("francis", "francis");
            ftp.makeDirectory(diretorio);
            ftp.changeWorkingDirectory(diretorio);
        } catch (IOException ex) {
            Logger.getLogger(Ftp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
    public void upload(String nome, String diretorio) throws FileNotFoundException, IOException 
    {   
        FileInputStream arqEnviar = new FileInputStream (diretorio);
            ftp.storeFile (nome, arqEnviar);
            
    }
    
    public void download(String diretorio) throws FileNotFoundException, IOException
    {
        String[] arq = ftp.listNames();
            System.out.println ("Listando arquivos: \n");
            for (String f : arq){
                  System.out.println(f);            
            }
        FileOutputStream fos = new FileOutputStream(diretorio+"\\edu.txt");
            if (ftp.retrieveFile( "edu.txt", fos ))
            {
                
                System.out.println("Download efetuado com sucesso!");
            }
                  
            else
                  System.out.println ("Erro ao efetuar download do arquivo.");
    }
}

