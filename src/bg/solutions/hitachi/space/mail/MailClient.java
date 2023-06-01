package bg.solutions.hitachi.space.mail;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class MailClient {
    private static final String FILE_PATH = "./src/bg/solutions/hitachi/space/report/WeatherReport.csv";
    private final String senderEmailAddress;
    private final String password;
    private final String receiverEmailAddress;

    private final boolean isGermanSet;

    private final int perfectDayToLaunch;

    public MailClient(String senderEmailAddress, String password, String receiverEmailAddress, int perfectDayToLaunch,
                      boolean isGermanSet) {
        this.senderEmailAddress = senderEmailAddress;
        this.password = password;
        this.receiverEmailAddress = receiverEmailAddress;
        this.perfectDayToLaunch = perfectDayToLaunch;
        this.isGermanSet = isGermanSet;
    }

    public String sendEmail() {
        Properties prop = configureProperties();
        Session session = createSession(prop);

        try {
            Message message = new MimeMessage(session);
            addDataToMessage(message);

            Transport.send(message);

            if (isGermanSet) {
                return "Nachricht erfolgreich gesendet!";
            }

            return "Successfully sent message!";
        } catch (MessagingException | IOException e) {
            if (isGermanSet) {
                return "Beim Senden der Nachricht ist ein Fehler aufgetreten! " +
                    "Bitte versuchen Sie es erneut, indem Sie die korrekte E-Mail-Adresse des Absenders, " +
                    "das Passwort des Absenders und die E-Mail-Adresse des Empfängers eingeben!";
            }

            return "There was error while sending the message! Please try again by inputting correct sender email, " +
                "sender password and receiver email!";
        }

    }

    private Properties configureProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.office365.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.office365.com");

        return prop;
    }

    private Session createSession(Properties prop) {
        return Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmailAddress, password);
            }
        });
    }

    private void addDataToMessage(Message message) throws MessagingException, IOException {
        message.setFrom(new InternetAddress(senderEmailAddress));
        Address addressTo = new InternetAddress(receiverEmailAddress);
        message.setRecipient(Message.RecipientType.TO, addressTo);

        message.setSubject("Weather report for launching space shuttle");
        message.setText("Based on the given data, the perfect day to launch the space shuttle is "
            + perfectDayToLaunch);

        MimeMultipart multipart = new MimeMultipart();
        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(new File(FILE_PATH));

        multipart.addBodyPart(attachment);
        message.setContent(multipart);
    }
}