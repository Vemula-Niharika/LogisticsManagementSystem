package com.alpha.logistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.alpha.logistics.entity.EmailType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String toEmail, EmailType type, String extraMessage) {

        log.info("Preparing to send {} email to {}", type, toEmail);

        String subject = "";
        String body = "";

        switch (type) {

            case ORDER_PLACED:
                subject = "Order Placed";
                body = "Your order has been placed successfully.";
                break;

            case TRUCK_ASSIGNED:
                subject = "Truck Assigned";
                body = "Truck assigned successfully.";
                break;

            case DRIVER_ASSIGNED:
                subject = "Driver Assigned";
                body = "Driver has been assigned to your shipment.";
                break;

            case ORDER_COMPLETED:
                subject = "Order Completed";
                body = "Your shipment has been delivered successfully.";
                break;
        }

        // Add dynamic message (like cost)
        if (extraMessage != null) {
            body = body + "\n" + extraMessage;
        }

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

        log.info("Email sent successfully to {}", toEmail);
    }
}