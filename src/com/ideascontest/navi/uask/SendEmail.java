package com.ideascontest.navi.uask;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;


/**
 * @author divahar
 *
 */

public class SendEmail
{
	public SendEmail() {}

	public static boolean composeEmail(String to, String question, String answer)
	{
		String CLIENT_TOKEN = "AKIAILATVZDHGSNJQ22Q";
		String CLIENT_SECRET = "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";

		String subjectStr = question + " has been answered";
		String bodyStr = "Hi,\n\n Please find the answer posted below:\n\n " + 
				answer + " \n\n Cheers,\n Team uAsk";

		com.amazonaws.auth.BasicAWSCredentials CREDENTIALS = new com.amazonaws.auth.BasicAWSCredentials(CLIENT_TOKEN, CLIENT_SECRET);


		com.amazonaws.services.simpleemail.model.Destination destination = new com.amazonaws.services.simpleemail.model.Destination().withToAddresses(new String[] { to });


		Content subject = new Content().withData(subjectStr);
		Content textBody = new Content().withData(bodyStr);
		Body body = new Body().withText(textBody);


		Message message = new Message().withSubject(subject).withBody(body);


		SendEmailRequest request = new SendEmailRequest().withSource("divahar90@gmail.com").withDestination(destination).withMessage(message);

		try
		{
			AmazonSimpleEmailServiceClient client = 
					new AmazonSimpleEmailServiceClient(CREDENTIALS);


			client.sendEmail(request);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}

		return true;
	}
}