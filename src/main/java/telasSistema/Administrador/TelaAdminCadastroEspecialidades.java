package telasSistema.Administrador;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaAdminCadastroEspecialidades extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaAdminCadastroEspecialidades frame = new TelaAdminCadastroEspecialidades();
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
	public TelaAdminCadastroEspecialidades() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 454);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 253, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnGestaoProfissionalButton = new JButton("Gestão de Profissionais(Médicos)");
		btnGestaoProfissionalButton.setBounds(279, 273, 245, 62);
		contentPane.add(btnGestaoProfissionalButton);
		
		JButton btnEditarEspecialidadesButton = new JButton("Editar especialidades existentes");
		btnEditarEspecialidadesButton.setBounds(279, 171, 245, 62);
		contentPane.add(btnEditarEspecialidadesButton);
		
		JButton btnAdicionarEspecialidades = new JButton("Adicionar especialidades");
		btnAdicionarEspecialidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAdminAdicionarEspecialidade frame = new TelaAdminAdicionarEspecialidade();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				dispose();
			}
		});
		btnAdicionarEspecialidades.setBounds(279, 77, 245, 62);
		contentPane.add(btnAdicionarEspecialidades);
		
		
		JLabel lblNewLabel = new JLabel("Cadastro de especialidades");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(244, 11, 325, 39);
		contentPane.add(lblNewLabel);
		
		JButton btnvoltarButton = new JButton("Voltar");
		btnvoltarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAdministradorGestaoUsuarios tela= new TelaAdministradorGestaoUsuarios();
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				dispose();
			}
		});
		btnvoltarButton.setBounds(34, 343, 117, 32);
		contentPane.add(btnvoltarButton);

	}
}
