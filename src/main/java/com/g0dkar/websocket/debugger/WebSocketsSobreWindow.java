package com.g0dkar.websocket.debugger;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
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
	
	private void initComponents() {
		labelIcon = new JLabel();
		labelTitle = new JLabel();
		labelBy = new JLabel();
		labelLink = new JLabel();
		btnClose = new JButton();
		btnCredits = new JButton();
		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
		
		btnCredits.setText(strings.getString("window.about.btnCredits"));
		
		final GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(labelTitle, GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE).addComponent(labelBy, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(labelIcon, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(labelLink, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(btnCredits).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnClose))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(labelIcon).addGap(18, 18, 18).addComponent(labelTitle).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(labelBy).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(labelLink).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(btnCredits).addComponent(btnClose)).addContainerGap()));
		
		pack();
	}
	
	/**
	 * Ao clicar no Link (tenta abrir o link usando o navegador default)
	 */
	private void labelLink() {
		try {
			Runtime.getRuntime().exec("http://github.com/g0dkar/websocket-debugger");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JButton btnClose;
	private JButton btnCredits;
	private JLabel labelBy;
	private JLabel labelIcon;
	private JLabel labelLink;
	private JLabel labelTitle;
}