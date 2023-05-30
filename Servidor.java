

import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;


public class Servidor  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
	}	
}

class MarcoServidor extends JFrame implements Runnable {
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);

		Thread mihilo = new Thread(this); //Creación del hilo pra que haya comunicación continúa del servidor

		mihilo.start();
		
		}
	
	

	@Override
	public void run() {


		//System.out.println("Estoy a la escucha");

		//Creamos el serversocket que escuchara lo que le enviemos desde el cliente y lo aceptará esto envuelto en una exception en el puerto 3333
		try (ServerSocket servidor = new ServerSocket(3333)) { 
			Socket misocket = servidor.accept(); //Creamos esta variable tipo Socket para que este a la escucha y acepte lo enviado

			DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream()); //flujo de datos entrando al servidor

			String mensaje = flujo_entrada.readUTF(); //Variable para que escriba el mensaje enviado en el textfield

			areatexto.append("/n" + mensaje);

			misocket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private	JTextArea areatexto;
}
