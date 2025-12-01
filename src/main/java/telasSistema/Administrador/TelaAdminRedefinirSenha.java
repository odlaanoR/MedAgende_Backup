package telasSistema.Administrador;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaAdminRedefinirSenha extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaAdminRedefinirSenha frame = new TelaAdminRedefinirSenha();
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
	public TelaAdminRedefinirSenha() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setBackground(new Color(170, 255, 255));
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Selecionar Usuario");
		btnNewButton.setBounds(272, 50, 217, 67);
		getContentPane().add(btnNewButton);
		
		JButton btnPreencherComO = new JButton("Preencher com o email de recuperação");
		btnPreencherComO.setBounds(272, 171, 217, 67);
		getContentPane().add(btnPreencherComO);
		
		JButton btnTokenRecuperacao = new JButton("Enviar Token de Recuperação");
		btnTokenRecuperacao.setBounds(272, 298, 217, 67);
		getContentPane().add(btnTokenRecuperacao);
		
		JButton btnvoltar = new JButton("Voltar");
		btnvoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAdministradorGestaoUsuarios tela= new TelaAdministradorGestaoUsuarios();
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				dispose();	
			}
		});
		btnvoltar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnvoltar.setBounds(29, 360, 105, 31);
		getContentPane().add(btnvoltar);

	}

}
