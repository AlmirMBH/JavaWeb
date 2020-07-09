package servleti;

// klasa i metoda gdje ce se raditi generacija passworda

import java.util.Random;

public class PasswordGenerator2 {
    private static final String MOGUCA_SLOVA = "abcdjklmnoprstuvzs123456789";
    private static final int MINIMUM_LENGTH = 5;    
    
    public String generatePassword(){
       
        Random random = new Random();
        int duzinaLozinke = MINIMUM_LENGTH + random.nextInt(11);
        StringBuilder sb = new StringBuilder();// deklaracija van petlje, punimo ga u petlji
        
        for(int i =0; i<duzinaLozinke; i++){
            int slucajnaPozicija = random.nextInt(MOGUCA_SLOVA.length());
            char slucajniKarakter = MOGUCA_SLOVA.charAt(slucajnaPozicija);
        sb.append(slucajniKarakter);
        }
    
     return sb.toString();   
    }    
}
