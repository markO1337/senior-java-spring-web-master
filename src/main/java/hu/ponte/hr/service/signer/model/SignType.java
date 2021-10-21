package hu.ponte.hr.service.signer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignType {
    SHA_256_RSA("SHA256withRSA");

    private final String algorithmName;
}
