package telasSistema.Secretaria;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import conexao.ConnectionFactory;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;


public class TelaSecretariaAgendar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField fieldNome;
	private JTextField fieldCpf;

	/**
	 * Create the panel.
	 */
	public TelaSecretariaAgendar() {
		setBounds(100, 100, 742, 454);
		getContentPane().setBackground(new Color(170, 255, 255));
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(204, 253, 255));
		panel.setBounds(0, 11, 729, 417);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAgendarConsulta = new JLabel("Agendar Consulta");
		lblAgendarConsulta.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblAgendarConsulta.setBounds(257, 31, 234, 54);
		panel.add(lblAgendarConsulta);
		
		JLabel lblPreenchaDados = new JLabel("Preencha os dados do paciente");
		lblPreenchaDados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblPreenchaDados.setBounds(288, 84, 203, 16);
		panel.add(lblPreenchaDados);
		
		JLabel lblNomePaciente = new JLabel("Nome do paciente");
		lblNomePaciente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblNomePaciente.setBounds(145, 187, 103, 16);
		panel.add(lblNomePaciente);
		
		JLabel lblCpfPaciente = new JLabel("CPF do paciente");
		lblCpfPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblCpfPaciente.setBounds(158, 160, 93, 16);
		panel.add(lblCpfPaciente);
		
		fieldNome = new JTextField();
		fieldNome.setBounds(251, 189, 265, 18);
		panel.add(fieldNome);
		fieldNome.setColumns(10);
		
		fieldCpf = new JTextField();
		fieldCpf.setBounds(251, 160, 265, 18);
		panel.add(fieldCpf);
		fieldCpf.setColumns(10);
		
		JButton btnProximo = new JButton("Próximo");
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensagemDeErro = "Por favor, preencha os seguintes campos obrigatórios:\n";
				int camposEmBranco=0;
				if (fieldNome.getText().trim().isEmpty()) {
			        camposEmBranco++;
			        mensagemDeErro += "- Nome\n"; 
			    }

			  
			    if (fieldCpf.getText().trim().isEmpty()) { 
			        camposEmBranco++;
			        mensagemDeErro += "- CPF\n";
			    }

			    
			    if (camposEmBranco > 0) {
			        
			        System.out.println(camposEmBranco + " campo(s) em branco. Abortando cadastro.");
			        
			        // 
			        JOptionPane.showMessageDialog(
			            null,
			            mensagemDeErro,
			            "Erro de Validação",
			            JOptionPane.WARNING_MESSAGE );
			        
			        return; 
			    
			}
			    TelaInfosAgendamento tela= new TelaInfosAgendamento();
			    tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				dispose();
			   
				//new TelaInfosAgendamento().setVisible(true);
				//dispose();
			}
		});
		btnProximo.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnProximo.setBounds(508, 349, 84, 20);
		panel.add(btnProximo);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPrincipalSecretaria tela = new TelaPrincipalSecretaria();
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				 dispose();
			}
		});
		btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btnVoltar.setBounds(98, 349, 84, 20);
		panel.add(btnVoltar);
		
		JButton btnCadastrarPaciente = new JButton("Cadastrar Paciente");
		btnCadastrarPaciente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaSecretariaCadastrarPaciente tela =new TelaSecretariaCadastrarPaciente();
				tela.setLocationRelativeTo(null);
				tela.setVisible(true);
				dispose();
			}
		});
		btnCadastrarPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		btnCadastrarPaciente.setBounds(300, 282, 137, 20);
		panel.add(btnCadastrarPaciente);
		
		JLabel lblCadastroPaciente = new JLabel("Primeira vez do paciente na clínica?");
		lblCadastroPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblCadastroPaciente.setBounds(98, 283, 203, 16);
		panel.add(lblCadastroPaciente);
		
		JButton Verificar = new JButton("Verificar");
		Verificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscarNome();
			}
		});
		Verificar.setBounds(522, 158, 89, 23);
		panel.add(Verificar);
		
		
	}
	private void buscarNome() {
		Connection conexao=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		try {
			conexao = ConnectionFactory.getConnection();
			 if (conexao == null) {
		            JOptionPane.showMessageDialog(this, 
		                "Erro ao conectar ao banco de dados", 
		                "Erro de Conexão", 
		                JOptionPane.ERROR_MESSAGE);
		            return;
		        }
		        
		        // 3. Validar que o CPF foi digitado
		        String cpf = fieldCpf.getText().trim();
		        if (cpf.isEmpty()) {
		            JOptionPane.showMessageDialog(this, 
		                "Digite um CPF para buscar", 
		                "Aviso", 
		                JOptionPane.WARNING_MESSAGE);
		            fieldCpf.requestFocus();
		            return;
		        
		        }
		        String sql = "SELECT nome FROM paciente WHERE cpf = ?";

		        ps = conexao.prepareStatement(sql);
		        ps.setString(1, cpf);
		        rs = ps.executeQuery();
		        if(rs.next()) {
		        	String nomeEncontrado=rs.getString("Nome");
		        	fieldNome.setText(nomeEncontrado);
		        JOptionPane.showMessageDialog(this, 
		                "Paciente encontrado!", 
		                "Sucesso", 
		                JOptionPane.INFORMATION_MESSAGE);
		        } else {
		            // Se não encontrou, limpa o campo e informa
		            fieldNome.setText("");
		            JOptionPane.showMessageDialog(this, 
		                "Paciente não encontrado com o CPF: " + cpf, 
		                "Paciente não encontrado", 
		                JOptionPane.WARNING_MESSAGE);
		            
		            // Opcional: focar no campo de nome para cadastro
		            fieldNome.requestFocus();
		        }
		        
		        
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}
}

		
		