package Paquete1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Principal {

	public static String tamanyo (long bytes) {
		double kb=bytes/1024.0;
		double mb= bytes /1024.0/1024.0;
		double gb= bytes/1024.0/1024.0/1024.0;
		
		if(gb>=1) {
			return String.format("%.2f GB", gb);
		} else if (mb >=1) {
			return String.format("%.2f MB", mb);
		} else if(kb >=1) {
			return String.format("%.2f KB", kb);
		}else {
			return bytes+" B";
		}
	}
	
	public static String info (File file) {
		String texto="";
		texto=texto + "Nombre: "+file.getName()+"\n";
		texto=texto + "Ruta: "+file.getAbsolutePath()+"\n";
		texto=texto + "Tipo: "+file.isDirectory()+"\n";
		texto=texto + "Tamaño: "+(file.isFile()?tamanyo(file.length()):"-")+"\n";
		texto=texto + "Última modificación (milisegundos desde 1/1/1970): "+file.lastModified() + "\n";
		texto=texto + "Lectura: "+(file.canRead()? "Si":"No")+"\n";
		texto=texto + "Escritura: "+(file.canWrite()? "Si":"No")+"\n";
		return texto;
	}
	
	public static boolean renombrar(File fichero,String nuevoNombre) {
		if(fichero==null) {
			return false;
		}
		if(nuevoNombre==null) {
			return false;
		}
		if(nuevoNombre.isBlank()) {
			return false;
		}
		File nuevo=new File(fichero.getParent(),nuevoNombre);
		boolean ok=fichero.renameTo(nuevo);
		return ok;
	}
	
	public static boolean copiarArchivo(File fichero) {
		if (fichero==null) {
			return false;
		}else if (!fichero.isFile()) {
			return false;
		}
		String nombre=fichero.getName();
		File copia=new File(fichero.getParent(),"copia_"+nombre);
		
		try {
			Files.copy(fichero.toPath(),copia.toPath(),StandardCopyOption.REPLACE_EXISTING);
			return true;
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean borrarArchivo(File fichero) {
		if (fichero == null) {
			return false;
		} else if (!fichero.isFile()) {
			return false;
		}
		return fichero.delete();
	}
	
	public static boolean borrarDirectorio(File carpeta) {
		if (carpeta==null) {
			return false;
		}else if(!carpeta.isDirectory()) {
			return false;
		}
		File[]archivos=carpeta.listFiles();
		if(archivos!=null) {
			for(File file:archivos) {
				if(file.isDirectory()) {
					borrarDirectorio(file);
				}else {
					file.delete();
				}
			}
		}
		return carpeta.delete();
	}
	
	public static String leerTexto(File fichero) {
		if (fichero ==null) {
			return "Archivo no valido";
		} else if (!fichero.isFile()) {
			return "Archivo no valido";
		}
		String texto="";
		try {
			FileReader fr= new FileReader(fichero);
			int valor=fr.read();
			while(valor!=-1) {
				texto=texto +(char) valor;
				valor=fr.read();
			}
			fr.close();
		} catch(IOException e) {
			texto= "Error al leer el archivo:"+e.getMessage();
		}
		return texto;
	}
	
	public static boolean escribirTexto(File fichero, String texto) {
		if(fichero==null) {
			return false;
		}
		if (texto==null) {
			return false;
		}
		try {
			Files.writeString(fichero.toPath(),texto);
			return true;
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}