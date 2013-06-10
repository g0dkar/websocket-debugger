package com.g0dkar.websocket.debugger;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Implementação de um cliente WebSocket que simplesmente exibe na {@link WebSocketsDebuggerWindow janela} o
 * que está acontecendo.
 * 
 * @author g0dkar
 *
 */
public class WebSocketClient extends org.java_websocket.client.WebSocketClient {
	private final ResourceBundle strings;
	
	/** {@link WebSocketsDebuggerWindow Janela} onde serão exibidas as coisas */
	private final WebSocketsDebuggerWindow window;
	
	public WebSocketClient(final WebSocketsDebuggerWindow window, final URI serverUri, final Draft draft) {
		super(serverUri, draft);
		this.window = window;
		strings = ResourceBundle.getBundle("strings");
	}
	
	/**
	 * Adiciona uma linha de texto ao Console.
	 * 
	 * @param texto Texto a ser incluso no console.
	 */
	private void console(String texto) {
		window.console(texto);
	}
	
	/**
	 * Chamado após uma conexão ser feita com sucesso junto ao Servidor.
	 */
	public void onOpen(ServerHandshake handshakedata) {
		window.onOpen(handshakedata);
		
		console(strings.getString("log.connectionStarted") + " " + handshakedata.getHttpStatus() + " " + handshakedata.getHttpStatusMessage());
		for (Iterator<String> iterator = handshakedata.iterateHttpFields(); iterator.hasNext();) {
			String field = iterator.next();
			console(strings.getString("log.httpHeader") + " " + field + ": " + handshakedata.getFieldValue(field));
		}
	}
	
	/**
	 * Chamado quando uma mensagem é recebida do servidor.
	 */
	public void onMessage(String message) {
		window.onMessage(message);
		console(message);
	}
	
	/**
	 * Chamado após a conexão com o servidor ter sido finalizada.
	 */
	public void onClose(int code, String reason, boolean remote) {
		window.onClose(code, reason, remote);
		console(strings.getString("log.connectionClosed"));
		console(strings.getString("log.connectionClosed.code") + " " + code);
		console(strings.getString("log.connectionClosed.reason") + " " + reason);
		console(remote ? strings.getString("log.connectionClosed.byPeer") : strings.getString("log.connectionClosed.byClient"));
	}
	
	/**
	 * Chamado quando uma exceção interna ao {@link org.java_websocket.client.WebSocketClient} ocorre.
	 */
	public void onError(Exception ex) {
		window.onError(ex);
	}
	
	/**
	 * Chamado para enviar texto ao servidor.
	 */
	public void send(String text) throws NotYetConnectedException {
		console(strings.getString("log.sendPrefix.text") + text);
		super.send(text);
	}
	
	/**
	 * Chamado para enviar dados brutos ao servidor.
	 */
	public void send(byte[] data) throws NotYetConnectedException {
		console(strings.getString("log.sendPrefix.bytes") + new String(data));
		super.send(data);
	}
	
	/**
	 * Chamado quando o cliente deseja finalizar a conexão.
	 */
	public void close() {
		console(strings.getString("log.connectionClosing"));
		super.close();
	}
}
