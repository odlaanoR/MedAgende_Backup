package telasSistema.Administrador;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaAdminEdicaoUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nometextField;
	private JTextField cpftextField;
	private JTextField emailtextField;
	private JTextField datanascimentotextField;
	private JTextField TelefonetextField;
	private JTextField ceptextField;
	private JTextField cidadetextField;
	private JTextField bairrotextField;
	private JTextField ruatextField;
	private JTextField numerocasatextField;
	private JTextField complementotextField;
	private JTextField planoSaudetextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaAdminEdicaoUsuario frame = new TelaAdminEdicaoUsuario();
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
	public TelaAdminEdicaoUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 454);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().setBackground(new Color(170, 255, 255));
		getContentPane().setLayout(null);
		
		JPanel contentPane_1 = new JPanel();
		contentPane_1.setLayout(null);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane_1.setBackground(new Color(204, 253, 255));
		contentPane_1.setBounds(0, 0, 726, 415);
		getContentPane().add(contentPane_1);
		
		JLabel edicaoUsuario = new JLabel("Edição Usuario");
		edicaoUsuario.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		edicaoUsuario.setBounds(284, 11, 349, 68);
		contentPane_1.add(edicaoUsuario);
		
		JLabel PreenchaDados = new JLabel("Preencha os dados do paciente");
		PreenchaDados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		PreenchaDados.setBounds(287, 65, 180, 39);
		contentPane_1.add(PreenchaDados);
		
		nometextField = new JTextField();
		nometextField.setColumns(10);
		nometextField.setBounds(61, 143, 160, 18);
		contentPane_1.add(nometextField);
		
		cpftextField = new JTextField();
		cpftextField.setColumns(10);
		cpftextField.setBounds(61, 191, 160, 18);
		contentPane_1.add(cpftextField);
		
		emailtextField = new JTextField();
		emailtextField.setColumns(10);
		emailtextField.setBounds(61, 245, 160, 18);
		contentPane_1.add(emailtextField);
		
		datanascimentotextField = new JTextField();
		datanascimentotextField.setColumns(10);
		datanascimentotextField.setBounds(61, 298, 160, 18);
		contentPane_1.add(datanascimentotextField);
		
		TelefonetextField = new JTextField();
		TelefonetextField.setColumns(10);
		TelefonetextField.setBounds(299, 143, 160, 18);
		contentPane_1.add(TelefonetextField);
		
		ceptextField = new JTextField();
		ceptextField.setColumns(10);
		ceptextField.setBounds(299, 191, 160, 18);
		contentPane_1.add(ceptextField);
		
		cidadetextField = new JTextField();
		cidadetextField.setColumns(10);
		cidadetextField.setBounds(299, 245, 160, 18);
		contentPane_1.add(cidadetextField);
		
		bairrotextField = new JTextField();
		bairrotextField.setColumns(10);
		bairrotextField.setBounds(299, 298, 160, 18);
		contentPane_1.add(bairrotextField);
		
		ruatextField = new JTextField();
		ruatextField.setColumns(10);
		ruatextField.setBounds(527, 143, 160, 18);
		contentPane_1.add(ruatextField);
		
		numerocasatextField = new JTextField();
		numerocasatextField.setColumns(10);
		numerocasatextField.setBounds(527, 191, 160, 18);
		contentPane_1.add(numerocasatextField);
		
		complementotextField = new JTextField();
		complementotextField.setColumns(10);
		complementotextField.setBounds(527, 245, 160, 18);
		contentPane_1.add(complementotextField);
		
		planoSaudetextField = new JTextField();
		planoSaudetextField.setColumns(10);
		planoSaudetextField.setBounds(527, 298, 160, 18);
		contentPane_1.add(planoSaudetextField);
		
		JLabel lblNewLabel = new JLabel("Nome Completo");
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblNewLabel.setBounds(93, 121, 109, 12);
		contentPane_1.add(lblNewLabel);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblCpf.setBounds(125, 171, 30, 12);
		contentPane_1.add(lblCpf);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblEmail.setBounds(125, 223, 30, 12);
		contentPane_1.add(lblEmail);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento");
		lblDataDeNascimento.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblDataDeNascimento.setBounds(93, 276, 109, 12);
		contentPane_1.add(lblDataDeNascimento);
		
		JLabel lblTelefoneParaContato = new JLabel("Telefone Para Contato");
		lblTelefoneParaContato.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblTelefoneParaContato.setBounds(320, 121, 109, 12);
		contentPane_1.add(lblTelefoneParaContato);
		
		JLabel lblCep = new JLabel("CEP");
		lblCep.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblCep.setBounds(367, 171, 30, 12);
		contentPane_1.add(lblCep);
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblCidade.setBounds(365, 223, 42, 12);
		contentPane_1.add(lblCidade);
		
		JLabel lblBairro = new JLabel("Bairro");
		lblBairro.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblBairro.setBounds(365, 276, 42, 12);
		contentPane_1.add(lblBairro);
		
		JLabel lblRua = new JLabel("Rua");
		lblRua.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblRua.setBounds(596, 121, 24, 12);
		contentPane_1.add(lblRua);
		
		JLabel lblN = new JLabel("Nº");
		lblN.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblN.setBounds(603, 169, 30, 12);
		contentPane_1.add(lblN);
		
		JLabel lblComplemento = new JLabel("Complemento");
		lblComplemento.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblComplemento.setBounds(577, 223, 66, 12);
		contentPane_1.add(lblComplemento);
		
		JLabel lblPlanoDeSade = new JLabel("Plano de Saúde (Opcional)");
		lblPlanoDeSade.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		lblPlanoDeSade.setBounds(537, 276, 133, 12);
		contentPane_1.add(lblPlanoDeSade);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensagem="Por favor, preencha os seguintes campos obrigatórios:\n";
				int camposEmBranco=0;
				if (nometextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="nome\n";}
				if (cpftextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="CPF\n";
				}
				if (emailtextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="email\n";
				}
				if (datanascimentotextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="Data de nascimento\n";
				}
				
				if (TelefonetextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="Telefone\n";
				}
				if (ceptextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="Cep\n";
				}
				if (cidadetextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="cidade\n";
				}
				if (bairrotextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="Bairro\n";
				}
				if (ruatextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="Rua\n";
				}
				if (numerocasatextField.getText().trim().isEmpty()) {
					camposEmBranco++;
					mensagem+="n°\n";
				}
				if (camposEmBranco>0) {
					JOptionPane.showMessageDialog(null, mensagem,"Erro de Validação",
				            JOptionPane.WARNING_MESSAGE 	);
					
				return;
				}else {
					JOptionPane.showMessageDialog(null, "Especialidade adicionada com sucesso, voltando ao menu de gestão de usuarios...","Especialidade adicionada",JOptionPane.INFORMATION_MESSAGE);
				}
			
				TelaAdministradorGestaoUsuarios tela= new TelaAdministradorGestaoUsuarios();
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				dispose();
			
			}
		});
		btnCadastrar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnCadastrar.setBounds(516, 370, 104, 20);
		contentPane_1.add(btnCadastrar);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAdministradorGestaoUsuarios tela= new TelaAdministradorGestaoUsuarios();
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				dispose();
			}
		});
		btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnVoltar.setBounds(93, 371, 104, 20);
		contentPane_1.add(btnVoltar);

	}
}
