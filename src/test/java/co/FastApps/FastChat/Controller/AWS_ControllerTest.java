//package co.FastApps.FastChat.Controller;
//
//
//import co.FastApps.FastChat.Entity.EndResult;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class AWS_ControllerTest {
////	@Autowired
////	private
////	AWS_Controller aws_controller;
////
////
////	@Test
////	public void testControllerOrganization() {
////		String inputText = "Who works at amazon";
////
////		EndResult outcome = aws_controller.listEntity(inputText,10);
////		assertThat(outcome.getPlainText(), is(equalTo("The Country for Amazon is located in Canada.The company is: Amazon.The company is: Amazon.The call you have is with: Bob from Amazon at 2110-06-01 04:04:34.0.The Lead is: Jim from Amazon.The Contact info I found is: Bob, You can contact him/her at 647-548-4549 and by email at Bob@amazon.ca.The Asset is Bob  and it says: lead for AWS Comprehend.")));
////
////
////		assertThat(outcome.getResultList().get(0).get(0).get("Organization"), is(equalTo("Amazon")));
////	}
////
////	@Test
////	public void testControllerLocation() {
////		String inputText = "Who works in toronto";
////
////		EndResult outcome = aws_controller.listEntity(inputText,10);
////		assertThat(outcome.getPlainText(), is(equalTo("The Country for Amazon is located in Canada.The company is: " +
////				"Amazon.The Employee is: Lincoln his/her id is 1.The Employee is: Luke his/her id is 123.The Employee is: Karen his/her id is 323.The Employee is: Andrew his/her id is 3424.The Employee is: James his/her id is 232.The Contact info I found is: Bob, You can contact him/her at 647-548-4549 and by email at Bob@amazon.ca.")));
////
////		assertThat(outcome.getResultList().get(0).get(0).get("City"), is(equalTo("Toronto")));
////	}
////
////
////	@Test
////	public void testControllerDate() {
////		String inputText = "What is happening on 2018-05-31";
////
////		EndResult outcome = aws_controller.listEntity(inputText,10);
////		assertThat(outcome.getPlainText(), is(equalTo("Your current task is: Tomcat Server.The Employee is: Lincoln his/her id is 1.The Document you have is of type: NDA.")));
////
////		//Expected: is "2018-05-31"
////		//     but: was <2018-05-31>
////		//assertThat(outcome.getResultList().get(0).get(0).get("Date"),is(equalTo("2018-05-31")));
////	}
////
////	@Test
////	public void testControllerQuantity() {
////		String inputText = "What was the $200 spent on ";
////
////		EndResult outcome = aws_controller.listEntity(inputText,10);
////		assertThat(outcome.getPlainText(), is(equalTo("The expense is: Travel, it cost $200.0.")));
////
////		assertThat(outcome.getResultList().get(0).get(0).get("Cost"), is(equalTo(200.0)));
////	}
////
////	@Test
////	public void testControllerPerson() {
////		String inputText = "Tell me about Lincoln";
////
////		EndResult outcome = aws_controller.listEntity(inputText,10);
////		assertThat(outcome.getPlainText(), is(equalTo("The Employee is: Lincoln his/her id is 1.")));
////
////		assertThat(outcome.getResultList().get(0).get(0).get("Name"), is(equalTo("Lincoln")));
////	}
////
////	@Test
////	public void testControllerEvent() {
////		String inputText = "tell me about St Patrick's Day";
////
////		EndResult outcome = aws_controller.listEntity(inputText,10);
////		assertThat(outcome.getPlainText(), is(equalTo("The event upcoming is: St Patrick's Day on 2057-08-16.")));
////
////		assertThat(outcome.getResultList().get(0).get(0).get("Name"), is(equalTo("St Patrick's Day")));
////	}
////
////	@Test
////	public void testControllerCOMMERCIAL_ITEM() {
////		String inputText = "how many qc35 do we own";
////
////		EndResult outcome = aws_controller.listEntity(inputText,10);
////		assertThat(outcome.getPlainText(), is(equalTo("The Inventory for: qc35 is 34.")));
////
////		assertThat(outcome.getResultList().get(0).get(0).get("Name"), is(equalTo("qc35")));
////	}
////
//
//
//}