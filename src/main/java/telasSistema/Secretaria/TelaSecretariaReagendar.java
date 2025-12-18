package telasSistema.Secretaria;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexao.ConnectionFactory;
import javax.swing.SwingConstants;


public class TelaSecretariaReagendar extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JTextField textField_1;
    private JButton btnBuscar;

    public TelaSecretariaReagendar() {
        setBounds(100, 100, 742, 454);
        getContentPane().setBackground(new Color(170, 255, 255));
        getContentPane().setLayout(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(204, 253, 255));
        panel.setBounds(0, 1, 729, 417);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblCancelarConsulta = new JLabel("Reagendar Consulta");
        lblCancelarConsulta.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblCancelarConsulta.setBounds(257, 26, 234, 54);
        panel.add(lblCancelarConsulta);
        
        JLabel lblPreenchaDados = new JLabel("Preencha os dados do paciente");
        lblPreenchaDados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPreenchaDados.setBounds(288, 84, 203, 16);
        panel.add(lblPreenchaDados);
        
        JLabel lblNomePaciente = new JLabel("Nome do paciente");
        lblNomePaciente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNomePaciente.setBounds(98, 161, 203, 16);
        panel.add(lblNomePaciente);
        
        JLabel lblCpfPaciente = new JLabel("CPF do paciente");
        lblCpfPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCpfPaciente.setBounds(108, 189, 203, 16);
        panel.add(lblCpfPaciente);
        
        textField = new JTextField();
        textField.setBounds(208, 161, 265, 18);
        panel.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(208, 189, 265, 18);
        panel.add(textField_1);
        textField_1.setColumns(10);
        
        // Botão Buscar CPF - NOVO
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnBuscar.setBounds(485, 189, 80, 18);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPacientePorCPF();
            }
        });
        panel.add(btnBuscar);
        
        // Label para status da busca - NOVO
        JLabel lblStatusBusca = new JLabel("");
        lblStatusBusca.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblStatusBusca.setForeground(Color.BLUE);
        lblStatusBusca.setBounds(208, 210, 265, 16);
        panel.add(lblStatusBusca);
        
        JButton btnProximo = new JButton("Próximo");
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mensagemDeErro = "Por favor, preencha os seguintes campos obrigatórios:\n";
                int camposEmBranco=0;
                
                if (textField.getText().trim().isEmpty()) {
                    camposEmBranco++;
                    mensagemDeErro += "- Nome\n"; 
                }

                if (textField_1.getText().trim().isEmpty()) { 
                    camposEmBranco++;
                    mensagemDeErro += "- CPF\n";
                }

                if (camposEmBranco > 0) {
                    System.out.println(camposEmBranco + " campo(s) em branco. Abortando cadastro.");
                    
                    JOptionPane.showMessageDialog(
                        null,
                        mensagemDeErro,
                        "Erro de Validação",
                        JOptionPane.WARNING_MESSAGE );
                    
                    return; 
                }
                
                // Validar se o CPF existe no sistema - NOVO
                String cpf = textField_1.getText().trim().replaceAll("[^0-9]", "");
                if (!validarCPFNoSistema(cpf)) {
                    int opcao = JOptionPane.showConfirmDialog(
                        TelaSecretariaReagendar.this,
                        "Este CPF não está cadastrado no sistema.\n" +
                        "Deseja cadastrar este paciente primeiro?",
                        "Paciente Não Encontrado",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (opcao == JOptionPane.YES_OPTION) {
                        // Aqui você pode abrir a tela de cadastro de paciente
                        JOptionPane.showMessageDialog(
                            TelaSecretariaReagendar.this,
                            "Abrir tela de cadastro de paciente...\n" +
                            "CPF: " + formatarCPF(cpf),
                            "Cadastro Necessário",
                            JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } else {
                        return;
                    }
                }
                
                // Verificar se o paciente tem consultas agendadas - NOVO
                if (!verificarConsultasAgendadas(cpf)) {
                    int opcao = JOptionPane.showConfirmDialog(
                        TelaSecretariaReagendar.this,
                        "Este paciente não possui consultas agendadas.\n" +
                        "Deseja prosseguir para agendar uma nova consulta?",
                        "Sem Consultas Agendadas",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (opcao != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
                
                TelaInfosAgendamento tela = new TelaInfosAgendamento();
                tela.setLocationRelativeTo(null);
                tela.setVisible(true);
                dispose();
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
        
        // Botão Limpar - NOVO
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnLimpar.setBounds(485, 161, 80, 18);
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("");
                textField_1.setText("");
                lblStatusBusca.setText("");
                textField_1.requestFocus();
            }
        });
        panel.add(btnLimpar);
        
        // Listener para busca automática quando o CPF perde o foco - NOVO
        textField_1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!textField_1.getText().trim().isEmpty()) {
                    buscarPacientePorCPF();
                }
            }
        });
    }
    
    /**
     * Método para buscar paciente por CPF
     */
    private void buscarPacientePorCPF() {
        String cpf = textField_1.getText().trim();
        
        if (cpf.isEmpty()) {
            textField.setText("");
            JOptionPane.showMessageDialog(this,
                "Por favor, digite um CPF para buscar.",
                "CPF Vazio",
                JOptionPane.WARNING_MESSAGE);
            textField_1.requestFocus();
            return;
        }
        
        // Formatar CPF (remover caracteres não numéricos)
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Validar formato do CPF
        if (cpf.length() != 11) {
            textField.setText("");
            JOptionPane.showMessageDialog(this,
                "CPF inválido! Deve conter 11 dígitos.\n" +
                "Exemplo: 123.456.789-01",
                "CPF Inválido",
                JOptionPane.WARNING_MESSAGE);
            textField_1.requestFocus();
            return;
        }
        
        // Desabilitar botão durante a busca
        btnBuscar.setEnabled(false);
        btnBuscar.setText("Buscando...");
        
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            if (conexao == null) {
                JOptionPane.showMessageDialog(this,
                    "Erro ao conectar ao banco de dados.",
                    "Erro de Conexão",
                    JOptionPane.ERROR_MESSAGE);
                btnBuscar.setEnabled(true);
                btnBuscar.setText("Buscar");
                return;
            }
            
            // Buscar paciente pelo CPF na tabela paciente
            String sql = "SELECT Nome, Id_Paciente FROM paciente WHERE CPF = ?";
            ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String nomePaciente = rs.getString("Nome");
                int idPaciente = rs.getInt("Id_Paciente");
                
                // Preencher o campo nome
                textField.setText(nomePaciente);
                
                // Atualizar status da busca
                JOptionPane.showMessageDialog(this,
                    "Paciente encontrado!\n" +
                    "Nome: " + nomePaciente + "\n" +
                    "ID: " + idPaciente + "\n" +
                    "CPF: " + formatarCPF(cpf),
                    "Paciente Encontrado",
                    JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                textField.setText("");
                
                int opcao = JOptionPane.showConfirmDialog(this,
                    "CPF não encontrado no sistema!\n\n" +
                    "CPF: " + formatarCPF(cpf) + "\n\n" +
                    "Deseja cadastrar este paciente?",
                    "Paciente Não Encontrado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (opcao == JOptionPane.YES_OPTION) {
                    // Aqui você pode abrir a tela de cadastro de paciente
                    JOptionPane.showMessageDialog(this,
                        "Abrir tela de cadastro de paciente...\n" +
                        "CPF será preenchido automaticamente.",
                        "Cadastro de Paciente",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao buscar paciente: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Reabilitar botão
            btnBuscar.setEnabled(true);
            btnBuscar.setText("Buscar");
            
            // Fechar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Validar se o CPF existe no sistema
     */
    private boolean validarCPFNoSistema(String cpf) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            if (conexao == null) return false;
            
            String sql = "SELECT COUNT(*) as total FROM paciente WHERE CPF = ?";
            ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total") > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao validar CPF: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Verificar se o paciente tem consultas agendadas
     */
    private boolean verificarConsultasAgendadas(String cpf) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            if (conexao == null) return false;
            
            // Primeiro buscar o ID do paciente
            String sqlBuscarId = "SELECT Id_Paciente FROM paciente WHERE CPF = ?";
            ps = conexao.prepareStatement(sqlBuscarId);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int idPaciente = rs.getInt("Id_Paciente");
                
                // Fechar recursos
                rs.close();
                ps.close();
                
                // Agora buscar consultas agendadas
                String sqlConsultas = "SELECT COUNT(*) as total FROM agenda " +
                                    "WHERE Id_Paciente = ? AND Status = 'agendada' " +
                                    "AND Data_Consulta >= CURDATE()";
                
                ps = conexao.prepareStatement(sqlConsultas);
                ps.setInt(1, idPaciente);
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao verificar consultas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /**
     * Formatar CPF no padrão 000.000.000-00
     */
    private String formatarCPF(String cpf) {
        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + 
                   cpf.substring(3, 6) + "." + 
                   cpf.substring(6, 9) + "-" + 
                   cpf.substring(9, 11);
        }
        return cpf;
    }
    
    // Método main para teste
    public static void main(String[] args) {
        TelaSecretariaReagendar tela = new TelaSecretariaReagendar();
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }
}