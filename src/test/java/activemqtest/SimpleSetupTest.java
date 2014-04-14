package activemqtest;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleSetupTest {

	private ConnectionFactory connectionFactory;

	@Before
	public void setUp() throws Exception {
		connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
	}

	@Test
	public void testSendAndReceiveMessagesInASingleQueue() throws Exception {
		Connection connection = connectionFactory.createConnection();

		connection.start();

		// Create a Session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// Create a temporary Queue
		final Queue queue = session.createTemporaryQueue();

		// Send a message to this queue
		{
			final MessageProducer producer = session.createProducer(queue);
			final TextMessage message = session.createTextMessage("testing");
			producer.send(message);
		}

		// Read a message from this queue and check for the expected value
		{
			final MessageConsumer consumer = session.createConsumer(queue);
			final TextMessage message = (TextMessage) consumer.receiveNoWait();

			Assert.assertNotNull(message);
			Assert.assertEquals("testing", message.getText());
		}

		// Clean up
		session.close();
		connection.close();
	}

}
