package hu.ponte.hr.service.signer;

import hu.ponte.hr.exception.FileSignatureException;
import hu.ponte.hr.service.signer.model.SignType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Service
@RequiredArgsConstructor
public class Sha256RsaSigner implements SignerService {
    private static final String PRIVATE_KEY_LOCATION = "classpath:config/keys/key.private";

    private final ResourceLoader resourceLoader;

    @Override
    public SignType getType() {
        return SignType.SHA_256_RSA;
    }

    @Override
    public byte[] execute(byte[] data) {
        try {
            Signature signature = Signature.getInstance(getType().getAlgorithmName());
            File privateKeyFile = resourceLoader.getResource(PRIVATE_KEY_LOCATION).getFile();
            signature.initSign(loadPrivateKey(privateKeyFile));
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            throw new FileSignatureException(getType(), e);
        }
    }

    private PrivateKey loadPrivateKey(File file) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(FileUtils.readFileToByteArray(file));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

}
