package PC_Pay_point_DEL.PC_Pay_point_DEL;

import static com.codeborne.selenide.Selenide.open;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.WebDriverRunner;

public class AppTest {
	public static WebDriver driver;

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		WebDriverRunner.setWebDriver(driver);

		// 브라우저 오픈
		open("https://front-qa.wemakeprice.com/product/100055424");

		try {
			File f2 = new File("login.txt");
			FileReader fr = new FileReader(f2);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer str = new StringTokenizer(line, ";");
				while (str.hasMoreTokens()) {
					String name = str.nextToken();
					String value = str.nextToken();
					String domain = str.nextToken();
					String path = str.nextToken();
					Date expiry = null;
					String dt;
					if (!(dt = str.nextToken()).equals("null")) {
						// expiry = new Date(dt);
					}
					boolean isSecure = new Boolean(str.nextToken()).booleanValue();
					Cookie ck = new Cookie(name, value, domain, path, expiry, isSecure);
					driver.manage().addCookie(ck);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Test
	public void test() {

		try {
			open("https://front-qa.wemakeprice.com/product/100055424");
			$(By.xpath("//span[contains(.,'구매하기')]")).click();
		} catch (Exception e) {
			e.printStackTrace();

		}

		// 전화번호 입력
		$(By.id("mobile2")).sendKeys("3381");
		$(By.id("mobile3")).sendKeys("3358");

		// 포인트 결제
		$(By.xpath("//label[@id='allPointUse']/span")).click();
		$(By.xpath("//div[4]/input")).sendKeys("qwer1234");
		$(By.xpath("//span[contains(.,'확인')]")).click();

		// 약관공의 후 결제
		$(By.xpath("//div[3]/div/div/div/label/span")).click();
		$(By.xpath("//span[contains(.,'결제하기')]")).click();

		try {
			$(By.xpath("//span[contains(.,'구매내역 확인')]")).getText(); // 버튼이 있는지 체크
			screenshot("pay");
		} catch (Exception e) {
			System.out.println("구매목록 확인 버튼이 없습니다.(구매실패 또는 완료화면 비노출)" + e);
			screenshot("pay");
		}
	}

	@AfterMethod
	public void end() {

	}

}