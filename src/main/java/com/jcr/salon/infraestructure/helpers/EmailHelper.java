package com.jcr.salon.infraestructure.helpers;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;


import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailHelper {
  private final JavaMailSender mailSender;

  public void sendEmail(String destinity, String nameClient, LocalDateTime date, String employeeName) {
       MimeMessage message = mailSender.createMimeMessage();

       DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      String dateAppointment = date.format(dateFormat);
       String htmlContent = this.readHtmlTemplate(nameClient, employeeName, dateAppointment);


       try {
        message.setFrom(new InternetAddress("pachoregino2001@gmail.com"));
        message.setSubject("Confirmacion de cita en Jimena Salon");
        message.setRecipients(MimeMessage.RecipientType.TO, destinity);
        message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);

        mailSender.send(message);
        System.out.println("Email enviado");
       } catch (Exception e) {
        System.out.println("ERROR al enviar el email" + e.getMessage());
       }
  }

  //Metodo paraleer el template
  private String readHtmlTemplate(String nameClient, String employeeName, String date) {
    //Indicar donde se encuentra el archivo html 
    final Path path = Paths.get("src/main/resources/templates/emails/email.html");
  // EL primero lee lo que esta en los parentecis y si falla de una lo tira al catch
    try(var lines = Files.lines(path)){
      var html = lines.collect(Collectors.joining());

      return html.replace("${employee}", employeeName).replace("${date}", date).replace("${name}", nameClient);
    }catch(IOException e){
      System.out.println("no se pudo leer el HTML");
      throw new RuntimeException(e);
    }
  }
}
