package app.netlify.maav.passportvalidation.Service;

import org.springframework.stereotype.Service;

import app.netlify.maav.passportvalidation.Model.Passport;
import app.netlify.maav.passportvalidation.Model.Zml;

@Service
public class PassportService {

    private final int LENGTH_LETTER = 44;
    private final String SYMBOL_SPACE= "<";
    private final int LENGTH_PASSPORD_NUMBER=9;
    private final int LENGHT_VALIDATOR=10;
    private final String WEIGHTING="731";
    
    public Zml generateZml(Passport passport){
        Zml zml = new Zml();
        String firstLine;
        String secondLine;

        String fullName = passport.getLastName().replace(" ", SYMBOL_SPACE) + SYMBOL_SPACE.repeat(2) + passport.getFirstName().replace(" ", this.SYMBOL_SPACE);
        firstLine = passport.getType() + this.SYMBOL_SPACE + passport.getCountry() + fullName;

        String birthDate = convertToYYMMdd(passport.getDateOfBirth());
        String expireDate = convertToYYMMdd(passport.getDateExpiry());
        int expireVerification = digitVerification(expireDate);
        String passportFormat = lengthElementToVerification(passport.getPassportNumber(), LENGTH_PASSPORD_NUMBER);
        int passportVerification = digitVerification(passport.getPassportNumber());
        int birthVerification = digitVerification(birthDate);
        String optionalFormat = lengthElementToVerification(passport.getOptionalData(), 14);
        int optionalVerification = digitVerification(optionalFormat);
        secondLine = passportFormat + passportVerification + passport.getCountry() + birthDate + birthVerification + passport.getSex() + expireDate + expireVerification +
                        optionalFormat + optionalVerification;
        
        String passportToVerify = passportFormat + passportVerification;
        String birthToVerify = lengthElementToVerification(birthDate + birthVerification + expireDate, LENGHT_VALIDATOR);
        String expirySub = expireDate.substring(3);
        String expiryToVerify = lengthElementToVerification(expirySub + expireVerification + optionalFormat, LENGHT_VALIDATOR);
        String optionalSub = optionalFormat.substring(6);
        String optionalToVerify = lengthElementToVerification(optionalSub + optionalVerification, LENGTH_PASSPORD_NUMBER);

        int verificationSecondLine = verificationPassport(passportToVerify, birthToVerify, expiryToVerify, optionalToVerify);
        secondLine += verificationSecondLine;

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

    private String convertToYYMMdd(String date){
        String newDate;
        String cleanDate = date.replace("/", "");

        newDate = cleanDate.substring(6, 8) + cleanDate.substring(2, 4) + cleanDate.substring(0, 2);
        return newDate;
    }

    private String lengthElementToVerification(String element, int size){
        String newElement = element;

        if(element == null){
            element = "";
            newElement = "";
        }

        if(element.length() < size){
            for(int i = element.length()+1; i <= size; i++ ){
                newElement += SYMBOL_SPACE;
            }
        }
        else if (element.length() > size) {
            newElement = newElement.substring(0, Math.min(newElement.length(), size));
        }

        return newElement;
    }

    private int digitVerification(String value){
        int digitVerificator = 0;
        int sumProducts = 0;
        int[] products = productVerification(value);

        for(int i = 0; i < products.length; i++){
            sumProducts += products[i];
        }

        if (sumProducts > 0) {
            digitVerificator = sumProducts%10;
        }

        return digitVerificator;
    }

    private int[] productVerification(String value){
        int index = 0;
        int countWeighting = 0;
        int arrayInit = value.length();
        int products[] = new int[arrayInit];
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

        return products;
    }

    private int[] productVerification(String value, int beginIndexWeighting){
        int index = 0;
        int countWeighting = beginIndexWeighting;
        int arrayInit = value.length();
        int products[] = new int[arrayInit];
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

        return products;
    }

    private int verificationPassport(String passNumber10, String birthPlusDigitPlusNumber10, String expiryMissingPlus10, String optionalDataPlusDigit9){
        int digitVerificator;
        int sumProducts = 0;
        int[][] products = {
            productVerification(passNumber10),
            productVerification(birthPlusDigitPlusNumber10,1),
            productVerification(expiryMissingPlus10, 2),
            productVerification(optionalDataPlusDigit9)
        };

        for (int i = 0; i < products.length; i++) {
            for (int j = 0; j < products[i].length; j++) {
                sumProducts += products[i][j];
            }
        }

        digitVerificator = sumProducts%10;

        return digitVerificator;
    }
}
