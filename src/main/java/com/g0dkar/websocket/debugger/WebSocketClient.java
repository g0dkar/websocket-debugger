package com.g0dkar.websocket.debugger;

import java.net.URI;
import java.nio.channels.NotYetConnectedException;
import java.util.Iterator;
import java.util.Map;
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
		this(window, serverUri, draft, null);
	}
	
	public WebSocketClient(final WebSocketsDebuggerWindow window, final URI serverUri, final Draft draft, final Map<String, String> headers) {
		super(serverUri, draft, headers, 0);
		this.window = window;
		strings = ResourceBundle.getBundle("strings");
	}
	
	/**
	 * Adiciona uma linha de texto ao Console.
	 * 
	 * @param texto Texto a ser incluso no console.
	 */
	private void console(final String texto) {
		window.console(texto);
	}
	
	/**
	 * Chamado após uma conexão ser feita com sucesso junto ao Servidor.
	 */
	public void onOpen(final ServerHandshake handshakedata) {
		window.onOpen(handshakedata);
		
		console(strings.getString("log.connectionStarted") + " " + handshakedata.getHttpStatus() + " " + handshakedata.getHttpStatusMessage());
		for (final Iterator<String> iterator = handshakedata.iterateHttpFields(); iterator.hasNext();) {
			final String field = iterator.next();
			console(strings.getString("log.httpHeader") + " " + field + ": " + handshakedata.getFieldValue(field));
		}
	}
	
	/**
	 * Chamado quando uma mensagem é recebida do servidor.
	 */
	public void onMessage(final String message) {
		window.onMessage(message);
		console(message);
	}
	
	/**
	 * Chamado após a conexão com o servidor ter sido finalizada.
	 */
	public void onClose(final int code, final String reason, final boolean remote) {
		window.onClose(code, reason, remote);
		console(strings.getString("log.connectionClosed"));
		console(strings.getString("log.connectionClosed.code") + " " + code);
		console(strings.getString("log.connectionClosed.reason") + " " + reason);
		console(remote ? strings.getString("log.connectionClosed.byPeer") : strings.getString("log.connectionClosed.byClient"));
	}
	
	/**
	 * Chamado quando uma exceção interna ao {@link org.java_websocket.client.WebSocketClient} ocorre.
	 */
	public void onError(final Exception ex) {
		window.onError(ex);
	}
	
	/**
	 * Chamado para enviar texto ao servidor.
	 */
	public void send(final String text) throws NotYetConnectedException {
		console(strings.getString("log.sendPrefix.text") + text);
		super.send(text);
	}
	
	/**
	 * Chamado para enviar dados brutos ao servidor.
	 */
	public void send(final byte[] data) throws NotYetConnectedException {
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
