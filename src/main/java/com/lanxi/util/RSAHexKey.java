package com.lanxi.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

//存放16进制字符串的RSA公钥和私钥的类
public class RSAHexKey {
	
	/* 16进制字符串形式的私钥 */
	public static String privateKey ;//
	/* 16进制字符串形式的公钥 */
	public static String publicKey ;//
	private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	public static String getPrivateKey() {
		return privateKey;
	}

	public static void setPrivateKey(String privateKey) {
		RSAHexKey.privateKey = privateKey;
	}

	public static String getPublicKey() {
		return publicKey;
	}

	public static void setPublicKey(String publicKey) {
		RSAHexKey.publicKey = publicKey;
	}

	public static void main(String[] args) throws Exception {

		RSAHexKey rsaHexKey=generateRSAKey();
		System.out.println("------->"+rsaHexKey.privateKey);
		System.out.println("------->"+rsaHexKey.publicKey);
		String card = "123456789";
		/*String jm = SecurityCodecUtil.encryptRSA(card,rsaHexKey.publicKey);
		System.out.println(jm);
		String jm1 = SecurityCodecUtil.decryptRSA(jm, rsaHexKey.privateKey);
		System.out.println(jm1);

		String s = SecurityCodecUtil.decryptRSA("7be2c665491d8a7efbdccc7ab90404ed7557fac785cc5ef600795bed004ec57903fcca47b13750484bddb137f68214e2e5352dcb1594722743b74dd28940ef66d19751fa60f05703664dbc6f2f3771d64f598c1f870aef703a77d604f997246b689aac1c1fabc9b00648b557508dbd895bb6ff8fbf9eca4a2c3411523a55ce3b", "30820278020100300d06092a864886f70d0101010500048202623082025e020100028181009a16c20911181ae64f9a28b629f43e131382a2e47baaeffd3b75985d31246ea5e93eaffe7a11eb7819b31b31cfddea0e501b476165891caf16df728a8eadfa2f0f4e1d8da9e2fe670afa2c9c5f7ced9e4363580d0de502f3fe5ea478885c50b8210c0d6d6a88b175e7f76e9a415754ba9b4df5ee3ca4e72bbae94627077c3e0d0203010001028181008c7fc5cb18a01f1ef364404ce172480b82d5b20b9dcb9e6a0b864d38642fb1086ce5f0f99b7aad76e41010f2d973a2100cf0e2a4273ddf3eb31f69447796a1304f674272bc6f9083e3a626c147a11b846d9ac76a6096460ea2c8d5d136e13b440d4350cbd1fb1510239b6147d550d84d39db98ed6b2e34d2640ab48ab605510d024100ccf20474890fdd25fd0f5fde99abf090069b59426ed509d597d531c78d35624070c03c401502bca400be0f7bb684f96ff322156124622991af166e78dde9a44b024100c079784b16c3baa432dbabd231674c00e34deaf7fb01e903f91de75cae752b7dfcf9658a635e9fe483afe17c3d5f51cd5ec2e077249bdd84f7124831a752400702410095381d80500f49e08ff972bf1bba71e41bbb5e691d5365deabb721907ec300af94a892f5c196858d0fb1528aabe0f3d0cc00e3fa1fc88d25b28aad92b95895330240171cff231542bfe103f27939ae9117016c4a69130a637a55edce77801bbb1a53f0d30a2c8a2e4cc9263f32b6b6b001a4c5e2650e29a945fab7a8cc5e9a1738750241008b9e0f913047bd0dba4fdc1b11d4133ea210f7358f1ef06bb7e07aaa6ce19f8399f344214777af0c20c0c766c21aafadf68c8487f64101175a85f7160b31082d");
		System.out.println(s);*/
	}

	/**
	 * 公钥加密过程
	 *
	 * @param publicKey     公钥
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception 加密过程中的异常信息
	 */
	public static byte[] encrypt(String publicKey, byte[] plainTextData)
			throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {

			BASE64Decoder base64 = new BASE64Decoder();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			RSAPublicKey rsaPublicKey = (RSAPublicKey)keyFactory.generatePublic(new X509EncodedKeySpec( base64.decodeBuffer(publicKey)));
			//X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);

			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	public static RSAHexKey generateRSAKey() throws Exception {
		try {
			KeyPairGenerator caKeyPairGen = KeyPairGenerator.getInstance("RSA");
			caKeyPairGen.initialize(1024, new SecureRandom());
			KeyPair keypair = caKeyPairGen.genKeyPair();

			RSAHexKey rsaKey = new RSAHexKey();

			Base64 base64 = new Base64();

			rsaKey.privateKey = new String(base64.encode(keypair.getPrivate().getEncoded()));
			rsaKey.publicKey = new String(base64.encode(keypair.getPublic().getEncoded()));

			return rsaKey;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 私钥解密过程
	 *
	 * @param privateKey 私钥
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static byte[] decrypt(String privateKey, byte[] cipherData)			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}



		Cipher cipher = null;
		try {
			// 使用默认RSA
			BASE64Decoder base64 = new BASE64Decoder();
			byte[] buffer = base64.decodeBuffer(privateKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKey rsaPrivateKey = (RSAPrivateKey)keyFactory.generatePrivate(new PKCS8EncodedKeySpec(buffer));
			//PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}



	/**
	 * 字节数据转十六进制字符串
	 *
	 * @param data 输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}
	/**
	 * 将byte[] 转换成字符串
	 */
	public static String byte2Hex(byte[] srcBytes) {
		StringBuilder hexRetSB = new StringBuilder();
		for (byte b : srcBytes) {
			String hexString = Integer.toHexString(0x00ff & b);
			hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
		}
		return hexRetSB.toString();
	}


	/**
	 *
	 */
	public static byte[] hex2Bytes(String source) {
		byte[] sourceBytes = new byte[source.length() / 2];
		for (int i = 0; i < sourceBytes.length; i++) {
			sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
		}
		return sourceBytes;
	}
}
