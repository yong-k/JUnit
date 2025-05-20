package com.junit.demo.util;

// 추후에 Mail 클래스가 완성되면 코드 완성할거임
public class MailSenderAdapter implements MailSender {
    /*
    private Mail mail;

    public MailSenderAdapter(Mail mail) {
        this.mail = new Mail();
    }
    */

    @Override
    public boolean send() {
        return true;
    }
}
