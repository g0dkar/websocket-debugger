package com.g0dkar.websocket.debugger;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.drafts.Draft_75;
import org.java_websocket.drafts.Draft_76;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Janela principal do cliente WebSockets.
 * 
 * @author g0dkar
 *
 */
public class WebSocketsDebuggerWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	/** {@link ResourceBundle} */
	private final ResourceBundle strings;
	
	/** Janela "Sobre" */
	private final WebSocketsSobreWindow sobre;
	
	/** Instância atual do Cliente */
	private WebSocketClient client;
	
	/** Arquivo onde o Log será salvo */
	private File arquivo;
	
	/** Construtor */
	public WebSocketsDebuggerWindow() {
		strings = ResourceBundle.getBundle("strings");
		initComponents();
		
		sobre = new WebSocketsSobreWindow();
	}
	
	/**
	 * Inicializa TUDO. Chame apenas uma vez.
	 * @author NetBeans :P
	 */
	private void initComponents() {
		splitPane = new JSplitPane();
		panelSuperior = new JPanel();
		editServidor = new JTextField();
		btnConectar = new JButton();
		scrollPane1 = new JScrollPane();
		editMensagem = new JTextArea();
		btnEnviar = new JButton();
		checkEnter = new JCheckBox();
		comboDraft = new JComboBox<String>();
		panelInferior = new JPanel();
		scrollPane2 = new JScrollPane();
		editConsole = new JTextArea();
		menuBar = new JMenuBar();
		menuArquivo = new JMenu();
		menuitemSalvarLog = new JMenuItem();
		menuitemSalvarLogComo = new JMenuItem();
		separator01 = new JPopupMenu.Separator();
		menuitemSair = new JMenuItem();
		menuEditar = new JMenu();
		menuitemLimparLog = new JMenuItem();
		menuitemCopiarLog = new JMenuItem();
		menuitemRecortarLog = new JMenuItem();
		separator02 = new JPopupMenu.Separator();
		menuitemQuebrarLinhas = new JMenuItem();
		menuAjuda = new JMenu();
		menuitemSobre = new JMenuItem();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle(strings.getString("window.main.title"));
		
		try {
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/terminal.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		splitPane.setBorder(null);
		splitPane.setDividerLocation(150);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		editServidor.setText(strings.getString("window.main.server"));
		editServidor.setToolTipText(strings.getString("window.main.server.tooltip"));
		editServidor.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				editServidorKeyPressed(e);
			}
		});
		
		btnConectar.setText(strings.getString("window.main.btnConnect.connect"));
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnConectar();
			}
		});
		
		editMensagem.setColumns(20);
		editMensagem.setRows(5);
		editMensagem.setText(strings.getString("window.main.command"));
		editMensagem.setWrapStyleWord(true);
		editMensagem.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				editMensagemKeyPressed(e);
			}
		});
		scrollPane1.setViewportView(editMensagem);
		
		btnEnviar.setText(strings.getString("window.main.btnSend"));
		btnEnviar.setEnabled(false);
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnEnviar();
			}
		});
		
		checkEnter.setSelected(true);
		checkEnter.setText(strings.getString("window.main.checkEnterSend"));
		checkEnter.setToolTipText(strings.getString("window.main.checkEnterSend.tooltip"));
		
		comboDraft.setModel(new DefaultComboBoxModel<String>(new String[] { "Draft 10", "Draft 17", "Draft 75", "Draft 76" }));
		comboDraft.setSelectedItem("Draft 17");
		comboDraft.setToolTipText(strings.getString("window.main.comboDraft.tooltip"));
		
		GroupLayout panelSuperiorLayout = new GroupLayout(panelSuperior);
		panelSuperior.setLayout(panelSuperiorLayout);
		panelSuperiorLayout.setHorizontalGroup(panelSuperiorLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, panelSuperiorLayout.createSequentialGroup().addContainerGap().addGroup(panelSuperiorLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE).addGroup(panelSuperiorLayout.createSequentialGroup().addComponent(editServidor).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(comboDraft, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(panelSuperiorLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(checkEnter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnEnviar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnConectar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()));
		panelSuperiorLayout.setVerticalGroup(panelSuperiorLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelSuperiorLayout.createSequentialGroup().addContainerGap().addGroup(panelSuperiorLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(editServidor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(btnConectar).addComponent(comboDraft, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(panelSuperiorLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE).addGroup(panelSuperiorLayout.createSequentialGroup().addComponent(btnEnviar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(checkEnter))).addContainerGap()));
		
		splitPane.setTopComponent(panelSuperior);
		
		editConsole.setEditable(false);
		editConsole.setColumns(20);
		editConsole.setRows(5);
		scrollPane2.setViewportView(editConsole);
		
		GroupLayout panelInferiorLayout = new GroupLayout(panelInferior);
		panelInferior.setLayout(panelInferiorLayout);
		panelInferiorLayout.setHorizontalGroup(panelInferiorLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInferiorLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE).addContainerGap()));
		panelInferiorLayout.setVerticalGroup(panelInferiorLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(panelInferiorLayout.createSequentialGroup().addContainerGap().addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE).addContainerGap()));
		
		splitPane.setRightComponent(panelInferior);
		
		menuArquivo.setText(strings.getString("window.main.menu.file"));
		
		menuitemSalvarLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		menuitemSalvarLog.setText(strings.getString("window.main.menu.file.save"));
		menuitemSalvarLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuitemSalvarLog();
			}
		});
		menuArquivo.add(menuitemSalvarLog);
		
		menuitemSalvarLogComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
		menuitemSalvarLogComo.setText(strings.getString("window.main.menu.file.saveAs"));
		menuitemSalvarLogComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuitemSalvarLogComo();
			}
		});
		menuArquivo.add(menuitemSalvarLogComo);
		menuArquivo.add(separator01);
		
		menuitemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		menuitemSair.setText(strings.getString("window.main.menu.file.quit"));
		menuitemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuitemSair();
			}
		});
		menuArquivo.add(menuitemSair);
		
		menuBar.add(menuArquivo);
		
		menuEditar.setText(strings.getString("window.main.menu.edit"));
		
		menuitemLimparLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		menuitemLimparLog.setText(strings.getString("window.main.menu.edit.clear"));
		menuitemLimparLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuitemLimparLog();
			}
		});
		menuEditar.add(menuitemLimparLog);
		
		menuitemCopiarLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
		menuitemCopiarLog.setText(strings.getString("window.main.menu.edit.copy"));
		menuitemCopiarLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuitemCopiarLog();
			}
		});
		menuEditar.add(menuitemCopiarLog);
		
		menuitemRecortarLog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK));
		menuitemRecortarLog.setText(strings.getString("window.main.menu.edit.cut"));
		menuitemRecortarLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuitemRecortarLog();
			}
		});
		menuEditar.add(menuitemRecortarLog);
		menuEditar.add(separator02);
		
		menuitemQuebrarLinhas.setText(strings.getString("window.main.menu.edit.wordwrap"));
		menuitemQuebrarLinhas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuitemQuebrarLinhas();
			}
		});
		menuEditar.add(menuitemQuebrarLinhas);
		
		menuBar.add(menuEditar);
		
		menuAjuda.setText(strings.getString("window.main.menu.help"));
		
		menuitemSobre.setText(strings.getString("window.main.menu.help.about"));
		menuitemSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuitemSobre();
			}
		});
		menuAjuda.add(menuitemSobre);
		
		menuBar.add(menuAjuda);
		
		setJMenuBar(menuBar);
		
		final GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(splitPane));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(splitPane));
		
		pack();
	}
	
	public void console(String texto) {
		editConsole.append(texto);
		editConsole.append("\n");
		editConsole.setCaretPosition(editConsole.getDocument().getLength());
	}
	
	/**
	 * Chamado pelo {@link WebSocketClient cliente} quando uma conexão é aberta.
	 */
	public void onOpen(ServerHandshake handshakedata) {
		editServidor.setEnabled(false);
		comboDraft.setEnabled(false);
		btnConectar.setEnabled(true);
		btnConectar.setText(strings.getString("window.main.btnConnect.disconnect"));
		
		editMensagem.setEditable(true);
		btnEnviar.setEnabled(true);
	}
	
	/**
	 * Chamado quando chegam dados do servidor.
	 * @param message
	 */
	public void onMessage(String message) {
	}
	
	/**
	 * Chamado pelo {@link WebSocketClient cliente} quando uma conexão é finalizada.
	 * 
	 * @param code Código de fechamento
	 * @param reason Motivo do fechamento
	 * @param remote A conexão foi fechada pelo cliente ou pelo servidor?
	 */
	public void onClose(int code, String reason, boolean remote) {
		editServidor.setEnabled(true);
		comboDraft.setEnabled(true);
		btnConectar.setEnabled(true);
		btnConectar.setText(strings.getString("window.main.btnConnect.connect"));
		
		btnEnviar.setEnabled(false);
		
		client = null;
	}
	
	/**
	 * Chamado quando ocorre uma {@link Exception}.
	 * @param ex {@link Exception} que aconteceu.
	 */
	public void onError(Exception ex) {
		console(strings.getString("log.exception"));
		
		final ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ex.printStackTrace(new PrintStream(byteArray));
		console(byteArray.toString());
	}
	
	private void btnConectar() {
		btnConectar.setEnabled(false);
		
		if (client == null) {
			try {
				editServidor.setEnabled(false);
				comboDraft.setEnabled(false);
				
				String selectedDraft = (String) comboDraft.getSelectedItem();
				Draft draft = null;
				
				if ("Draft 10".equals(selectedDraft)) {
					draft = new Draft_10();
				}
				
				if ("Draft 17".equals(selectedDraft)) {
					draft = new Draft_17();
				}
				
				if ("Draft 75".equals(selectedDraft)) {
					draft = new Draft_75();
				}
				
				if ("Draft 76".equals(selectedDraft)) {
					draft = new Draft_76();
				}
				
				client = new WebSocketClient(this, new URI(editServidor.getText()), draft);
				client.connect();
			} catch (Exception e) {
				onError(e);
				client = null;
				editServidor.setEnabled(true);
				comboDraft.setEnabled(true);
				btnConectar.setEnabled(true);
				btnConectar.setText(strings.getString("window.main.btnConnect.connect"));
			}
		}
		else {
			// Desconecta e descarta este WebSocket client.
			try {
				client.closeBlocking();
				client = null;
			} catch (InterruptedException e) {
				onError(e);
			}
		}
	}
	
	private void btnEnviar() {
		if (btnEnviar.isEnabled() && client != null) {
			client.send(editMensagem.getText());
		}
	}
	
	/**
	 * Quando uma tecla é pressionada no edit do servidor. Enter = Conectar.
	 * @param evt {@link KeyEvent} correspondente.
	 */
	private void editServidorKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			btnConectar();
			evt.consume();
		}
	}
	
	/**
	 * Quando uma tecla é pressionada no edit do comando. Enter = Enviar se o
	 * {@link #checkEnter checkbox "Enter = Enviar"} estiver selecionado.
	 * 
	 * @param evt {@link KeyEvent} correspondente.
	 */
	private void editMensagemKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER && checkEnter.isSelected()) {
			btnEnviar();
			evt.consume();
		}
	}
	
	/**
	 * Ação do item de menu "Salvar Log". Chama {@link #menuitemSalvarLogComo()} caso um arquivo
	 * ainda não tenha sido selecionado.
	 */
	private void menuitemSalvarLog() {
		if (arquivo == null) {
			menuitemSalvarLogComo();
		}
		else {
			try {
				final FileWriter fileWriter = new FileWriter(arquivo);
				fileWriter.write(editConsole.getText());
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Abre uma janela para {@link JFileChooser#showSaveDialog(java.awt.Component) salvar arquivo}
	 * e {@link #menuitemSalvarLog() salva o log no arquivo} logo em seguida.
	 */
	private void menuitemSalvarLogComo() {
		final JFileChooser fileChooser = new JFileChooser();
		if (arquivo != null) {
			fileChooser.setSelectedFile(arquivo);
		}
		fileChooser.setMultiSelectionEnabled(false);
		
		final int result = fileChooser.showSaveDialog(this);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			arquivo = fileChooser.getSelectedFile();
			
			if (arquivo != null) {
				menuitemSalvarLog();
			}
		}
	}
	
	/**
	 * Oculta esta janela e a destrói, finalizando o programa.
	 * (devido ao {@link JFrame#EXIT_ON_CLOSE})
	 */
	private void menuitemSair() {
		try {
			if (client != null) {
				btnConectar();
			}
			
			sobre.setVisible(false);
			sobre.dispose();
			setVisible(false);
			dispose();
		} catch (Exception e) {
			System.exit(0);
		}
	}
	
	/**
	 * Limpa o conteúdo do Log.
	 */
	private void menuitemLimparLog() {
		editConsole.setText("");
		editConsole.setCaretPosition(editConsole.getDocument().getLength());
	}
	
	/**
	 * Coloca no {@link Clipboard} o conteúdo do log.
	 */
	private void menuitemCopiarLog() {
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(new StringSelection(editConsole.getText()), null);
	}
	
	/**
	 * {@link #menuitemCopiarLog() Copia} e {@link #menuitemLimparLog() apaga} o log, efetivamente
	 * "Recortando-o".
	 */
	private void menuitemRecortarLog() {
		menuitemCopiarLog();
		menuitemLimparLog();
	}
	
	/**
	 * Liga/Desliga as quebras de linha no Console.
	 */
	private void menuitemQuebrarLinhas() {
		menuitemQuebrarLinhas.setSelected(!menuitemQuebrarLinhas.isSelected());
		editConsole.setWrapStyleWord(menuitemQuebrarLinhas.isSelected());
	}
	
	/**
	 * Mostra informações sobre o programa.
	 */
	private void menuitemSobre() {
		// Abre a janela no próximo ciclo de fuleragem do Swing.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				sobre.setVisible(true);
			}
		});
	}
	
	/**
	 * Inicia a execução do programa.
	 */
	public static void main(String args[]) {
		try {
			// Tenta usar o Look and Feel do S.O. atual
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			// No fucks given...
		}
		
		// Abre a janela no próximo ciclo de fuleragem do Swing.
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new WebSocketsDebuggerWindow().setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Erro ao iniciar programa. Finalizando.\n\nErro: " + e.getClass().getCanonicalName() + " (mensagem: " + e.getLocalizedMessage() + ")", "Erro na Inicialização", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		});
	}
	
	// Variables declaration - do not modify
	private JButton btnConectar;
	private JButton btnEnviar;
	private JCheckBox checkEnter;
	private JComboBox<String> comboDraft;
	private JTextArea editConsole;
	private JTextArea editMensagem;
	private JTextField editServidor;
	private JMenu menuAjuda;
	private JMenu menuArquivo;
	private JMenuBar menuBar;
	private JMenu menuEditar;
	private JMenuItem menuitemCopiarLog;
	private JMenuItem menuitemLimparLog;
	private JMenuItem menuitemQuebrarLinhas;
	private JMenuItem menuitemRecortarLog;
	private JMenuItem menuitemSair;
	private JMenuItem menuitemSalvarLog;
	private JMenuItem menuitemSalvarLogComo;
	private JMenuItem menuitemSobre;
	private JPanel panelInferior;
	private JPanel panelSuperior;
	private JScrollPane scrollPane1;
	private JScrollPane scrollPane2;
	private JPopupMenu.Separator separator01;
	private JPopupMenu.Separator separator02;
	private JSplitPane splitPane;
}