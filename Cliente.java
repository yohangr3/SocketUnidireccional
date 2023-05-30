

import javax.swing.*;
import java.awt.event.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.*;




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

class LaminaMarcoCliente extends JPanel implements Runnable{
	
	public LaminaMarcoCliente(){

		nick = new JTextField(5);

		add(nick);
	
		JLabel texto=new JLabel("-CHAT-");
		
		add(texto);

		ip = new JTextField(8);

		add(ip);

		campochat = new JTextArea(12,20); //Creación campo de texto donde se visualiza los mensajes

		add(campochat);//Agregamos el campo de texto

	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");

		EnviaTexto mienevento = new EnviaTexto(); //Creamos una instancia de EnviarTexto
		
		miboton.addActionListener(mienevento);
		
		add(miboton);	

		Thread mihilo = new Thread(this);
		
		mihilo.start();
	}
	
	
	private class EnviaTexto implements ActionListener{ //Clase para el funcionamiento del boton enviar utilizando eventos

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//System.out.println("Funciona"); //Me imprime "funciona" cuando utilizamos el boton enviar
			//System.out.println(campo1.getText()); En la barra de textfield nos dejará escribir y nos imprime en consola

			//Creación del socket
			try {
				Socket misocket = new Socket("127.0.0.1",5555);

				PaqueteEnvio datos = new PaqueteEnvio();
				datos.setNick(nick.getText());//Obtener el texto del nick
				datos.setPuerto(ip.getText());
				datos.setMensaje(campo1.getText());

				ObjectOutputStream paquete_datos = new ObjectOutputStream(misocket.getOutputStream()); //flujo de salida para poder enviar información

				paquete_datos.writeObject(datos);
				paquete_datos.close();
				misocket.close();

				/*DataOutputStream flujo_salida= new DataOutputStream(misocket.getOutputStream());
				flujo_salida.writeUTF(campo1.getText()); //Lo que se escriba en el textfield viajara por el socket al servidor*
				flujo_salida.close(); //cerramos el flujo de datos*/

			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}catch (IOException e1){
				System.out.println(e1.getMessage());

			}

		}

	}

		
		
		
	private JTextField campo1,nick,ip;

	private JTextArea campochat;
	
	private JButton miboton;

	@Override
	public void run() {
		try{
			ServerSocket servidor_cliente = new ServerSocket(9091);
			Socket cliente;

			PaqueteEnvio paqueteRecibido;

			while(true){
				cliente = servidor_cliente.accept();

				ObjectInputStream flujoentrada = new ObjectInputStream(cliente.getInputStream());
				paqueteRecibido=(PaqueteEnvio) flujoentrada.readObject();
				campochat.append("\n" + paqueteRecibido.getNick()+ ": " + paqueteRecibido.getMensaje());


			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}

class PaqueteEnvio  implements Serializable{
	
	private String nick,puerto,mensaje;

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPuerto() {
		return puerto;
	}

	public void setPuerto(String ip) {
		this.puerto = ip;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
}