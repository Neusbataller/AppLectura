package Paquete1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import java.awt.FlowLayout;
import java.awt.*;



public class Botones {

	private JFrame frame;
	
	private JLabel lblDirectorio;
	private JList<java.io.File> listaArchivos;
	private DefaultListModel<java.io.File> modelo;
	private JTextArea areaTexto;
	
	private JButton btnOpen;
	private JButton btnNewFolder;
	private JButton btnNewFile;
	private JButton btnFolderOptions;
	private JButton btnFileOptions;
	private JButton btnSearch;
	private JButton btnReplace;
	private JButton btnSave;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Botones window = new Botones();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Botones() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		btnOpen = new JButton("Open");
		btnOpen.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(btnOpen, BorderLayout.WEST);
		
		lblDirectorio = new JLabel("Directorio no seleccionado");
		panel.add(lblDirectorio, BorderLayout.CENTER);
		
		JPanel panelCentro = new JPanel();
		frame.getContentPane().add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0,0,0};
		gbl_panelCentro.rowHeights = new int[]{0,0};
		gbl_panelCentro.columnWeights = new double[]{1.0,0.0, 1.0};
		gbl_panelCentro.rowWeights = new double[]{1.0, 0.0};
		panelCentro.setLayout(gbl_panelCentro);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.weighty = 1.0;
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.insets = new Insets(0, 3, 5, 3);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panelCentro.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setMinimumSize(new Dimension(160,150));
		scrollPane.setPreferredSize(null);
		panel_1.add(scrollPane);
		
		modelo=new DefaultListModel<>();
		listaArchivos = new JList<>(modelo);
		scrollPane.setViewportView(listaArchivos);
		gbc_panel_1.weightx=1.0;
		
		JPanel panel_2Botones = new JPanel();
		GridBagConstraints gbc_panel_2Botones = new GridBagConstraints();
		gbc_panel_2Botones.weighty = 1.0;
		gbc_panel_2Botones.weightx = 0.0;
		gbc_panel_2Botones.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_2Botones.insets = new Insets(0, 3, 5, 3);
		gbc_panel_2Botones.gridx = 1;
		gbc_panel_2Botones.gridy = 0;
		panelCentro.add(panel_2Botones, gbc_panel_2Botones);
		panel_2Botones.setLayout(new GridLayout(8, 1, 0, 0));
		
		btnNewFolder = new JButton("New Folder");
		btnNewFile = new JButton("New File");
		btnFolderOptions = new JButton("FolderOptions");
		btnFileOptions = new JButton("File Options");
		panel_2Botones.add(btnNewFolder);
        panel_2Botones.add(btnNewFile);
        panel_2Botones.add(btnFolderOptions);
        panel_2Botones.add(btnFileOptions);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setMinimumSize(new Dimension(300, 150));
		scrollPane_1.setPreferredSize(null);
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.weighty = 1.0;
		gbc_scrollPane_1.weightx = 1.0;
		gbc_scrollPane_1.anchor = GridBagConstraints.WEST;
		gbc_scrollPane_1.insets = new Insets(0, 3, 5, 5);
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 0;
		panelCentro.add(scrollPane_1, gbc_scrollPane_1);
		
		areaTexto = new JTextArea();
		areaTexto.setWrapStyleWord(true);
		areaTexto.setLineWrap(true);
		scrollPane_1.setViewportView(areaTexto);
		
		
		JPanel panelBottom = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelBottom.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(10);
		GridBagConstraints gbc_panelBottom = new GridBagConstraints();
		gbc_panelBottom.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelBottom.gridwidth = 3;
		gbc_panelBottom.weightx = 1.0;
		gbc_panelBottom.gridx = 0;
		gbc_panelBottom.gridy = 1;
		gbc_panelBottom.fill=GridBagConstraints.HORIZONTAL;
		panelCentro.add(panelBottom, gbc_panelBottom);
		
		
		btnSearch = new JButton("Search");
		panelBottom.add(btnSearch);
		btnReplace = new JButton("Replace");
		panelBottom.add(btnReplace);
		btnSave = new JButton("Save");
		panelBottom.add(btnSave);
		

	}
    public JFrame getFrame() { return frame; }
    public JLabel getLblDirectorio() { return lblDirectorio; }
    public JList<java.io.File> getListaArchivos() { return listaArchivos; }
    public DefaultListModel<java.io.File> getModelo() { return modelo; }
    public JTextArea getAreaTexto() { return areaTexto; }
    public JButton getBtnOpen() { return btnOpen; }
    public JButton getBtnNewFolder() { return btnNewFolder; }
    public JButton getBtnNewFile() { return btnNewFile; }
    public JButton getBtnFolderOptions() { return btnFolderOptions; }
    public JButton getBtnFileOptions() { return btnFileOptions; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnReplace() { return btnReplace; }
    public JButton getBtnSave() { return btnSave; }

}
