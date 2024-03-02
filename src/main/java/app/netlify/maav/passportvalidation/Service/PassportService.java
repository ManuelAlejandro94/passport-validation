package app.netlify.maav.passportvalidation.Service;

import org.springframework.stereotype.Service;

import app.netlify.maav.passportvalidation.Model.Passport;
import app.netlify.maav.passportvalidation.Model.Zml;

@Service
public class PassportService {

    private final int LENGTH_LETTER = 44;
    private final String SYMBOL_SPACE= "<";
    private final int LENGTH_PASSPORD_NUMBER=9;
    private final String WEIGHTING="731";
    
    public Zml generateZml(Passport passport){
        Zml zml = new Zml();
        String firstLine;
        String secondLine;

        String fullName = passport.getLastName().replace(" ", SYMBOL_SPACE) + SYMBOL_SPACE.repeat(2) + passport.getFirstName().replace(" ", this.SYMBOL_SPACE);
        firstLine = passport.getType() + this.SYMBOL_SPACE + passport.getCountry() + fullName;
        String birthDate = convertToYYMMdd(passport.getDateOfBirth());
        secondLine = lengthPassportNumber(passport.getPassportNumber()) + digitVerification(passport.getPassportNumber(), 'p') + passport.getCountry() + birthDate + digitVerification(birthDate, 'd');

        zml.setFirstLine(this.lenghtZml(firstLine));
        zml.setSecondLine(this.lenghtZml(secondLine));

        return zml;
    }

    private String lenghtZml(String mechanicalLine){
        String line = mechanicalLine;

        if(mechanicalLine.length() < this.LENGTH_LETTER){
            for(int i = mechanicalLine.length()+1; i <= this.LENGTH_LETTER; i++ ){
                line += SYMBOL_SPACE;
            }
        }
        else if (mechanicalLine.length() > this.LENGTH_LETTER) {
            line = line.substring(0, Math.min(line.length(), this.LENGTH_LETTER));
        }

        return line;
    }

    private String lengthPassportNumber(String passportNumber){
        String textPassport = passportNumber;

        if (passportNumber.length() < this.LENGTH_PASSPORD_NUMBER) {
            for (int i = passportNumber.length(); i < this.LENGTH_PASSPORD_NUMBER; i++) {
                textPassport += SYMBOL_SPACE;
            }
        }
        
        return textPassport;
    }

    private String convertToYYMMdd(String date){
        String newDate;
        String cleanDate = date.replace("/", "");

        newDate = cleanDate.substring(6, 8) + cleanDate.substring(2, 4) + cleanDate.substring(0, 2);
        return newDate;
    }

    private int digitVerification(String value, char type){
        int digitVerificator;
        int index = 0;
        int countWeighting = 0;
        int arrayInit = 0;
        switch (type) {
            case 'd':
                arrayInit = 6;
                break;
            case 'p':
                arrayInit = 9;
                break;
            default:
                break;
        }
        int products[] = new int[arrayInit];
        int sumProducts = 0;
        char weightingArray[] = this.WEIGHTING.toCharArray();

        for (char digit : value.toCharArray()) {
            char character = ((digit == '<') ? '0': digit);
            products[index] = Character.getNumericValue(character) * Character.getNumericValue(weightingArray[countWeighting]);
            index++;
            countWeighting++;
            if (countWeighting == 3) {
                countWeighting = 0;
            }
        }

        for (int i = 0; i < products.length; i++) {
            sumProducts += products[i];
        }

        digitVerificator = sumProducts%10;

        return digitVerificator;
    }
}
