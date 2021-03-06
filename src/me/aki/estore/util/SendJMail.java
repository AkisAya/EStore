package me.aki.estore.util;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendJMail implements Runnable {

	private String email;
	private String emailMsg;

    private final static String FROM = "kevinchan00@126.com";     //邮件发送人的邮件地址
    private final static String USERNAME = "kevinchan00@126.com"; //发件人的邮件帐户
    private final static String PASSWORD = "wangyi0320";          //发件人的邮件密码
    private final static String HOST = "smtp.126.com";


	public SendJMail(String email, String emailMsg) {
		this.email = email;
		this.emailMsg = emailMsg;
	}

	public boolean sendMail(String email, String emailMsg) {

		//定义Properties对象,设置环境信息
		Properties props = new Properties();

		//设置邮件服务器的地址
		props.setProperty("mail.smtp.host", HOST); // 指定的smtp服务器
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");//设置发送邮件使用的协议
		//创建Session对象,session对象表示整个邮件的环境信息
		Session session = Session.getInstance(props);
		//设置输出调试信息
		session.setDebug(true);
		try {
			//Message的实例对象表示一封电子邮件
			MimeMessage message = new MimeMessage(session);
			//设置发件人的地址
			message.setFrom(new InternetAddress(FROM));
			//设置主题
			message.setSubject("用户激活");
			//设置邮件的文本内容
			//message.setText("Welcome to JavaMail World!");
			message.setContent((emailMsg),"text/html;charset=utf-8");
			//从session的环境中获取发送邮件的对象
			Transport transport=session.getTransport();
			//连接邮件服务器
			transport.connect(HOST,25, USERNAME, PASSWORD);
			//设置收件人地址,并发送消息
			transport.sendMessage(message,new Address[]{new InternetAddress(email)});
			transport.close();
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void run() {
        sendMail(email, emailMsg);
	}
}
