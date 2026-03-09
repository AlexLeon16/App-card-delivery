package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import io.github.bonigarcia.wdm.WebDriverManager;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private String generateDate(int days) {
        return LocalDate.now().plusDays(days)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldSubmitFormWithValidData() {
        
        open("http://localhost:9999");

        String planningDate = generateDate(3);

        $("[data-test-id=city] input").setValue("Казань");

//        задание 2
//        $("[data-test-id=city] input").setValue("Ка");
        $$(".menu-item").find(Condition.text("Казань")).click();

        // дата может быть предзаполнена — чистим через Ctrl+A + Delete
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);

//      Задание 2
//      $("[data-test-id=date] button").click(); // кнопка календаря, если есть
//        $$(".calendar__day").find(Condition.text("25")).click();

        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();

        $$("button").find(Condition.text("Забронировать")).click();

        // уведомление об успехе (обычно data-test-id=notification)
        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Успешно!"))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}
