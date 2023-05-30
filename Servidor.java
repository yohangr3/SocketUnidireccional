
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

	public MarcoServidor() {

		setBounds(1200, 300, 280, 350);

		JPanel milamina = new JPanel();

		milamina.setLayout(new BorderLayout());

		areatexto = new JTextArea();

		milamina.add(areatexto, BorderLayout.CENTER);

		add(milamina);

		setVisible(true);

		Thread mihilo = new Thread(this); // Creación del hilo pra que haya comunicación continúa del servidor

		mihilo.start();

	}

	@Override
	public void run() {

		// System.out.println("Estoy a la escucha");

		// Creamos el serversocket que escuchara lo que le enviemos desde el cliente y
		// lo aceptará esto envuelto en una exception en el puerto 3333
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

				
					/*DataInputStream flujo_entrada = new DataInputStream(misocket.getInputStream()); // flujo de datos entrando al servidor
																									 

					String mensaje_texto = flujo_entrada.readUTF(); // Variable para que escriba el mensaje enviado en el textfield
																

					areatexto.append("\n" + mensaje_texto);*/

					areatexto.append("\n"+ nick+ ": " + mensaje + " para " + puerto);
					
					Socket enviaDestinatario = new Socket("127.0.0.1",Integer.parseInt(puerto)); //puente para recibir información
					ObjectOutputStream paqueteReenvio = new ObjectOutputStream(enviaDestinatario.getOutputStream());
					paqueteReenvio.writeObject(paquete_recibido);
					paqueteReenvio.close();
					enviaDestinatario.close();
					misocket.close();
				}
			}

		} catch (IOException  | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			areatexto.append(e.getMessage());
		} 
	}

	private JTextArea areatexto;
}
