
import javax.swing.*;

import java.awt.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Servidor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MarcoServidor mimarco = new MarcoServidor();

		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}

class MarcoServidor extends JFrame implements Runnable {

	/* Creación interfaz , boton, textfield y campos de nick name y puerto */

	public MarcoServidor() {

		setBounds(1200, 300, 280, 350);

		JPanel milamina = new JPanel();

		milamina.setLayout(new BorderLayout());

		areatexto = new JTextArea();

		milamina.add(areatexto, BorderLayout.CENTER);

		add(milamina);

		setVisible(true);

		Thread mihilo = new Thread(this); // Creación del hilo para que haya comunicación continúa del servidor

		mihilo.start();

	}

	@Override
	public void run() {

	
		try {
			try (ServerSocket servidor = new ServerSocket(5555)) {
				String nick,puerto,mensaje;
				PaqueteEnvio paquete_recibido;
				// Creamos esta variable tipo Socket para que este a la escucha y acepte lo enviado

				while (true) { //Blucle infinito para poder enviar más de un mensaje
					Socket misocket = servidor.accept();
					areatexto.append("Mensaje recibido");
				 
					ObjectInputStream paquete_datos = new ObjectInputStream(misocket.getInputStream());			
					
					paquete_recibido = (PaqueteEnvio) paquete_datos.readObject(); //Recepción del mensaje cliente
					areatexto.append("Paquete deserializado");
					nick = paquete_recibido.getNick();
					puerto= paquete_recibido.getPuerto();
					mensaje = paquete_recibido.getMensaje();


					areatexto.append("\n"+ nick+ ": " + mensaje + " para " + puerto);
					
					Socket enviaDestinatario = new Socket("127.0.0.1",Integer.parseInt(puerto)); //puente para recibir información
					ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream()); //Utilizamos ObjectOutputStream ya que la salida de datos es de tipo objeto
					paqueteReenvio.writeObject(paquete_recibido);
					paqueteReenvio.close(); //Cierre del paquete reenvio
					enviaDestinatario.close();//Cierre del envio al destinatario
					misocket.close();//Cierre del socket
				}
			}

		} catch (IOException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			areatexto.append(e.getMessage());
		} 
	}

	private JTextArea areatexto;
}
