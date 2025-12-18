package telasSistema.Secretaria;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import conexao.ConnectionFactory;

public class TelaHistoricoAgendamentos extends JFrame {

    private static final long serialVersionUID = 1L;

    // Labels preenchidos dinamicamente
    private JLabel lblConsulta1;
    private JLabel lblConsulta2;
    private JLabel lblConsulta3;
    private JLabel lblStatusBusca;
    private JTextField txtCPFBusca;
    private JButton btnBuscarCPF;
    private JButton btnLimparBusca;

    public TelaHistoricoAgendamentos() {
        setTitle("Histórico de Consultas");
        setBounds(100, 100, 800, 550);
        getContentPane().setBackground(new Color(170, 255, 255));
        getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(204, 253, 255));
        panel.setBounds(0, 1, 786, 513);
        getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblTitulo = new JLabel("Histórico de Consultas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setBounds(250, 20, 350, 54);
        panel.add(lblTitulo);

        // Adicionando campo de busca por CPF
        JLabel lblBuscaCPF = new JLabel("Buscar por CPF:");
        lblBuscaCPF.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblBuscaCPF.setBounds(100, 85, 100, 20);
        panel.add(lblBuscaCPF);

        txtCPFBusca = new JTextField();
        txtCPFBusca.setBounds(180, 85, 200, 25);
        txtCPFBusca.setColumns(10);
        panel.add(txtCPFBusca);

        btnBuscarCPF = new JButton("Buscar");
        btnBuscarCPF.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnBuscarCPF.setBounds(390, 85, 80, 25);
        btnBuscarCPF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarConsultasPorCPF();
            }
        });
        panel.add(btnBuscarCPF);

        btnLimparBusca = new JButton("Limpar");
        btnLimparBusca.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnLimparBusca.setBounds(480, 85, 80, 25);
        btnLimparBusca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparBusca();
            }
        });
        panel.add(btnLimparBusca);

        lblStatusBusca = new JLabel("");
        lblStatusBusca.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblStatusBusca.setForeground(Color.BLUE);
        lblStatusBusca.setBounds(180, 110, 400, 20);
        panel.add(lblStatusBusca);

        JButton btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(e -> {
            TelaPrincipalSecretaria tela = new TelaPrincipalSecretaria();
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);
            dispose();
        });
        btnConfirmar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnConfirmar.setBounds(600, 450, 95, 25);
        panel.add(btnConfirmar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            TelaPrincipalSecretaria tela = new TelaPrincipalSecretaria();
            tela.setLocationRelativeTo(null);
            tela.setVisible(true);
            dispose();
        });
        btnVoltar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVoltar.setBounds(100, 450, 84, 25);
        panel.add(btnVoltar);

        // Painel 1
        JPanel panel1 = new JPanel();
        panel1.setBounds(100, 140, 600, 60);
        panel.add(panel1);
        panel1.setLayout(null);

        lblConsulta1 = new JLabel();
        lblConsulta1.setBounds(10, 10, 580, 40);
        lblConsulta1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel1.add(lblConsulta1);

        // Painel 2
        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(100, 210, 600, 60);
        panel.add(panel2);

        lblConsulta2 = new JLabel();
        lblConsulta2.setBounds(10, 10, 580, 40);
        lblConsulta2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel2.add(lblConsulta2);

        // Painel 3
        JPanel panel3 = new JPanel();
        panel3.setLayout(null);
        panel3.setBounds(100, 280, 600, 60);
        panel.add(panel3);

        lblConsulta3 = new JLabel();
        lblConsulta3.setBounds(10, 10, 580, 40);
        lblConsulta3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel3.add(lblConsulta3);

        // Painel 4 (para mais consultas)
        JPanel panel4 = new JPanel();
        panel4.setLayout(null);
        panel4.setBounds(100, 350, 600, 60);
        panel.add(panel4);

        JLabel lblMaisConsultas = new JLabel("<html><i>Para ver todas as consultas, busque por um CPF específico</i></html>");
        lblMaisConsultas.setBounds(10, 10, 580, 40);
        lblMaisConsultas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMaisConsultas.setForeground(Color.GRAY);
        panel4.add(lblMaisConsultas);

        // Carregar histórico geral inicial
        carregarHistorico();
    }

    private void carregarHistorico() {
        carregarHistorico(null);
    }

    private void carregarHistorico(String cpfFiltro) {
        // Limpar labels
        lblConsulta1.setText("");
        lblConsulta2.setText("");
        lblConsulta3.setText("");

        // Construir SQL com ou sem filtro de CPF
        String sql;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = ConnectionFactory.getConnection();
            
            if (cpfFiltro != null && !cpfFiltro.trim().isEmpty()) {
                // Buscar paciente pelo CPF primeiro para obter o ID
                String cpfFormatado = formatarCPFNumeros(cpfFiltro);
                
                // Verificar se o paciente existe
                String sqlPaciente = "SELECT Id_Paciente, Nome FROM paciente WHERE CPF = ?";
                ps = con.prepareStatement(sqlPaciente);
                ps.setString(1, cpfFormatado);
                rs = ps.executeQuery();
                
                if (rs.next()) {
                    int idPaciente = rs.getInt("Id_Paciente");
                    String nomePaciente = rs.getString("Nome");
                    
                    // Fechar ResultSet atual
                    rs.close();
                    ps.close();
                    
                    // Agora buscar as consultas deste paciente (todas, não apenas 3)
                    sql = "SELECT " +
                          "p.Nome AS paciente_nome, " +
                          "u.Nome AS medico_nome, " +
                          "e.Nome_Especialidade AS especialidade_nome, " +
                          "c.Data_Consulta AS data_consulta, " +
                          "c.Horario_Consulta AS horario_consulta, " +
                          "c.Situacao AS situacao_consulta, " +
                          "c.Cidade_Consulta AS cidade_consulta " +
                          "FROM consultas c " +
                          "INNER JOIN paciente p ON c.Fk_Id_Paciente = p.Id_Paciente " +
                          "INNER JOIN medico m ON c.Fk_Matricula_Medico = m.Matricula " +
                          "INNER JOIN usuarios u ON m.Id_Usuario = u.Id_Usuario " +
                          "INNER JOIN especialidades e ON m.Especialidade = e.Id_Especialidade " +
                          "WHERE p.Id_Paciente = ? " +
                          "ORDER BY c.Data_Consulta DESC, c.Horario_Consulta DESC " +
                          "LIMIT 10";
                    
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, idPaciente);
                    rs = ps.executeQuery();
                    
                    lblStatusBusca.setText("Mostrando consultas para: " + nomePaciente + " (CPF: " + formatarCPF(cpfFormatado) + ")");
                    lblStatusBusca.setForeground(new Color(0, 100, 0));
                    
                } else {
                    // Paciente não encontrado
                    lblConsulta1.setText("Paciente com CPF " + formatarCPF(cpfFormatado) + " não encontrado.");
                    lblConsulta1.setForeground(Color.RED);
                    lblStatusBusca.setText("Paciente não encontrado");
                    lblStatusBusca.setForeground(Color.RED);
                    return;
                }
            } else {
                // Histórico geral (sem filtro) - mostrar apenas últimas consultas
                sql = "SELECT " +
                      "p.Nome AS paciente_nome, " +
                      "u.Nome AS medico_nome, " +
                      "e.Nome_Especialidade AS especialidade_nome, " +
                      "c.Data_Consulta AS data_consulta, " +
                      "c.Horario_Consulta AS horario_consulta, " +
                      "c.Situacao AS situacao_consulta, " +
                      "c.Cidade_Consulta AS cidade_consulta, " +
                      "p.CPF AS paciente_cpf " +
                      "FROM consultas c " +
                      "INNER JOIN paciente p ON c.Fk_Id_Paciente = p.Id_Paciente " +
                      "INNER JOIN medico m ON c.Fk_Matricula_Medico = m.Matricula " +
                      "INNER JOIN usuarios u ON m.Id_Usuario = u.Id_Usuario " +
                      "INNER JOIN especialidades e ON m.Especialidade = e.Id_Especialidade " +
                      "ORDER BY c.Data_Consulta DESC, c.Horario_Consulta DESC " +
                      "LIMIT 3";
                
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                
                lblStatusBusca.setText("Mostrando últimas 3 consultas gerais");
                lblStatusBusca.setForeground(Color.BLUE);
            }

            JLabel[] labels = { lblConsulta1, lblConsulta2, lblConsulta3 };
            int i = 0;
            
            while (rs.next() && i < labels.length) {
                String situacao = rs.getString("situacao_consulta");
                Color corSituacao = Color.BLACK;
                
                // Definir cor baseada na situação
                if (situacao != null) {
                    String situacaoLower = situacao.toLowerCase();
                    if (situacaoLower.contains("agenda") || situacaoLower.contains("marcada")) {
                        corSituacao = new Color(0, 100, 0); // Verde escuro
                    } else if (situacaoLower.contains("cancela") || situacaoLower.contains("cancelada")) {
                        corSituacao = Color.RED;
                    } else if (situacaoLower.contains("realiza") || situacaoLower.contains("concluída") || 
                               situacaoLower.contains("finalizada")) {
                        corSituacao = Color.BLUE;
                    } else if (situacaoLower.contains("pendente") || situacaoLower.contains("aguardando")) {
                        corSituacao = Color.ORANGE;
                    }
                }
                
                String pacienteNome = rs.getString("paciente_nome");
                String medicoNome = rs.getString("medico_nome");
                String especialidade = rs.getString("especialidade_nome");
                String dataConsulta = rs.getDate("data_consulta") != null ? 
                    rs.getDate("data_consulta").toString() : "N/A";
                
                String horario = "N/A";
                if (rs.getTime("horario_consulta") != null) {
                    String horaCompleta = rs.getTime("horario_consulta").toString();
                    horario = horaCompleta.length() >= 5 ? horaCompleta.substring(0, 5) : horaCompleta;
                }
                
                String cidade = rs.getString("cidade_consulta");
                if (cidade == null || cidade.trim().isEmpty()) {
                    cidade = "Não informada";
                }
                
                String textoConsulta = String.format(
                    "<html>" +
                    "<b>Paciente:</b> %s" +
                    " | <b>Especialidade:</b> %s" +
                    " | <b>Médico:</b> %s" +
                    "<br/><b>Data:</b> %s" +
                    " | <b>Hora:</b> %s" +
                    " | <b>Cidade:</b> %s" +
                    " | <b>Situação:</b> <font color='%s'>%s</font>" +
                    "</html>",
                    pacienteNome,
                    especialidade,
                    medicoNome,
                    dataConsulta,
                    horario,
                    cidade,
                    getColorHex(corSituacao),
                    (situacao != null ? situacao : "Não informada")
                );
                
                labels[i].setText(textoConsulta);
                labels[i].setForeground(Color.BLACK);
                i++;
            }

            if (i == 0 && cpfFiltro != null) {
                lblConsulta1.setText("Nenhuma consulta encontrada para este CPF.");
                lblConsulta1.setForeground(Color.GRAY);
            } else if (i == 0) {
                lblConsulta1.setText("Nenhuma consulta encontrada no sistema.");
                lblConsulta1.setForeground(Color.GRAY);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar histórico de consultas: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            lblStatusBusca.setText("Erro ao buscar consultas");
            lblStatusBusca.setForeground(Color.RED);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buscarConsultasPorCPF() {
        String cpf = txtCPFBusca.getText().trim();
        
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, digite um CPF para buscar.",
                "CPF Vazio",
                JOptionPane.WARNING_MESSAGE);
            txtCPFBusca.requestFocus();
            return;
        }
        
        // Formatar CPF (remover caracteres não numéricos)
        String cpfFormatado = formatarCPFNumeros(cpf);
        
        // Validar formato do CPF
        if (cpfFormatado.length() != 11) {
            JOptionPane.showMessageDialog(this,
                "CPF inválido! Deve conter 11 dígitos.\n" +
                "Exemplo: 123.456.789-01",
                "CPF Inválido",
                JOptionPane.WARNING_MESSAGE);
            txtCPFBusca.requestFocus();
            return;
        }
        
        // Validar se todos os dígitos são iguais (CPF inválido)
        if (cpfFormatado.matches("(\\d)\\1{10}")) {
            JOptionPane.showMessageDialog(this,
                "CPF inválido!",
                "CPF Inválido",
                JOptionPane.WARNING_MESSAGE);
            txtCPFBusca.requestFocus();
            return;
        }
        
        // Desabilitar botão durante a busca
        btnBuscarCPF.setEnabled(false);
        btnBuscarCPF.setText("Buscando...");
        
        // Carregar histórico filtrado por CPF
        carregarHistorico(cpfFormatado);
        
        // Reabilitar botão
        btnBuscarCPF.setEnabled(true);
        btnBuscarCPF.setText("Buscar");
        
        // Formatar visualmente o CPF no campo
        txtCPFBusca.setText(formatarCPF(cpfFormatado));
    }

    private void limparBusca() {
        txtCPFBusca.setText("");
        lblStatusBusca.setText("");
        carregarHistorico(); // Carregar histórico geral
        txtCPFBusca.requestFocus();
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
    
    private String formatarCPFNumeros(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

    private String getColorHex(Color color) {
        return String.format("#%02x%02x%02x", 
            color.getRed(), 
            color.getGreen(), 
            color.getBlue());
    }

    public static void main(String[] args) {
        TelaHistoricoAgendamentos tela = new TelaHistoricoAgendamentos();
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLocationRelativeTo(null);
        tela.setVisible(true);
    }
}