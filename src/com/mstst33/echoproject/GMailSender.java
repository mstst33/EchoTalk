package com.mstst33.echoproject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GMailSender extends javax.mail.Authenticator {
	private String mailhost = "smtp.gmail.com";
	private String user;
	private String password;
	private Session session;

	public GMailSender(String user, String password) {
		this.user = user;
		this.password = password;

		Properties props = new Properties();

		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.host", mailhost);
		props.setProperty("mail.smtp.quitwait", "false");
		props.put("mail.smtp.port", "465");
		// props.put("mail.smtp.user", user);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		session = Session.getDefaultInstance(props, this);
		// session.setDebug(true);
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}

	public synchronized void sendMail(String subject, String body,
			String sender, String recipients, String filename,
			String filename2, String filename3) throws Exception {
		MimeMessage message = new MimeMessage(session);
		// DataHandler handler = new DataHandler(new ByteArrayDataSource(
		// body.getBytes(), "text/html"));
		// message.setDataHandler(handler);
		message.setSender(new InternetAddress(sender));
		message.setSubject(subject);
		if (recipients.indexOf(',') > 0)
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipients));
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(
					recipients));

		message.setHeader("X-Mailer", "ANDROES");
		message.setHeader("Content-type", "text/html; charset=euc-kr");

		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(body);
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		if (filename != "") {
			File file = new File(filename);
			if (file.exists()) {
				messageBodyPart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(filename);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setFileName(filename);
				multipart.addBodyPart(messageBodyPart);
			}
		}

		if (filename2 != "") {
			File file = new File(filename2);
			if (file.exists()) {
				messageBodyPart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(filename2);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setFileName(fds.getName());
				multipart.addBodyPart(messageBodyPart);
			}
		}

		if (filename3 != "") {
			File file = new File(filename3);
			if (file.exists()) {
				messageBodyPart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(filename3);
				messageBodyPart.setDataHandler(new DataHandler(fds));
				messageBodyPart.setFileName(fds.getName());
				multipart.addBodyPart(messageBodyPart);
			}
		}

		// Multipart
		message.setContent(multipart);
		message.setSentDate(new Date());
		Transport.send(message);
	}

	public class ByteArrayDataSource implements DataSource {
		private byte[] data;
		private String type;

		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getContentType() {
			if (type == null)
				return "application/octet-stream";
			else
				return type;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public String getName() {
			return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
	}
}