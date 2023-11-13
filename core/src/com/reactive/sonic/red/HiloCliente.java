package com.reactive.sonic.red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import Pantallas.PantallaJuego;
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
		
		String[] mensajeParametrizado = msg.split("-");
		if(mensajeParametrizado.length<2) {
			if(msg.equals("OK")) {
				ipServidor = dp.getAddress();
			} else if(msg.equals("Empieza")) {
				System.out.println("Llega EMPIEZA");
				Global.empieza = true;
			}
		} else {
			if(mensajeParametrizado[0].equals("Actualizar")) {
				if(mensajeParametrizado[1].equals("jugador")) {
					float posX = Float.parseFloat(mensajeParametrizado[2]);
					app.jugador.setX(posX);
				}else if (mensajeParametrizado[4].equals("jugadorAlt")) {
					float posX = Float.parseFloat(mensajeParametrizado[5]);
					app.jugadorAlt.setX(posX);
				}
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
