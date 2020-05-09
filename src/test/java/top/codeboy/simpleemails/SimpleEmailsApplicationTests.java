package top.codeboy.simpleemails;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SimpleEmailsApplicationTests {

    @Autowired
    JavaMailSender javaMailSender;

    /**
     * 发送简单邮件
     */
    @Test
    void contextLoads() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("这是测试邮件主题");
        simpleMailMessage.setText("This is Test Email Content");
        simpleMailMessage.setFrom("776303560@qq.com");
        simpleMailMessage.setSentDate(new Date());
        simpleMailMessage.setTo("2685359919@qq.com");
        //邮件抄送
//        simpleMailMessage.setCc();
        //邮件密抄
//        simpleMailMessage.setBcc();
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送附件信息邮件
     * @throws MessagingException
     */
    @Test
    public void test() throws MessagingException {
        MimeMessage  msg= javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setSubject("发送一封测试邮件(带附件)");
        helper.setText("传递一封有附件资料的邮件内容");
        helper.setFrom("776303560@qq.com");
        helper.setSentDate(new Date());
        helper.setTo("2685359919@qq.com");
        //添加邮件所需的附件内容
        helper.addAttachment("pikachu.jpg",
                new File("C:\\Users\\Think\\Documents\\WeChat Files\\All Users\\pikachu.jpg"));
        javaMailSender.send(msg);
    }

    /**
     * 发送带图片的邮件
     * @throws MessagingException
     */
    @Test
    public void send() throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setSubject("发送一封测试邮件(带图片)");
        helper.setText("相关图片信息：这是第一张图片：<img src='cid:p01' />"+"\n"+"这是第二张图片：<img src='cid:p02' />", true);
        helper.setFrom("776303560@qq.com");
        helper.setSentDate(new Date());
        helper.setTo("2685359919@qq.com");
        helper.addInline("p01",new FileSystemResource(
                new File("C:\\Users\\Think\\Documents\\WeChat Files\\All Users\\pikachu.jpg")));
        helper.addInline("p02",new FileSystemResource(
                new File("C:\\Users\\Think\\Documents\\WeChat Files\\All Users\\handsomedog.jpg")));
        javaMailSender.send(msg);
    }

    @Autowired
    TemplateEngine templateEngine;

    /**
     * 发送邮件模板1(Thymeleaf)
     * @throws MessagingException
     */
    @Test
    public void display() throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setSubject("测试邮件主题(Thymeleaf模板)");
        Context context = new Context();
        context.setVariable("username","codeboy");
        context.setVariable("position","Java工程师");
        context.setVariable("department","产品研发部");
        context.setVariable("salary",99999);
        context.setVariable("job","高级工程师");
        String process = templateEngine.process("mail.html", context);
        helper.setText(process,true);
        helper.setFrom("776303560@qq.com");
        helper.setSentDate(new Date());
        helper.setTo("2685359919@qq.com");
        javaMailSender.send(msg);
    }

    /**
     * 发送邮件模板2(Freemarker)
     * @throws MessagingException
     */
    @Test
    public void show() throws MessagingException, IOException, TemplateException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setSubject("测试邮件主题(Freemarker模板)");
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(),"templates");
        Template template = configuration.getTemplate("mail.ftlh");
        Map<String,Object> map = new HashMap<>();
        map.put("username","codeboy");
        map.put("position","Java程序员");
        map.put("job","高级工程师");
        map.put("salary",9999);
        map.put("department","产品研发部");
        StringWriter out = new StringWriter();
        template.process(map,out);
        helper.setText(out.toString(),true);
        helper.setFrom("776303560@qq.com");
        helper.setSentDate(new Date());
        helper.setTo("2685359919@qq.com");
        javaMailSender.send(msg);
    }

}
