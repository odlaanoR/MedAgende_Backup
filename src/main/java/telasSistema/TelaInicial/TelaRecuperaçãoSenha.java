package telasSistema.TelaInicial;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.event.ActionEvent;

public class TelaRecuperaçãoSenha extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JButton btnEnviar;
    private JButton btnReenviar;
    private JLabel lblNewLabel_2;
    private JButton btnProximo;
    private JButton btnvoltar;
    private JLabel lblStatus;
    
    // CREDENCIAIS FORNECIDAS - CORRIGIDAS
    private static final String EMAIL_SISTEMA = "suporteapp2026@gmail.com";
    private static final String SENHA_SISTEMA = "lbmb fpcn ctmi jhwh"; // Senha do email do sistema
    
    // Credenciais para o email de destino (se necessário para testes)
    private static final String SENHA_DESTINATARIO = "vzyw lush aydc jawv"; // Senha do emailprojetos01@gmail.com
    
    private String tokenGerado = "";
    private String emailDestinatario = "";

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaRecuperaçãoSenha frame = new TelaRecuperaçãoSenha();
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
    public TelaRecuperaçãoSenha() {
        setTitle("Sistema de Recuperação de Senha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 728, 350);
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
        textField.setBounds(45, 115, 300, 25);
        contentPane.add(textField);
        textField.setColumns(10);
        
        // Email do destinatário para testes
        textField.setText("emailprojetos01@gmail.com");
        
        JLabel lblNewLabel_1 = new JLabel("Insira aqui o Email para o qual devemos enviar o Token de Recuperação:");
        lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNewLabel_1.setBounds(35, 89, 434, 14);
        contentPane.add(lblNewLabel_1);
        
        // BOTÃO ENVIAR
        btnEnviar = new JButton("Enviar Token");
        btnEnviar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnEnviar.setBackground(new Color(0, 153, 0));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                enviarTokenEmail();
            }
        });
        btnEnviar.setBounds(45, 150, 120, 30);
        contentPane.add(btnEnviar);
        
        // BOTÃO REENVIAR
        btnReenviar = new JButton("Reenviar Token");
        btnReenviar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnReenviar.setBackground(new Color(51, 102, 255));
        btnReenviar.setForeground(Color.WHITE);
        btnReenviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reenviarTokenEmail();
            }
        });
        btnReenviar.setBounds(175, 150, 130, 30);
        contentPane.add(btnReenviar);
        
        lblNewLabel_2 = new JLabel("Caso ultrapasse os 60s e não tenha recebido o Token, solicite o Reenvio ou verifique se preencheu o campo corretamente.");
        lblNewLabel_2.setBounds(10, 280, 702, 14);
        contentPane.add(lblNewLabel_2);
        
        // BOTÃO PRÓXIMO
        btnProximo = new JButton("Próximo →");
        btnProximo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnProximo.setBackground(new Color(0, 102, 204));
        btnProximo.setForeground(Color.WHITE);
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tokenGerado != null && !tokenGerado.isEmpty() && emailDestinatario != null && !emailDestinatario.isEmpty()) {
                    TelaInserirToken tela = new TelaInserirToken(tokenGerado, emailDestinatario);
                    tela.setLocationRelativeTo(null);
                    tela.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Por favor, envie o token primeiro!", 
                        "Atenção", 
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnProximo.setBounds(501, 240, 156, 35);
        btnProximo.setEnabled(false); // Inicialmente desabilitado
        contentPane.add(btnProximo);
        
        // BOTÃO VOLTAR
        btnvoltar = new JButton("← Voltar");
        btnvoltar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnvoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnvoltar.setBounds(36, 245, 120, 30);
        contentPane.add(btnvoltar);
        
        // Label para status
        lblStatus = new JLabel("Status: Aguardando envio...");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblStatus.setBounds(45, 185, 400, 20);
        contentPane.add(lblStatus);
        
        // Botão de teste rápido
        JButton btnTesteRapido = new JButton("Teste Rápido");
        btnTesteRapido.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        btnTesteRapido.setBackground(new Color(255, 153, 0));
        btnTesteRapido.setForeground(Color.WHITE);
        btnTesteRapido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textField.setText("emailprojetos01@gmail.com");
                enviarTokenEmail();
            }
        });
        btnTesteRapido.setBounds(315, 150, 100, 30);
        contentPane.add(btnTesteRapido);
        
        // Info do sistema
        JLabel lblInfoSistema = new JLabel("<html><b>Sistema:</b> suporteapp2026@gmail.com</html>");
        lblInfoSistema.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblInfoSistema.setBounds(500, 120, 200, 30);
        contentPane.add(lblInfoSistema);
    }
    
    // ========== MÉTODOS DE ENVIO DE EMAIL REAL ==========
    
    /**
     * Método para enviar o token por email
     */
    private void enviarTokenEmail() {
        emailDestinatario = textField.getText().trim();
        
        // Validação básica do email
        if (emailDestinatario.isEmpty() || !emailDestinatario.contains("@")) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira um email válido!", 
                "Email Inválido", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Gerar novo token
        tokenGerado = gerarToken();
        
        // Atualizar status na interface
        lblStatus.setText("Status: Enviando token...");
        lblStatus.setForeground(Color.BLUE);
        
        // Desabilitar botões durante o envio
        btnEnviar.setEnabled(false);
        btnReenviar.setEnabled(false);
        
        // Usar thread separada para não travar a interface
        new Thread(() -> {
            boolean sucesso = false;
            String mensagemErro = "";
            
            try {
                // Tenta enviar email real
                enviarEmailReal(emailDestinatario, tokenGerado);
                sucesso = true;
                
            } catch (AuthenticationFailedException e) {
                mensagemErro = "Falha na autenticação. Verifique as credenciais do email.";
                e.printStackTrace();
            } catch (MessagingException e) {
                mensagemErro = "Erro ao enviar email: " + e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                mensagemErro = "Erro inesperado: " + e.getMessage();
                e.printStackTrace();
            }
            
            final boolean envioSucesso = sucesso;
            final String erroMsg = mensagemErro;
            
            // Atualizar interface na thread principal
            EventQueue.invokeLater(() -> {
                btnEnviar.setEnabled(true);
                btnReenviar.setEnabled(true);
                
                if (envioSucesso) {
                    lblStatus.setText("Status: Token enviado com sucesso!");
                    lblStatus.setForeground(new Color(0, 153, 0));
                    
                    JOptionPane.showMessageDialog(TelaRecuperaçãoSenha.this, 
                        "✅ Token enviado com SUCESSO!\n\n" +
                        "Email destino: " + emailDestinatario + 
                        "\nToken gerado: " + tokenGerado + 
                        "\n\nVálido por 10 minutos." + 
                        "\n\nClique em 'Próximo' para verificar o token.", 
                        "Email Enviado", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Habilitar botão próximo
                    btnProximo.setEnabled(true);
                    
                } else {
                    lblStatus.setText("Status: Falha no envio");
                    lblStatus.setForeground(Color.RED);
                    
                    // Opção de usar modo de teste como fallback
                    int opcao = JOptionPane.showConfirmDialog(TelaRecuperaçãoSenha.this,
                        "❌ Falha no envio do email!\n\n" + erroMsg + 
                        "\n\nDeseja usar o modo de teste (token será exibido)?",
                        "Erro no Envio",
                        JOptionPane.YES_NO_OPTION);
                    
                    if (opcao == JOptionPane.YES_OPTION) {
                        lblStatus.setText("Status: Modo de teste ativo");
                        lblStatus.setForeground(Color.ORANGE);
                        JOptionPane.showMessageDialog(TelaRecuperaçãoSenha.this,
                            "Modo de teste ativado!\n\nToken: " + tokenGerado +
                            "\n\nClique em 'Próximo' para continuar.",
                            "Modo Teste",
                            JOptionPane.WARNING_MESSAGE);
                        btnProximo.setEnabled(true);
                    }
                }
            });
        }).start();
    }
    
    /**
     * Método para reenviar o token
     */
    private void reenviarTokenEmail() {
        if (textField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, insira o email primeiro!", 
                "Email Vazio", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Confirmar reenvio
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja reenviar o token para:\n" + textField.getText().trim() + "?",
            "Confirmar Reenvio",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Gerar novo token e enviar
            tokenGerado = gerarToken();
            enviarTokenEmail();
        }
    }
    
    /**
     * Gera token numérico de 6 dígitos
     */
    private String gerarToken() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
    
    /**
     * Método REAL de envio de email
     */
    private void enviarEmailReal(String emailDestino, String token) throws MessagingException {
        // Configurar propriedades SMTP para Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");
        
        // Debug (opcional)
        props.put("mail.debug", "true");
        
        // Criar sessão com autenticação
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // IMPORTANTE: Remover espaços da senha se houver
                String senhaCorrigida = SENHA_SISTEMA.replace(" ", "");
                return new PasswordAuthentication(EMAIL_SISTEMA, senhaCorrigida);
            }
        });
        
        // Criar mensagem
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_SISTEMA, "Sistema de Recuperação de Senha"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
        message.setSubject("🔐 Token de Recuperação de Senha - Sistema");
        
        // Corpo do email em HTML formatado
        String htmlContent = "<html>"
                + "<body style='font-family: Arial, sans-serif;'>"
                + "<div style='background-color: #f4f4f4; padding: 20px; border-radius: 10px;'>"
                + "<h2 style='color: #333;'>Recuperação de Senha</h2>"
                + "<p>Olá,</p>"
                + "<p>Você solicitou a recuperação de senha. Use o token abaixo para continuar:</p>"
                + "<div style='background-color: #fff; border: 2px dashed #4CAF50; padding: 20px; "
                + "text-align: center; font-size: 24px; font-weight: bold; color: #333; "
                + "margin: 20px 0; border-radius: 5px;'>"
                + token
                + "</div>"
                + "<p><strong>Instruções:</strong></p>"
                + "<ul>"
                + "<li>Insira este token na tela de verificação</li>"
                + "<li>O token é válido por 10 minutos</li>"
                + "<li>Não compartilhe este token com ninguém</li>"
                + "</ul>"
                + "<p>Se você não solicitou esta recuperação, ignore este email.</p>"
                + "<hr style='border: 1px solid #ddd;'>"
                + "<p style='color: #666; font-size: 12px;'>"
                + "Este é um email automático, por favor não responda.<br>"
                + "Sistema de Recuperação de Senha<br>"
                + EMAIL_SISTEMA
                + "</p>"
                + "</div>"
                + "</body>"
                + "</html>";
        
        // Texto alternativo para clientes que não suportam HTML
        String textoSimples = "Token de recuperação: " + token + 
                            "\n\nUse este token na tela de verificação." +
                            "\nVálido por 10 minutos." +
                            "\n\nSe não solicitou, ignore este email.";
        
        // Configurar conteúdo multipart
        MimeMultipart multipart = new MimeMultipart("alternative");
        
        // Parte em texto simples
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(textoSimples, "utf-8");
        
        // Parte em HTML
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
        
        // Adicionar partes ao multipart
        multipart.addBodyPart(textPart);
        multipart.addBodyPart(htmlPart);
        
        // Definir conteúdo da mensagem
        message.setContent(multipart);
        
        // Enviar email
        Transport.send(message);
    }
    
  
    
    private class TelaInserirToken extends JFrame {
        private static final long serialVersionUID = 1L;
        private String tokenValido;
        private String emailUsuario;
        private JTextField txtToken;
        
        public TelaInserirToken(String token, String email) {
            this.tokenValido = token;
            this.emailUsuario = email;
            
            setTitle("Verificação de Token");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setSize(500, 350);
            setLocationRelativeTo(null);
            setResizable(false);
            
            JPanel contentPanel = new JPanel();
            contentPanel.setBackground(new Color(204, 255, 255));
            contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(contentPanel);
            contentPanel.setLayout(null);
            
            JLabel lblTitulo = new JLabel("Verificação de Token");
            lblTitulo.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
            lblTitulo.setBounds(120, 30, 250, 30);
            contentPanel.add(lblTitulo);
            
            JLabel lblInstrucao = new JLabel("Digite o token enviado para:");
            lblInstrucao.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblInstrucao.setBounds(50, 70, 400, 20);
            contentPanel.add(lblInstrucao);
            
            JLabel lblEmail = new JLabel(emailUsuario);
            lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblEmail.setForeground(Color.BLUE);
            lblEmail.setBounds(50, 90, 400, 20);
            contentPanel.add(lblEmail);
            
            txtToken = new JTextField();
            txtToken.setFont(new Font("Arial", Font.BOLD, 18));
            txtToken.setHorizontalAlignment(JTextField.CENTER);
            txtToken.setBounds(150, 130, 200, 40);
            contentPanel.add(txtToken);
            txtToken.setColumns(10);
            
            JLabel lblDica = new JLabel("Dica: O token tem 6 dígitos");
            lblDica.setFont(new Font("Segoe UI", Font.ITALIC, 11));
            lblDica.setForeground(Color.GRAY);
            lblDica.setBounds(150, 175, 200, 20);
            contentPanel.add(lblDica);
            
            JButton btnVerificar = new JButton("Verificar Token");
            btnVerificar.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnVerificar.setBackground(new Color(0, 153, 0));
            btnVerificar.setForeground(Color.WHITE);
            btnVerificar.setBounds(150, 200, 200, 40);
            btnVerificar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    verificarToken();
                }
            });
            contentPanel.add(btnVerificar);
            
            JButton btnVoltar = new JButton("← Voltar");
            btnVoltar.setBounds(50, 260, 100, 30);
            btnVoltar.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    TelaRecuperaçãoSenha tela = new TelaRecuperaçãoSenha();
                    tela.setLocationRelativeTo(null);
                    tela.setVisible(true);
                }
            });
            contentPanel.add(btnVoltar);
            
            JButton btnRedefinir = new JButton("Redefinir Senha →");
            btnRedefinir.setBackground(new Color(51, 102, 255));
            btnRedefinir.setForeground(Color.WHITE);
            btnRedefinir.setBounds(300, 260, 150, 30);
            btnRedefinir.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (txtToken.getText().trim().equals(tokenValido)) {
                        JOptionPane.showMessageDialog(TelaInserirToken.this, 
                            "✅ Token válido!\n\nSua senha será redefinida...", 
                            "Sucesso", 
                            JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(TelaInserirToken.this, 
                            "❌ Token inválido! Verifique e tente novamente.", 
                            "Erro", 
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            contentPanel.add(btnRedefinir);
            
            // Botão para exibir token (apenas para testes)
            JButton btnMostrarToken = new JButton("Mostrar Token");
            btnMostrarToken.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            btnMostrarToken.setBounds(360, 135, 100, 30);
            btnMostrarToken.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    txtToken.setText(tokenValido);
                }
            });
            contentPanel.add(btnMostrarToken);
        }
        
        private void verificarToken() {
            String tokenInserido = txtToken.getText().trim();
            
            if (tokenInserido.isEmpty()) {
                JOptionPane.showMessageDialog(TelaInserirToken.this, 
                    "Por favor, digite o token!", 
                    "Campo Vazio", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (tokenInserido.equals(tokenValido)) {
                JOptionPane.showMessageDialog(TelaInserirToken.this, 
                    "✅ Token válido!\n\nClique em 'Redefinir Senha' para continuar.", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(TelaInserirToken.this, 
                    "❌ Token inválido!\n\nVerifique e tente novamente.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}