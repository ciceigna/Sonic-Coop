package com.reactive.sonic.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import com.badlogic.gdx.graphics.OrthographicCamera;

import Pantallas.PantallaJuego;
import Sprites.Sonic;
import Sprites.Tails;
import utiles.Global;

public class HiloCliente extends Thread {
	
	private DatagramSocket socket;
	private InetAddress ipServidor;
	private int puertoServidor = 7517;
	private boolean fin = false;
	private PantallaJuego app; 
	
	public HiloCliente(PantallaJuego app) {
	    try {
	        this.app = app;
	        ipServidor = InetAddress.getByName("255.255.255.255");
	        socket = new DatagramSocket();
	    } catch (SocketException | UnknownHostException e) {
	        e.printStackTrace();
	    }
	    enviarMensaje("Conexion");
	}

	
	@Override
	public void run() {
		while(!fin) {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data,data.length);
			try {
				socket.receive(dp);
				procesarMensaje(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void procesarMensaje(DatagramPacket dp) {

	    String msg = (new String(dp.getData())).trim();
        Sonic jugador = app.jugador;
		Tails jugadorAlt = app.jugadorAlt;
		OrthographicCamera camJuego = app.camJuego;
        String[] mensajeParametrizado = msg.split("-");
        
	    if (mensajeParametrizado.length < 2) {
	        if (msg.equals("OK")) {
	            ipServidor = dp.getAddress();
	        } else if (msg.equals("Empieza")) {
	            Global.empieza = true;
	        }
	    } else if (mensajeParametrizado[0].equals("Actualizar")) {
	        	float posX = Float.parseFloat(mensajeParametrizado[2]);
		        float posY = Float.parseFloat(mensajeParametrizado[3]);
		        float camX = Float.parseFloat(mensajeParametrizado[2]);
		        float camY = Float.parseFloat(mensajeParametrizado[2]);
		        
	            if (mensajeParametrizado[1].equals("jugador")) {
	                jugador.setPosition(posX, posY);
	                
	            } else if (mensajeParametrizado[1].equals("jugadorAlt")) {
	            	jugadorAlt.setPosition(posX, posY);
	                
	            } else if(mensajeParametrizado[1].equals("camara")) {
            		camJuego.position.x = camX;
            		camJuego.position.y = camY;
	            }
	     } else if (mensajeParametrizado[0].equals("Estado")) {
	        	if (mensajeParametrizado[1].equals("jugador")) {
	        		Sonic.Estado estado = Sonic.Estado.valueOf(mensajeParametrizado[2]);
	                jugador.estadoActual = estado;
	            } else if (mensajeParametrizado[1].equals("jugadorAlt")) {
	   	    	 	Tails.Estado estado = Tails.Estado.valueOf(mensajeParametrizado[2]);
	   	    	 	jugadorAlt.estadoActual = estado;
	            }
	        }
	}

	
	public void enviarMensaje(String mensaje) {
		byte[] data = mensaje.getBytes();
		DatagramPacket dp = new DatagramPacket(data,data.length, this.ipServidor, this.puertoServidor);
		try {
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
