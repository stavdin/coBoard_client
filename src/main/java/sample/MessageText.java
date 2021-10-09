package sample;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MessageText extends Text {

    public MessageText(String text)
    {
        super(text);
        setFont(Font.font("Arial Rounded MT Bold", 14));

    }

    //TODO create method that changes text color - will be called from roomPage
}
