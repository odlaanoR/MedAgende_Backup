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

public class TelaSecretariaCancelar extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField FieldCpf;
    private JTextField FieldNomePaciente;
    private JLabel lblNomeEncontrado;
    private JButton btnBuscar; // Adicionado para controle

    public TelaSecretariaCancelar() {
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 742, 454);
        getContentPane().setBackground(new Color(170, 255, 255));
        getContentPane().setLayout(null);
        setTitle("Cancelamento de Consulta - Sistema Clínica");
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(204, 253, 255));
        panel.setBounds(0, 1, 729, 417);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        setupTitle(panel);
        setupFormFields(panel);
        setupButtons(panel);
    }
    
    private void setupTitle(JPanel panel) {
        JLabel lblCancelarConsulta = new JLabel("Cancelar Consulta");
        lblCancelarConsulta.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblCancelarConsulta.setBounds(250, 30, 250, 50);
        lblCancelarConsulta.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblCancelarConsulta);
        
        JLabel lblPreenchaDados = new JLabel("Preencha os dados do paciente");
        lblPreenchaDados.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblPreenchaDados.setBounds(260, 80, 230, 20);
        lblPreenchaDados.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblPreenchaDados);
    }
    
    private void setupFormFields(JPanel panel) {
        // CPF Label and Field
        JLabel lblCpfPaciente = new JLabel("CPF do paciente:");
        lblCpfPaciente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCpfPaciente.setBounds(108, 140, 120, 20);
        panel.add(lblCpfPaciente);
        
        FieldCpf = new JTextField();
        FieldCpf.setBounds(238, 140, 280, 25);
        FieldCpf.setColumns(10);
        FieldCpf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!FieldCpf.getText().trim().isEmpty()) {
                    buscarPacientePorCPF();
                }
            }
        });
        panel.add(FieldCpf);
        
        // Nome Label (static)
        JLabel lblNomePaciente = new JLabel("Nome do paciente:");
        lblNomePaciente.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNomePaciente.setBounds(108, 180, 120, 20);
        panel.add(lblNomePaciente);
        
        // Campo de texto para nome (apenas leitura)
        FieldNomePaciente = new JTextField();
        FieldNomePaciente.setEditable(false);
        FieldNomePaciente.setBounds(238, 180, 280, 25);
        FieldNomePaciente.setColumns(10);
        FieldNomePaciente.setBackground(new Color(240, 240, 240));
        panel.add(FieldNomePaciente);
        
        // Label para mostrar resultado da busca
        lblNomeEncontrado = new JLabel("");
        lblNomeEncontrado.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblNomeEncontrado.setForeground(Color.BLUE);
        lblNomeEncontrado.setBounds(238, 210, 280, 20);
        panel.add(lblNomeEncontrado);
        
        // Botão para buscar manualmente - MODIFICADO
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnBuscar.setBounds(530, 140, 80, 25);
        btnBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPacientePorCPF();
            }
        });
        panel.add(btnBuscar);
    }
    
    private void setupButtons(JPanel panel) {
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVoltar.setBounds(100, 300, 100, 30);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                voltarParaTelaPrincipal();
            }
        });
        panel.add(btnVoltar);
        
        JButton btnProximo = new JButton("Próximo");
        btnProximo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnProximo.setBounds(530, 300, 100, 30);
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validarEProximo();
            }
        });
        panel.add(btnProximo);
        
        // Botão de limpar
        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnLimpar.setBounds(530, 180, 80, 25);
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        panel.add(btnLimpar);
    }
    
    private void buscarPacientePorCPF() {
        String cpf = FieldCpf.getText().trim();
        
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, digite um CPF para buscar.",
                "CPF Vazio",
                JOptionPane.WARNING_MESSAGE);
            FieldCpf.requestFocus();
            return;
        }
        
        // Formatar CPF (remover caracteres não numéricos)
        cpf = cpf.replaceAll("[^0-9]", "");
        
        // Validar formato do CPF
        if (cpf.length() != 11) {
            FieldNomePaciente.setText("");
            lblNomeEncontrado.setText("CPF inválido (deve ter 11 dígitos)");
            lblNomeEncontrado.setForeground(Color.RED);
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
            
            // Buscar paciente pelo CPF - AJUSTADO para tabela PACIENTE
            String sql = "SELECT Nome, Id_Paciente FROM paciente WHERE CPF = ?";
            ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                String nomePaciente = rs.getString("Nome");
                int idPaciente = rs.getInt("Id_Paciente");
                FieldNomePaciente.setText(nomePaciente);
                lblNomeEncontrado.setText("Paciente encontrado - ID: " + idPaciente);
                lblNomeEncontrado.setForeground(new Color(0, 150, 0)); // Verde escuro
                
                // Verificar se existem consultas agendadas
                verificarConsultasAgendadas(idPaciente);
                
            } else {
                FieldNomePaciente.setText("");
                lblNomeEncontrado.setText("Paciente não encontrado");
                lblNomeEncontrado.setForeground(Color.RED);
                
                // Perguntar se deseja cadastrar
                int opcao = JOptionPane.showConfirmDialog(this,
                    "CPF não encontrado no sistema!\n\n" +
                    "Deseja cadastrar este paciente?",
                    "Paciente Não Encontrado",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
                
                if (opcao == JOptionPane.YES_OPTION) {
                    // Abrir tela de cadastro de paciente
                    abrirTelaCadastroPaciente(cpf);
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
    
    private void abrirTelaCadastroPaciente(String cpf) {
        try {
            // Fechar a tela atual
            dispose();
            
          
           
            TelaSecretariaCadastrarPaciente telaSecretariaCadastrar = new TelaSecretariaCadastrarPaciente();
			telaSecretariaCadastrar.setLocationRelativeTo(null);
			telaSecretariaCadastrar.setVisible(true);
			 dispose();
            
            JOptionPane.showMessageDialog(this,
                "Funcionalidade de cadastro será implementada aqui.\n" +
                "CPF para cadastro: " + formatarCPF(cpf),
                "Cadastro de Paciente",
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao abrir tela de cadastro: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String formatarCPF(String cpf) {
        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + 
                   cpf.substring(3, 6) + "." + 
                   cpf.substring(6, 9) + "-" + 
                   cpf.substring(9, 11);
        }
        return cpf;
    }
    
    private void verificarConsultasAgendadas(int idPaciente) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            
            
            String sql = "SELECT COUNT(*) as total_consultas " +
                        "FROM agenda " +
                        "WHERE Id_Paciente = ? AND Data_Consulta >= CURDATE() " +
                        "AND Status = 'agendada'";
            
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, idPaciente);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int totalConsultas = rs.getInt("total_consultas");
                if (totalConsultas > 0) {
                    lblNomeEncontrado.setText("Paciente encontrado - " + totalConsultas + 
                                             " consulta(s) agendada(s)");
                } else {
                    lblNomeEncontrado.setText("Paciente encontrado - Nenhuma consulta agendada");
                    lblNomeEncontrado.setForeground(Color.ORANGE);
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
    }
    
    private void limparCampos() {
        FieldCpf.setText("");
        FieldNomePaciente.setText("");
        lblNomeEncontrado.setText("");
        FieldCpf.requestFocus();
    }
    
    private void validarEProximo() {
      
        StringBuilder erros = new StringBuilder();
        
        String cpf = FieldCpf.getText().trim();
        if (cpf.isEmpty()) {
            erros.append("• CPF do paciente é obrigatório\n");
        } else {
            // Validar formato do CPF
            cpf = cpf.replaceAll("[^0-9]", "");
            if (cpf.length() != 11) {
                erros.append("• CPF deve conter 11 dígitos\n");
            }
        }
        
        String nomePaciente = FieldNomePaciente.getText().trim();
        if (nomePaciente.isEmpty()) {
            erros.append("• Paciente não encontrado. Verifique o CPF\n");
        }
        
       
        if (erros.length() > 0) {
            JOptionPane.showMessageDialog(this,
                "Por favor, corrija os seguintes erros:\n\n" + erros.toString(),
                "Validação",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar se o paciente tem consultas para cancelar
        String cpfFormatado = FieldCpf.getText().replaceAll("[^0-9]", "");
        if (!verificarSeTemConsultas(cpfFormatado)) {
            int resposta = JOptionPane.showConfirmDialog(this,
                "Este paciente não possui consultas agendadas.\n" +
                "Deseja prosseguir mesmo assim?",
                "Sem Consultas Agendadas",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
            
            if (resposta != JOptionPane.YES_OPTION) {
                return;
            }
        }
        
        
        passarParaHistorico(cpfFormatado);
    }
    
    private boolean verificarSeTemConsultas(String cpf) {
        Connection conexao = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conexao = ConnectionFactory.getConnection();
            
            
            String sqlBuscarId = "SELECT Id_Paciente FROM paciente WHERE CPF = ?";
            ps = conexao.prepareStatement(sqlBuscarId);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int idPaciente = rs.getInt("Id_Paciente");
                
               
                rs.close();
                ps.close();
                
                
                String sqlConsultas = "SELECT COUNT(*) as total " +
                                    "FROM agenda " +
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
    
    private void passarParaHistorico(String cpf) {
        try {
            
        	TelaHistoricoAgendamentos telaHistoricoAgendamentos = new TelaHistoricoAgendamentos();
			telaHistoricoAgendamentos.setLocationRelativeTo(this);
			telaHistoricoAgendamentos.setVisible(true);
           
			JOptionPane.showMessageDialog(this,
                "Funcionalidade 'Próximo' - Abriria a tela de consultas agendadas\n" +
                "para o CPF: " + formatarCPF(cpf),
                "Próximo Passo",
                JOptionPane.INFORMATION_MESSAGE);
            
          
            dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao abrir histórico de agendamentos: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void voltarParaTelaPrincipal() {
        try {
            TelaPrincipalSecretaria tela = new TelaPrincipalSecretaria();
            tela.setLocationRelativeTo(this);
            tela.setVisible(true);
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Erro ao voltar para tela principal: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    // Método main para teste
    public static void main(String[] args) {
        TelaSecretariaCancelar tela = new TelaSecretariaCancelar();
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }
}