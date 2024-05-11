package lk.ijse.gdse.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextField textField,String text){
        String field = "";

        switch (textField){
            case ID :
                field = "^([A-Z][0-9]{3})$";
                break;
            case NAME:
                field = "^[A-z|\\\\s]{3,}$";
                break;
            case TEL:
                field = "^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$";
                break;
            case NIC:
                field = "^([0-9]{9}[x|X|v|V]|[0-9]{12})$";
                break;
            case SALARY:
                field = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case AMOUNT:
                field = "^([0-9]){1,}[.]([0-9]){1,}$";
                break;
            case INT:
                field = "^\\\\d+$";
        }

        Pattern pattern = Pattern.compile(field);

        if (text != null) {
            if (text.trim().isEmpty()) {
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField) {
        if (Regex.isTextFieldValid(location, textField.getText())) {

            textField.setStyle("-fx-text-fill: Black; ");
        } else {
            textField.setStyle("-fx-text-fill: red; ");

            return false;
        }
        return false;
    }
}
