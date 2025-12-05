package Paquete1;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class AppController {

	private final Botones view;
	private File dirActual;
	
	public AppController(Botones view) {
		this.view=view;
		wireListeners();
	}
	private void wireListeners() {
		view.getBtnOpen().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed (ActionEvent ev) {
				JFileChooser fc=new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnCode=fc.showOpenDialog(view.getFrame());
				if(returnCode==JFileChooser.APPROVE_OPTION) {
					setDirActual(fc.getSelectedFile());
				}
			}
		});
		
		view.getListaArchivos().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent ev) {
				if(ev.getValueIsAdjusting())return;
				File file=view.getListaArchivos().getSelectedValue();
				if(file==null)
					return;
				if("..".equals(file.getName())) {
					view.getAreaTexto().setText("Subir al directorio");
				}else {
					view.getAreaTexto().setText(Principal.info(file));
				}
			}
		});
		
		view.getListaArchivos().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent raton) {
				if(raton.getClickCount()!=2) 
					return;
				File file=view.getListaArchivos().getSelectedValue();
				if(file==null)
					return;
				if("..".equals(file.getName())) {
					if(dirActual==null) 
						return;
						File padre =dirActual.getParentFile();
						if(padre !=null) {
							setDirActual(padre);
						}
					}else if(file.isDirectory()) {
						setDirActual(file);
					}else if(file.isFile()) {
						view.getAreaTexto().setText(Principal.leerTexto(file));
					}
			}
		});
		
		view.getBtnNewFolder().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkDir())
					return;
				String nombre=JOptionPane.showInputDialog(view.getFrame(), "Nombre de la carpeta:");
				if(nombre==null) {
					return;
				}
				if(nombre.isBlank()) {
					return;
				}
				File carpeta=new File(dirActual,nombre);
				if(carpeta.mkdir()) {
					cargarLista();
				}else {
					error("No se pudo crear la carpeta");
				}
			}
		});
		
		view.getBtnNewFile().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!checkDir()) {
					return;
				}
				String nombre=JOptionPane.showInputDialog(view.getFrame(),"Nombre del fichero");
				if(nombre==null) {
					return;
				}
				if(nombre.isBlank()) {
					return;
				}
				try {
					File file=new File(dirActual,nombre);
					if(file.createNewFile()) {
						cargarLista();
					}else {
						error("No se pudo crear el fichero");
					}
				}catch(IOException ex) {
					error(ex.getMessage());
				}
			}
		});
		
		view.getBtnFolderOptions().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file= view.getListaArchivos().getSelectedValue();
				
				if(file==null) {
					info("Selecciona un directorio");
					return;
				}
				if("..".equals(file.getName())) {
					info("Selecciona un directorio");
					return;
				}
				if(!file.isDirectory()) {
					info("Selecciona un directorio");
					return;
				}
				String[]opciones= {"Explorar","Renombrar","Suprimir"};
				String seleccion=(String) JOptionPane.showInputDialog(view.getFrame(),"Acción carpeta:","Carpeta",JOptionPane.QUESTION_MESSAGE,null,opciones,opciones[0]);
				if(seleccion==null) {
					return;
				}
				if("Explorar".equals(seleccion)) {
					setDirActual(file);
				}else if("Renombrar".equals(seleccion)) {
					boolean ok=sudo("Renombrar carpeta","Escribe sudo para confirmar");
					if(!ok) {
						return;
					}
					String nuevo= JOptionPane.showInputDialog(view.getFrame(),"Nuevo nombre: ");
					if(nuevo ==null) {
						return;
					}
					if(nuevo.isBlank()) {
						return;
					}
					boolean ren=Principal.renombrar(file, nuevo);
					if(ren) {
						cargarLista();
					}else {
						error("No se pudo renombrar la carpeta");
					}
				}else if("Suprimir".equals(seleccion)) {
					boolean ok=sudo("Suprimir carpeta","Se borrará todo el contenido.\\nEscribe sudo para confirmar");
					if (!ok) {
                        return;
                    }
                    boolean borrado = Principal.borrarDirectorio(file);
                    if (borrado) {
                        cargarLista();
                    } else {
                        error("No se pudo Suprimir la carpeta.");
                    }
				}
			}
		});
		
		view.getBtnFileOptions().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = view.getListaArchivos().getSelectedValue();

                if (file == null) {
                    info("Selecciona un fichero.");
                    return;
                }
                if (!file.isFile()) {
                    info("Selecciona un fichero.");
                    return;
                }
                String[] opciones = {"Mostrar contenido", "Copiar", "Renombrar", "Suprimir"};
                String seleccion = (String) JOptionPane.showInputDialog(
                        view.getFrame(), "Acción fichero:", "Fichero",
                        JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                if (seleccion == null) {
                    return;
                }
                if ("Mostrar contenido".equals(seleccion)) {
                    view.getAreaTexto().setText(Principal.leerTexto(file));
                } else if ("Copiar".equals(seleccion)) {
                    boolean copiado = Principal.copiarArchivo(file);
                    if (copiado) {
                        cargarLista();
                    } else {
                        error("No se pudo copiar el fichero.");
                    }
                } else if ("Renombrar".equals(seleccion)) {
                    boolean ok = sudo("Renombrar fichero", "Escribe sudo para confirmar");
                    if (!ok) {
                        return;
                    }
                    String nuevo = JOptionPane.showInputDialog(view.getFrame(), "Nuevo nombre (con extensión):");
                    if (nuevo == null) {
                        return;
                    }
                    if (nuevo.isBlank()) {
                        return;
                    }
                    boolean ren = Principal.renombrar(file, nuevo);
                    if (ren) {
                        cargarLista();
                    } else {
                        error("No se pudo renombrar el fichero.");
                    }
                } else if ("Suprimir".equals(seleccion)) {
                    boolean ok = sudo("Suprimir fichero", "Escribe sudo para confirmar");
                    if (!ok) {
                        return;
                    }
                    boolean borrado = Principal.borrarArchivo(file);
                    if (borrado) {
                        cargarLista();
                    } else {
                        error("No se pudo Suprimir el fichero.");
                    }
                }
            }
        });
		
		view.getBtnSearch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = JOptionPane.showInputDialog(view.getFrame(), "Buscar (case-sensitive):");
                if (search == null) {
                    return;
                }
                if (search.isEmpty()) {
                    return;
                }
                resaltar(view.getAreaTexto(), search);
            }
        });
		
		view.getBtnReplace().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buscar = JOptionPane.showInputDialog(view.getFrame(), "Buscar (case-sensitive):");
                if (buscar == null) {
                    return;
                }
                String reemplazo = JOptionPane.showInputDialog(view.getFrame(), "Reemplazar por:");
                if (reemplazo == null) {
                    return;
                }
                String nuevoTexto = view.getAreaTexto().getText().replace(buscar, reemplazo);
                view.getAreaTexto().setText(nuevoTexto);
            }
        });
		
		view.getBtnSave().addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        File file = view.getListaArchivos().getSelectedValue();

		        if (file == null) {
		            if (!checkDir()) return;
		            String nombre = JOptionPane.showInputDialog(view.getFrame(), "Guardar como (nombre.ext):");
		            if (nombre == null || nombre.isBlank()) return;

		            File nuevo = new File(dirActual, nombre);
		            boolean ok = Principal.escribirTexto(nuevo, view.getAreaTexto().getText());
		            if (ok) {
		                cargarLista();
		                view.getListaArchivos().setSelectedValue(nuevo, true);
		            } else {
		                error("No se pudo guardar.");
		            }
		            return;
		        }
                
                if (!file.isFile()) {
                    info("Selecciona un fichero para sobrescribir o usa Guardar como.");
                    return;
                }
                String[] opciones = {"Sobrescribir", "Guardar como..."};
                int resp = JOptionPane.showOptionDialog(view.getFrame(), "¿Cómo quieres guardar?", "Guardar",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

                if (resp == 0) {
                    boolean ok = Principal.escribirTexto(file, view.getAreaTexto().getText());
                    if (ok) {
                        cargarLista();
                    } else {
                        error("No se pudo guardar.");
                    }
                } else if (resp == 1) {
                    String nombre = JOptionPane.showInputDialog(view.getFrame(),"Nombre del nuevo fichero:");
                    if (nombre == null) {
                        return;
                    }
                    if (nombre.isBlank()) {
                        return;
                    }
                    File nuevo = new File(dirActual, nombre);
                    boolean ok = Principal.escribirTexto(nuevo, view.getAreaTexto().getText());
                    if (ok) {
                        cargarLista();
                        view.getListaArchivos().setSelectedValue(nuevo, true);
                    } else {
                        error("No se pudo guardar.");
                    }
                }
            }
        });
    }
	
	private void setDirActual(File dir) {
        if (dir == null) {
            return;
        }
        if (!dir.isDirectory()) {
            return;
        }
        dirActual = dir;
        view.getLblDirectorio().setText(dir.getAbsolutePath());
        cargarLista();
        view.getAreaTexto().setText("");
    }
	
	private void cargarLista() {
        DefaultListModel<File> m = view.getModelo();
        m.clear();

        if (dirActual == null) {
            return;
        }

        File padre = dirActual.getParentFile();
        if (padre != null) {
            m.addElement(new File(".."));
        }

        File[] files = dirActual.listFiles();
        if (files != null) {
            Arrays.sort(files, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            for (File f : files) {
                m.addElement(f);
            }
        }
    }
	
	 private boolean checkDir() {
	        if (dirActual == null) {
	            info("Primero elige un directorio con Open.");
	            return false;
	        }
	        return true;
	    }
	 private boolean sudo(String titulo, String msg) {
	        String s = JOptionPane.showInputDialog(view.getFrame(), msg, titulo, JOptionPane.WARNING_MESSAGE);
	        if (s == null) {
	            return false;
	        }
	        if (!"sudo".equals(s)) {
	            return false;
	        }
	        return true;
	    }

	    private void info(String m) {
	        JOptionPane.showMessageDialog(view.getFrame(), m, "Info", JOptionPane.INFORMATION_MESSAGE);
	    }

	    private void error(String m) {
	        JOptionPane.showMessageDialog(view.getFrame(), m, "Error", JOptionPane.ERROR_MESSAGE);
	    }

	    private void resaltar(JTextArea area, String q) {
	        Highlighter hl = area.getHighlighter();
	        hl.removeAllHighlights();
	        String txt = area.getText();
	        int idx = 0;
	        try {
	            while (true) {
	                int encontrado = txt.indexOf(q, idx);
	                if (encontrado < 0) {
	                    break;
	                }
	                hl.addHighlight(encontrado, encontrado + q.length(),
	                        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
	                idx = encontrado + q.length();
	            }
	        } catch (BadLocationException ignored) {
	        }
	    }
}
