package com.hj.util;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
public class SMTPUtil {

	public static void main(String[] args) {
		String emailAddress = "951847751@qq.com";
		String title = "验证绑定邮箱";
		String code = "123456";
		sample(emailAddress,title,code);
	}
	 
    public static void sample(String emailAddress,String title,String code) {        
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIvSsCpi35hqfA", "yXGciyqfFvdPijTHTuOnXEKQmCVxtb");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
        	//request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            request.setAccountName("kefumessage@www.hjacdq.top");
            request.setFromAlias("hjacdq");
            request.setAddressType(1);
            request.setTagName("mail");
            request.setReplyToAddress(true);
            request.setToAddress(emailAddress);
            request.setSubject(title);
            request.setHtmlBody("<div style=\"font-size: 16px;margin-bottom: 43px;\">"
            		+"您好，您的验证码为：<span style=\"color:#ff2a00;\">【"+code+"】</span>，五分钟内有效，请马上进行验证。"
            		+"<br><div style=\"text-align:left; margin-left:80px; margin-top:10px;\">若非本人操作，请忽略此邮件。</div></div>");
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            System.out.println("EnvId="+ httpResponse.getEnvId());
            System.out.println("RequestId="+  httpResponse.getRequestId());
        } catch (ServerException e) {
            e.printStackTrace();
        }
        catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
