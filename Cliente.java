// 

import javax.swing.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.io.DataInputStream;


public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,280,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel{
	
	public LaminaMarcoCliente(){
	
		JLabel texto=new JLabel("CLIENTE");
		
		add(texto);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");

		EnviaTexto mienevento = new EnviaTexto(); //Creamos una instancia de EnviarTexto
		
		miboton.addActionListener(mienevento);
		
		add(miboton);	
		
	}
	
	
	private class EnviaTexto implements ActionListener{ //Clase para el funcionamiento del boton enviar utilizando eventos

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//System.out.println("Funciona"); //Me imprime "funciona" cuando utilizamos el boton enviar
			//System.out.println(campo1.getText()); En la barra de textfield nos dejará escribir y nos imprime en consola

			//Creación del socket
			try {
				Socket misocket = new Socket("192.168.0.51",3333);

				DataOutputStream flujo_salida= new DataOutputStream(misocket.getOutputStream());

				flujo_salida.writeUTF(campo1.getText()); //Lo que se escriba en el textfield viajara por el socket al servidor

				flujo_salida.close(); //cerramos el flujo de datos
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
			}

		}

	}

		
		
		
	private JTextField campo1;
	
	private JButton miboton;
	
}