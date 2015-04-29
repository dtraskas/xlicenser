/*
 *	XLicenser Source Code
 *	Copyright (C) 2015 XINFERIN Technologies 
 *  
 * 	All Rights Reserved
 * 
 */

package com.xinferin.licensing;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LicenceGenerator {
	
	private PrivateKey privateKey; 
	private PublicKey publicKey; 
	
	private byte[] encodedPrivateKey;
	private String encodedToXMLPublicKey;
	
	public LicenceGenerator(byte[] encodedPrivateKey){
		if (encodedPrivateKey == null){
			firstTimeInitialisation(1024);
		} else {
			this.encodedPrivateKey = encodedPrivateKey;
		}
	}
	
	/**
	 * Creates a new private and public key and at the same time encodes the public key as XML to be used by the .NET client
	 * @param size
	 * @param productId
	 *
	 */
	private void firstTimeInitialisation(int size) {
		try {
			
			// Get Key Pair Generator for RSA.
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(size);
	  
			KeyPair keypair = keyGen.genKeyPair();
			privateKey = keypair.getPrivate();
			publicKey = keypair.getPublic();
	
			// Get the bytes of the public and private keys
			byte[] privateKeyBytes = privateKey.getEncoded();
			byte[] publicKeyBytes = publicKey.getEncoded();
	
			// store temporarily witht he public key for the lifetime of this class.
			encodedPrivateKey = new Base64().encode(privateKeyBytes);
			
			// Generate the Private Key, Public Key and Public Key in XML format.
			KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
			KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
			RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
	  
			// Store the public key in XML string to make compatible .Net public key file
			encodedToXMLPublicKey = getRSAPublicKeyAsXMLString(rsaPublicKey);
			
	    } catch (Exception ex) {
	    	System.out.println(ex.getMessage());
	    }
	}
	
	/**
	 * Signs the data and returns the signature.
	 * @param toBeSigned Data to be signed
	 * @return byte[] Signature
	 * @throws Exception 
	*/
	public byte[] signData(byte[] toBeSigned) throws Exception {
		
		try {
			if (privateKey == null) 
				initialisePrivateKey();	   
			
	    	Signature signatureInstance = Signature.getInstance("SHA1withRSA");
	    	signatureInstance.initSign(privateKey);
	    	signatureInstance.update(toBeSigned);
	    	
	    	return signatureInstance.sign();	    	

	    } catch (NoSuchAlgorithmException ex) {
	    	throw new Exception("The SHA1withRSA algorithm was not found. " + ex.getCause());
	    } catch (InvalidKeyException in) {
	    	throw new Exception("Invalid key returned from database. " + in.getCause());
	    } catch (SignatureException se) {
	    	throw new Exception("No signature instance can be created. " + se.getCause());
	    }
	}
	  
	/**
	 * Gets an RSA public key as String
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private String getRSAPublicKeyAsXMLString(RSAPublicKey key) throws UnsupportedEncodingException, ParserConfigurationException, TransformerException {
		Document xml = getRSAPublicKeyAsXML(key);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter sw = new StringWriter();
		transformer.transform(new DOMSource(xml), new StreamResult(sw));
		
		return sw.getBuffer().toString();
	}
	
	/**
	 * Gets an RSA Public key as an xml document
	 * 
	 * @param key
	 * @return
	 * @throws ParserConfigurationException
	 * @throws UnsupportedEncodingException
	 */
	private Document getRSAPublicKeyAsXML(RSAPublicKey key) throws ParserConfigurationException, UnsupportedEncodingException {
		
		Document result = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
	    Element rsaKeyValue = result.createElement("RSAKeyValue");
	    result.appendChild(rsaKeyValue);
	    
	    Element modulus = result.createElement("Modulus");
	    rsaKeyValue.appendChild(modulus);
	
	    byte[] modulusBytes = key.getModulus().toByteArray();
	    modulusBytes = stripLeadingZeros(modulusBytes);
	    modulus.appendChild(result.createTextNode(new String(Base64.encodeBase64(modulusBytes))));
			
	    Element exponent = result.createElement("Exponent");
	    rsaKeyValue.appendChild(exponent);
	
	    byte[] exponentBytes = key.getPublicExponent().toByteArray();
	    exponent.appendChild(result.createTextNode(new String(Base64.encodeBase64(exponentBytes))));
	    		
	    return result;
	}
	
	/**
	 * Main method to initialise the private key from the database
	 * @throws Exception
	 */
	private void initialisePrivateKey() throws Exception {
		
		if (encodedPrivateKey == null)
			throw new Exception("An encoded Base64 private key has not been specified.");
			
		try {
			//Read key files back and decode them from BASE64
			byte[] privateKeyBytes = Base64.decodeBase64(encodedPrivateKey);
	    	
			// Convert back to public and private key objects
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			privateKey = keyFactory.generatePrivate(privateKeySpec);
			
	    } catch (InvalidKeySpecException e) {
	    	throw new Exception("Invalid key specifications. Not a valid key entry." + e.getCause());
	    } catch (NoSuchAlgorithmException e) {
	    	throw new Exception("There is no such algorithm. Please check the JDK ver." + e.getCause());
	    }
	}
	
	/**
	 * Utility method to delete the leading zeros from the modulus.
	 * @param a modulus
	 * @return modulus
	 */
	private byte[] stripLeadingZeros(byte[] a) {
		int lastZero = -1;
	    for (int i = 0; i < a.length; i++) {
	      if (a[i] == 0) {
	        lastZero = i;
	      }
	      else {
	        break;
	      }
	    }
	    lastZero++;
	    byte[] result = new byte[a.length - lastZero];
	    System.arraycopy(a, lastZero, result, 0, result.length);
	    return result;
	}

	public String getEncodedToXMLPublicKey() {
		return encodedToXMLPublicKey;
	}
	
	public byte[] getEncodedPrivateKey() {
		return encodedPrivateKey;
	}

	public void setEncodedPrivateKey(byte[] encodedPrivateKey) {
		this.encodedPrivateKey = encodedPrivateKey;
	}
}
