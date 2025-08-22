package kafka.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    private int amount;

    private String currency;

    private String bankAccountNumber;

    private String note;

    public String calculateHash() throws NoSuchAlgorithmException {
        String data = amount + "//" + currency + "//" + bankAccountNumber ;
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(data.getBytes());
        StringBuilder hashBuilder = new StringBuilder();
        for(byte b : hashBytes){
            hashBuilder.append(String.format("%02x", b));
        }
        return hashBuilder.toString();

    }

    private LocalDate paymentDate;


}
