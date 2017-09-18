package com.shirodemo.test;

import com.sun.javafx.css.converters.EnumConverter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;
import java.security.Key;


/**
 * class5: http://jinnianshilongnian.iteye.com/blog/2021439
 *  加密
 */
public class Class5Test {

    @Test
    public void testBase64Encoded(){
        String str = "abc";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encoded);
        System.out.println("base64Encoded:"+base64Encoded+" ,str2:"+str2);
        Assert.assertEquals(str,str2);
    }

    @Test
    public void testHexEncode(){
        String str = "abc";
        String base64Encoded = Hex.encodeToString(str.getBytes());
        String str2 = Base64.decodeToString(base64Encoded);
        System.out.println("base64Encoded:"+base64Encoded+" ,str2:"+str2);
        Assert.assertEquals(str,str2);
    }

    /**
     * 散列算法 MD5、SHA等
     *  shiro还提交了通用散列支持 SimpleHash,内部使用MessageDigest
     */
    @Test
    public void testSaltEncode(){
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str,salt,2).toBase64();//also can convert to toBase64()/toHex()
        System.out.println(md5);

        String sha = new Sha256Hash(str,salt).toString();// also can use SHA1、SHA512
        System.out.println(sha);

        //SimpleHash
        String simpleHash = new SimpleHash("SHA-1",str,salt).toBase64();
        System.out.println(simpleHash);
    }

    /**
     * 为了方便使用，shiro提供了HashService，默认提供了DefaultHashService实现
     *  1、首先创建一个DefaultHashService，默认使用SHA-512算法；
     *  2、可以通过hashAlgorithmName属性修改算法；
     *  3、可以通过privateSalt设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐；
     *  4、可以通过generatePublicSalt属性在用户没有传入公盐的情况下是否生成公盐；
     *  5、可以设置randomNumberGenerator用于生成公盐；
     *  6、可以设置hashIterations属性来修改默认加密迭代次数；
     *  7、需要构建一个HashRequest，传入算法、数据、公盐、迭代次数。
     */
    @Test
    public void testHashService(){
        DefaultHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123"));//私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐
        hashService.setHashIterations(1);//生成Hash值的迭代次数

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        System.out.println(hex);
    }

    /**
     *
     SecureRandomNumberGenerator 用于生成一个随机数：
     */
    @Test
    public void testRandomNum(){
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        String hex = randomNumberGenerator.nextBytes().toHex();
        System.out.println(hex);
    }

    //【加密/解密】
    //  Shiro还提供对称式加密/解密算法的支持，如AES、Blowfish等；当前还没有提供对非对称加密/解密算法支持，未来版本可能提供。

    @Test
    public void testAES(){
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128);
        //生成key
        Key key = aesCipherService.generateNewKey();
        String text = "hello";
        //加密
        String encrpText = aesCipherService.encrypt(text.getBytes(),key.getEncoded()).toHex();
        //解密
        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrpText),key.getEncoded()).getBytes());
        System.out.println("encrpText:"+encrpText+" ,text2:"+text2);
    }

    //【5.4 PasswordService/CredentialsMatcher
    //  Shiro提供了PasswordService及CredentialsMatcher用于提供加密密码及验证密码服务。】
    //  Shiro默认提供了PasswordService实现DefaultPasswordService；CredentialsMatcher实现PasswordMatcher及HashedCredentialsMatcher（更强大）。

    /**
     * 2.1、passwordService使用DefaultPasswordService，如果有必要也可以自定义；
     * 2.2、hashService定义散列密码使用的HashService，默认使用DefaultHashService（默认SHA-256算法）；
     * 2.3、hashFormat用于对散列出的值进行格式化，默认使用Shiro1CryptFormat，另外提供了Base64Format和HexFormat，对于有salt的密码请自定义实现ParsableHashFormat然后把salt格式化到散列值中；
     * 2.4、hashFormatFactory用于根据散列值得到散列的密码和salt；因为如果使用如SHA算法，那么会生成一个salt，此salt需要保存到散列后的值中以便之后与传入的密码比较时使用；默认使用DefaultHashFormatFactory；
     * 2.5、passwordMatcher使用PasswordMatcher，其是一个CredentialsMatcher实现；
     * 2.6、将credentialsMatcher赋值给myRealm，myRealm间接继承了AuthenticatingRealm，其在调用getAuthenticationInfo方法获取到AuthenticationInfo信息后，会使用credentialsMatcher来验证凭据是否匹配，如果不匹配将抛出IncorrectCredentialsException异常
     */
    @Test
    public void testPasswordRealm(){
        login("classpath:class5/shiro-passwordservice.ini","wu","123");
    }


    @Test
    public void testJdbcRealm(){
        login("classpath:class5/shiro-jdbc-passwordservice.ini","wu","123");
    }

    @Test
    public void testHashedCredentialsMatcherWithMyRealm2(){
        login("classpath:class5/shiro-hashedCredentialsMatcher.ini","wu","123");
    }

    @Test
    public void testHashedCredentialsMatcherWithJdbcRealm(){
//        BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);
        login("classpath:class5/shiro-jdbc-hashedCredentialsMatcher.ini","wu","123");
    }


    //HashedCredentialsMatcher实现密码验证服务
    //  Shiro提供了CredentialsMatcher的散列实现HashedCredentialsMatcher，和之前的PasswordMatcher不同的是，它只用于密码验证，且可以提供自己的盐，而不是随机生成盐，且生成密码散列值的算法需要自己写，因为能提供自己的盐。

    @Test
    public void testMd5(){
        String algorithmName = "md5";
        String username = "liu";
        String password = "123";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;
        SimpleHash hash = new SimpleHash(algorithmName,password,salt1+salt2,hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(encodedPassword);

    }



    private void login(String configfile,String username,String password){
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configfile);
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        subject.login(token);
    }
}
