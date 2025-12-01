package telasSistema;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import telasSistema.Administrador.TelaAdministradorGestaoUsuarios;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastroUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroUsuarios frame = new TelaCadastroUsuarios();
					frame.setLocationRelativeTo(null);
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
	public TelaCadastroUsuarios() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 454);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 253, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(268, 236, 196, 45);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(263, 129, 201, 44);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Matricula");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblNewLabel.setBounds(268, 211, 79, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("CPF");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(268, 104, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Cadastro de novos Usuarios/Profissionais");
		lblNewLabel_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(206, 27, 400, 53);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Voltar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAdministradorGestaoUsuarios tela= new TelaAdministradorGestaoUsuarios();
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(48, 348, 99, 35);
		contentPane.add(btnNewButton);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(585, 348, 99, 35);
		contentPane.add(btnCadastrar);


	}

}
