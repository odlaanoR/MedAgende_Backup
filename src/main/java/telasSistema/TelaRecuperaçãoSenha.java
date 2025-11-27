package telasSistema;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaRecuperaçãoSenha extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnEnviar;
	private JButton btnReenviar;
	private JLabel lblNewLabel_2;
	private JButton btnPróximo;
	private JButton btnProximo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaRecuperaçãoSenha frame = new TelaRecuperaçãoSenha();
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
	public TelaRecuperaçãoSenha() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 728, 316);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Recuperação de Senha");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 22));
		lblNewLabel.setBounds(195, 10, 258, 62);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(45, 115, 238, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Insira aqui o Email para o qual devemos enviar o Token de Recuperação:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(35, 89, 434, 14);
		contentPane.add(lblNewLabel_1);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEnviar.setBounds(45, 146, 87, 20);
		contentPane.add(btnEnviar);
		
		btnReenviar = new JButton("Reenviar");
		btnReenviar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnReenviar.setBounds(142, 146, 119, 20);
		contentPane.add(btnReenviar);
		
		lblNewLabel_2 = new JLabel("Caso ultrapasse os 60s e não tenha recebido o Token , solicite o Reenvio ou verifique se preencheu o campo corretamente.");
		lblNewLabel_2.setBounds(10, 252, 702, 14);
		contentPane.add(lblNewLabel_2);
		
		btnProximo = new JButton("Próximo");
		btnProximo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInserirTolken tela = new TelaInserirTolken();
				tela.setVisible(true);
				dispose();
			}
		});
		btnProximo.setBounds(501, 208, 156, 20);
		contentPane.add(btnProximo);
		
	}
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
}






