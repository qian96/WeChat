package hogwarts;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @Author wangqian
 * @Date 2020-11-11 22:23
 * @Version 1.0
 */
public class WeChatTest {

    @Test
    public void login() throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://work.weixin.qq.com/wework_admin/frame");

        Thread.sleep(20000);

        Set<Cookie> cookies = driver.manage().getCookies();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        mapper.writeValue(new File("cookies.yaml"),cookies);

    }
   @Test
    public void logined() throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://work.weixin.qq.com/wework_admin/frame");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference typeReference = new TypeReference<List<HashMap<String,Object>>>() {};

        List<HashMap<String,Object>> cookies = (List<HashMap<String, Object>>) mapper.readValue(new File("cookies.yaml"),typeReference);
        System.out.println(cookies);

        cookies.forEach(cookieMap->{
            driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(),cookieMap.get("value").toString()));
        });
        driver.navigate().refresh();

        driver.findElements(By.cssSelector(".index_service_cnt_item_title")).get(0).click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.id("username")).sendKeys("abcd");
        driver.findElement(By.id("memberAdd_english_name")).sendKeys("ccc");
        driver.findElement(By.id("memberAdd_acctid")).sendKeys("17666666@qq.com");

        driver.findElements(By.xpath("//label[@class=\"ww_label ww_label_Middle\"]")).get(1).click();

        driver.findElement(By.id("memberAdd_mail")).sendKeys("1767666666@qq.com");

        driver.findElements(By.xpath("//a[@class=\"qui_btn ww_btn js_btn_save\"]")).get(1).click();

        Thread.sleep(3000);

       // driver.quit();
    }

}
