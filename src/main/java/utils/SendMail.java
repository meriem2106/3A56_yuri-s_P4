package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class SendMail {

    public  void envoyerMail(String email,String Subject,String Object) {

        final String username = "rihem.benaissa@esprit.tn";
        final String password = "Live PC help";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        //Start our mail message
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(username));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject(Subject);

            Multipart emailContent = new MimeMultipart();

            //Text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(Object);

            emailContent.addBodyPart(textBodyPart);
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    public void send_mail_nour(String from, String pass, String to, String object, String subject) {

        String password = pass;
        String fromEmail = from;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);

            // HTML content with CSS styles
            String htmlContent = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body {" +
                    "font-family: Arial, sans-serif;" +
                    "background-color: #f4f4f4;" +
                    "margin: 0;" +
                    "padding: 0;" +
                    "}" +
                    ".container {" +
                    "max-width: 600px;" +
                    "margin: 20px auto;" +
                    "padding: 20px;" +
                    "background-color: #fff;" +
                    "border-radius: 10px;" +
                    "box-shadow: 0 0 10px rgba(0,0,0,0.1);" +
                    "}" +
                    "h1 {" +
                    "color: #333;" +
                    "}" +
                    "p {" +
                    "color: #666;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h1>Hello, World!</h1>" +
                    "<p>This is a test email with HTML content and CSS styles.</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            // Create MimeBodyPart for HTML content
            MimeBodyPart htmlBodyPart = new MimeBodyPart();
            htmlBodyPart.setContent(htmlContent, "text/html");

            // Create MimeBodyPart for plain text content
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(object);

            // Create Multipart to combine HTML and plain text content
            Multipart emailContent = new MimeMultipart();
            emailContent.addBodyPart(htmlBodyPart);
            emailContent.addBodyPart(textBodyPart);

            // Set the email content
            msg.setContent(emailContent);

            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
