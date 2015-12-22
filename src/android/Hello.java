package com.example.plugin;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import java.net.InetAddress;   
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Hello extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("greet")) {

            String ip = "";
			String ipvpn = "";
			String nom_conexion = "Falso";
			//String nom_conexion = "";
			String ide_conexion = "";
			Integer indiceenc = 0;
			
			try {
				Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
				while(enumNetworkInterfaces.hasMoreElements()){
					NetworkInterface networkInterface =
							enumNetworkInterfaces.nextElement();
					Enumeration<InetAddress> enumInetAddress =
							networkInterface.getInetAddresses();
					
					//Identificamos los nombres de la interface de red.
					//nom_conexion += "Red: " + networkInterface.getDisplayName();
					ide_conexion = "(" + networkInterface.getDisplayName() + ")";
					
					//Revisa si se esta ejecutando el controlador TUN (VPN)
					indiceenc = ide_conexion.indexOf("tun0");
					if (indiceenc == -1) {
						indiceenc = ide_conexion.indexOf("tun1");
					} else {
						nom_conexion = "Verdadero";
						break;
					}
					if (indiceenc == -1) {
						indiceenc = ide_conexion.indexOf("tun2");
					} else {
						nom_conexion = "Verdadero";
						break;
					}		
					if (indiceenc == -1) {
						
					} else {
						nom_conexion = "Verdadero";
						break;
					}
										
					while(enumInetAddress.hasMoreElements()){
						InetAddress inetAddress = enumInetAddress.nextElement();

						String ipAddress = "";
						if(inetAddress.isLoopbackAddress()){
							ipAddress = "LoopbackAddress: ";
						}else if(inetAddress.isSiteLocalAddress()){
							ipAddress = "SiteLocalAddress: ";
						}else if(inetAddress.isLinkLocalAddress()){
							ipAddress = "LinkLocalAddress: ";
						}else if(inetAddress.isMulticastAddress()){
							ipAddress = "MulticastAddress: ";
						}
						ip += ipAddress + inetAddress.getHostAddress() + "\n";
						
						if(ipAddress == "SiteLocalAddress: ") {
							ipvpn += ipAddress + inetAddress.getHostAddress() + "\n"; 
							//nom_conexion += " = " + inetAddress.getHostAddress();
						} 

					}
					
					//nom_conexion += "\n";
				}

			} catch (SocketException e) {
				ip = "Something Wrong! ";
			}
					
            String name = data.getString(0);
            String message = nom_conexion; //ipvpn;
            callbackContext.success(message);

            return true;

        } else {
            
            return false;

        }
    }
}
