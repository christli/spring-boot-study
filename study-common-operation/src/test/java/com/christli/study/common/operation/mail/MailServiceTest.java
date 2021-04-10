package com.christli.study.common.operation.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

//用的时候，在配置文件填好账号密码，再打开sendSimpleMail等注释
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSimpleMail() throws Exception {
//        mailService.sendSimpleMail("christli@meijiabang.cn","test simple mail"," hello this is simple mail");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
//        mailService.sendHtmlMail("christli@meijiabang.cn","test simple mail",content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath="/Users/libinbin/picture/头像/christ.jpg";
//        mailService.sendAttachmentsMail("christli@meijiabang.cn", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }


    @Test
    public void sendInlineResourceMail() {
        String rscId = "neo006";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "/Users/libinbin/picture/头像/christ.jpg";

//        mailService.sendInlineResourceMail("christli@meijiabang.cn", "主题：这是有图片的邮件", content, imgPath, rscId);
    }


    @Test
    public void sendTemplateMail() {
        //创建邮件正文
        Context context = new Context();
        context.setVariable("id", "006");
        String emailContent = templateEngine.process("emailTemplate", context);

//        mailService.sendHtmlMail("christli@meijiabang.cn","主题：这是模板邮件",emailContent);
    }
}

