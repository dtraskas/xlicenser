/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.licensing;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;

public class LicenceActivator {
	
	private String spublicKey; 	
	private PublicKey publicKey;
	
	public LicenceActivator(String spublicKey){
		this.spublicKey = spublicKey;
	}
	
	private void initialiseKeys() throws Exception {
		try {
			
			byte[] publicKeyBytes = Base64.decodeBase64(spublicKey);
    			
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			publicKey = keyFactory.generatePublic(publicKeySpec);
			
		} catch (InvalidKeySpecException e) {
			throw new Exception("Invalid Key Specs not valid Key files."+ e.getCause());
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("There is no such algorithm. Please check the JDK ver." + e.getCause());
		}
	}

	/**
	 * Verifies the signature for the given bytes using the public key.
	 * @param signature Signature
	 * @param data Data that was signed
	 * @return boolean True if valid signature else false
	 * @throws Exception 
	*/
	public boolean verifySignature(byte[] signature, byte[] data) throws Exception {
		
		try {
			initialiseKeys();
			Signature signatureInstance = Signature.getInstance("SHA1withRSA");
			signatureInstance.initVerify(publicKey);
			signatureInstance.update(data);
			
			return signatureInstance.verify(signature);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("There is no such algorithm. Please check the JDK ver." + e.getCause());
		} catch (SignatureException e) {
			throw new Exception("There is a problem with the signature provided " + e.getCause());
		}
	}
}
