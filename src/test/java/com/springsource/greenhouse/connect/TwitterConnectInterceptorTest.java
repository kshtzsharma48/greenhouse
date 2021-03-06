package com.springsource.greenhouse.connect;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.UriTemplate;

import com.springsource.greenhouse.account.Account;
import com.springsource.greenhouse.account.AccountUtils;

public class TwitterConnectInterceptorTest {
	
	private MockHttpServletRequest httpServletRequest;
	
	private TwitterConnectInterceptor interceptor;
	
	private ServletWebRequest request;

	@Before
	public void setup() {
		interceptor = new TwitterConnectInterceptor();
		httpServletRequest = new MockHttpServletRequest();
		request = new ServletWebRequest(httpServletRequest);
	}

	@Test
	public void preConnect() {
		httpServletRequest.setParameter("postTweet", "true");
		interceptor.preConnect(null, null, request);
		Boolean postTweetAttributeValue = (Boolean) request.getAttribute("twitterConnect.postTweet", WebRequest.SCOPE_SESSION);
		assertNotNull(postTweetAttributeValue);
		assertTrue(postTweetAttributeValue);
	}

	@Test
	public void preConnect_noPostTweetParameter() {
		interceptor.preConnect(null, null, request);
		Boolean postTweetAttributeValue = (Boolean) request.getAttribute("twitterConnect.postTweet", WebRequest.SCOPE_SESSION);
		assertNull(postTweetAttributeValue);
	}

	@Test
	public void postConnect() {
		request.setAttribute("twitterConnect.postTweet", Boolean.TRUE, WebRequest.SCOPE_SESSION);
		Account account = new Account(2L, "Craig", "Walls", "cwalls@vmware.com", "habuma", "http://picture.com/url", new UriTemplate("http://greenhouse.springsource.org/members/{profile}"));
		AccountUtils.signin(account);
		//TwitterApi twitterApi = Mockito.mock(TwitterApi.class);
		//StubServiceProviderConnection<TwitterApi> connection = new StubServiceProviderConnection<TwitterApi>(twitterApi);
		//interceptor.postConnect(connection, request);
		//Mockito.verify(twitterApi).timelineOperations().updateStatus("Join me at the Greenhouse! http://greenhouse.springsource.org/members/habuma");
	}

	@Test
	public void postConnect_noPostTweetAttribute() {
		Account account = new Account(2L, "Craig", "Walls", "cwalls@vmware.com", "habuma", "http://picture.com/url", new UriTemplate("http://greenhouse.springsource.org/members/{profile}"));
		AccountUtils.signin(account);
		//TwitterApi twitterApi = Mockito.mock(TwitterApi.class);
		//StubServiceProviderConnection<TwitterApi> connection = new StubServiceProviderConnection<TwitterApi>(twitterApi);
		//interceptor.postConnect(connection, request);
		//Mockito.verifyZeroInteractions(twitterApi);
	}

}