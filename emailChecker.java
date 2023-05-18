import com.sanctionco.jmail.JMail;
import org.apache.commons.validator.EmailValidator;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class emailChecker extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JButton btGenerarFichero;

	public JLabel lbImagen;

	public JLabel lbTitulo;

	public JLabel lbSubTitulo;

	public JLabel lbRutaFichero;

	public JLabel IbNombreFicheroEntrada;

	public JLabel IbNombreFicheroSalida;

	public JCheckBox chxUsuariosAepd;

	
	static String fichero_salida_emailsNovalidos = "emailsNoValidos.csv";
	static String fichero_salida_emailsValidos = "emailsValidos.csv";
	static String nombreFichero;
	static String rutaFichero;

	static String regexp2 ="^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*\" \n" +
			"        + \"@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					emailChecker frame = new emailChecker();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public emailChecker() {

		// Parametros asociados a la ventana
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 310);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		JButton btnSeleccionar = new JButton("Seleccionar Fichero Emails a Comprobar");
		btnSeleccionar.setBounds(21, 100, 300, 20);
		contentPane.add(btnSeleccionar);
		setDefaultCloseOperation(3);
		getContentPane().setLayout((LayoutManager) null);
		lbImagen = new JLabel();
		lbImagen.setBounds(21, 11, 405, 60);
		lbImagen.setIcon(new ImageIcon(getClass().getResource("/aepd.png")));
		getContentPane().add(lbImagen);
		lbRutaFichero = new JLabel();
		lbRutaFichero.setBounds(21, 120, 400, 39);
		lbRutaFichero.setFont(new Font("Times New Roman", 1, 14));
		lbRutaFichero.setText("Ruta de destino");
		getContentPane().add(lbRutaFichero);
		IbNombreFicheroEntrada = new JLabel();
		IbNombreFicheroEntrada.setBounds(21, 150, 400, 39);
		IbNombreFicheroEntrada.setFont(new Font("Times New Roman", 1, 14));
		IbNombreFicheroEntrada.setText("Fichero de Entrada");
		getContentPane().add(IbNombreFicheroEntrada);
		btGenerarFichero = new JButton();
		btGenerarFichero.setBounds(21, 220, 180, 23);
		btGenerarFichero.setText("Generar Fichero");
		getContentPane().add(btGenerarFichero);
		chxUsuariosAepd=new JCheckBox(" Descartar Usuarios AEPD");
		chxUsuariosAepd.setBounds(18,180,200,30);
		getContentPane().add(chxUsuariosAepd);


		btGenerarFichero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Leemos fichero de entrada
				try {
					procesarCSVEmails();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				//Mostramos aviso de que hemos terminado
				JOptionPane.showMessageDialog(null, "Ficheros Generados correctamente");
			}
	
			private void procesarCSVEmails() throws IOException {
				EmailValidator validator = EmailValidator.getInstance();
				/**Preparamos programa para leer el fichero y escribir en dos ficheros: uno de emails válidos
				 *  y otro de no válidoss
				 */
				File csvEntrada = new File(String.valueOf(rutaFichero) + "\\" + nombreFichero);
				FileReader fr = null;
				BufferedReader br = null;
				FileWriter fichero_respuesta_valido = null;
				FileWriter fichero_respuesta_novalido = null;
				PrintWriter pw1 = null;
				PrintWriter pw2 = null;
				fichero_respuesta_valido = new FileWriter(
						String.valueOf(rutaFichero) + "\\" + fichero_salida_emailsValidos);
				fichero_respuesta_novalido = new FileWriter(
						String.valueOf(rutaFichero) + "\\" + fichero_salida_emailsNovalidos);
				pw1 = new PrintWriter(fichero_respuesta_valido);
				pw2 = new PrintWriter(fichero_respuesta_novalido);

				try {
					fr = new FileReader(csvEntrada);
					br = new BufferedReader(fr);
					String linea;
					while ((linea = br.readLine()) != null) {
						linea = linea.trim();


						if ((linea.endsWith("agpd.es") || linea.endsWith("aepd.es")) && chxUsuariosAepd.isSelected()) {
							//No hacemos nada. A estos e le manda vía lista_aepd@aepd.es
						}
						else {

							// validar email
							if (!JMail.isValid(linea.trim()) || !validator.isValid(linea) || linea.toLowerCase().contains("á") || linea.toLowerCase().contains("é")
									|| linea.toLowerCase().contains("í") || linea.toLowerCase().contains("ó")
									|| linea.toLowerCase().contains("ú") || linea.toLowerCase().endsWith(".ocm")
									|| linea.toLowerCase().endsWith(".comi") || linea.toLowerCase().endsWith(".ocm")
									|| linea.toLowerCase().endsWith(".mju") || linea.toLowerCase().endsWith(".ese")
							) {
								//imprimimos en fichero de no válido
								pw2.println(linea.trim());
							} else {
								//Imprimimos en fichero de válido
								pw1.println(linea.trim());
							}
						}
					}
					fichero_respuesta_valido.close();
					fichero_respuesta_novalido.close();
				}
				catch (IOException e3) {
					e3.printStackTrace();
				}
				br.close();
				fr.close();
			}

		});

		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				int r = j.showOpenDialog(null);
				if (r == 0) {
					nombreFichero = j.getSelectedFile().getName();
					rutaFichero = j.getSelectedFile().getParentFile().toString();
					IbNombreFicheroEntrada.setText(j.getSelectedFile().getName());
					lbRutaFichero.setText(j.getSelectedFile().getParentFile().toString());
				}

			}

		});
	}

}