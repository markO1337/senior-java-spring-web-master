package hu.ponte.hr.service.signer;

import hu.ponte.hr.service.signer.model.SignType;

public interface SignerService {

    SignType getType();

    byte[] execute(byte[] data);

}
