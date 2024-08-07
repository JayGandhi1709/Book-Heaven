// package com.example.BookHeaven.service;

// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;

// public class EmailService {

// private final JavaMailSender javaMailSender;

// public EmailService(JavaMailSender javaMailSender) {
// this.javaMailSender = javaMailSender;
// }

// public void sendEmail(String to, String subject, String text) {
// SimpleMailMessage message = new SimpleMailMessage();
// message.setTo(to);
// message.setSubject(subject);
// message.setText(text);

// javaMailSender.send(message);
// }
// }
