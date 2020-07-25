import com.yhq.userCenter.UserApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.security.RunAs;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: YHQ
 * @Date: 2020/7/9 18:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class test  {
    @Autowired
    JavaMailSender sender;
    @Autowired
    TemplateEngine templateEngine;
    @Test
    public void test() throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("915540781@qq.com");
        helper.setTo("915540781@qq.com");
        helper.setSubject("主题：简单邮件");
        helper.setText("测试邮件内容");
        helper.setSentDate(new Date());
        Context context = new Context();
        context.setVariable("username", "javaboy");
        context.setVariable("num","000001");
        context.setVariable("salary", "99999");
        String process = templateEngine.process("mail.html", context);
        helper.setText(process,true);
        sender.send(message);
    }

}
