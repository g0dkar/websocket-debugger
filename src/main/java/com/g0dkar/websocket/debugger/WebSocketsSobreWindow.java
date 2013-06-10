package com.g0dkar.websocket.debugger;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Janela "Sobre".
 * 
 * @author g0dkar
 * 
 */
public class WebSocketsSobreWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private final ResourceBundle strings;
	
	public WebSocketsSobreWindow() {
		strings = ResourceBundle.getBundle("strings");
		initComponents();
	}
	
	/**
	 * Inicializa TUDO. Chame apenas uma vez.
	 * 
	 * @author NetBeans :P
	 */
	private void initComponents() {
		labelIcon = new JLabel();
		labelTitle = new JLabel();
		labelBy = new JLabel();
		labelLink = new JLabel();
		btnClose = new JButton();
		btnCredits = new JButton();
		
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setTitle(strings.getString("window.about.title"));
		setResizable(false);
		
		try {
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/terminal.png")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		labelIcon.setHorizontalAlignment(SwingConstants.CENTER);
		labelIcon.setIcon(new ImageIcon(getClass().getResource("/terminal.png")));
		
		labelTitle.setFont(labelTitle.getFont().deriveFont(labelTitle.getFont().getStyle() | Font.BOLD, labelTitle.getFont().getSize() + 10));
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setText(strings.getString("window.about.labelTitle"));
		
		labelBy.setHorizontalAlignment(SwingConstants.CENTER);
		labelBy.setText(strings.getString("window.about.by"));
		
		labelLink.setForeground(new Color(51, 153, 255));
		labelLink.setHorizontalAlignment(SwingConstants.CENTER);
		labelLink.setText(strings.getString("window.about.labelLink"));
		labelLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		labelLink.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				labelLink();
			}
		});
		
		btnClose.setText(strings.getString("window.about.btnClose"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose();
			}
		});
		
		// No credits for now! Heh...
		btnCredits.setVisible(false);
		btnCredits.setText(strings.getString("window.about.btnCredits"));
		btnCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCredits();
			}
		});
		
		final GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(labelTitle, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE).addComponent(labelBy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(labelIcon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(labelLink, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(btnCredits).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnClose))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(labelIcon).addGap(18, 18, 18).addComponent(labelTitle).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(labelBy).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(labelLink).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnCredits).addComponent(btnClose)).addContainerGap()));
		
		pack();
	}
	
	/**
	 * Tenta abrir um link no navegador. Exceções podem ocorrer.
	 * 
	 * @param link Link que será aberto
	 * 
	 * @throws Exception Não foi possível abrir o link.
	 */
	private void openLink(String link) throws Exception {
		final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			desktop.browse(new URI("http://github.com/g0dkar/websocket-debugger"));
		}
	}
	
	/**
	 * Tenta abrir um link no navegador "na brutalidade", hehe.
	 * 
	 * @param link Link que será aberto
	 * 
	 * @throws Exception Realmente não foi possível abrir o link...
	 */
	private void openLinkBruteForce(String link) throws Exception {
		try {
			openLink(link);
		} catch (Exception e) {
			final Runtime runtime = Runtime.getRuntime();
			
			// Windows
			try {
				runtime.exec("explorer " + link);
			} catch (Exception e1) {
				// Linux
				try {
					runtime.exec("xdg-open " + link);
				} catch (Exception e2) {
					// OSX
					// Se rolar uma exception aqui, "não deu..."
					runtime.exec("open " + link);
				}
			}
		}
	}
	
	/**
	 * Ao clicar no Link (tenta abrir o link usando o navegador default)
	 */
	private void labelLink() {
		try {
			openLinkBruteForce("http://github.com/g0dkar/websocket-debugger");
		} catch (Exception e) {
			// Sorry but are you running this on a rock or something?
		}
	}
	
	/**
	 * Ao clicar no botão dos Créditos
	 */
	private void btnCredits() {
		setVisible(false);
	}
	
	/**
	 * Ao clicar no botão de fechar
	 */
	private void btnClose() {
		setVisible(false);
	}
	
	private JButton btnClose;
	private JButton btnCredits;
	private JLabel labelBy;
	private JLabel labelIcon;
	private JLabel labelLink;
	private JLabel labelTitle;
}