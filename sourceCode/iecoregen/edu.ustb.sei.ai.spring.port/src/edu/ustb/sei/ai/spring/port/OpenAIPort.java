package edu.ustb.sei.ai.spring.port;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.CallResponseSpec;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

import edu.ustb.sei.ai.AbstractLLMPort;

public class OpenAIPort extends AbstractLLMPort<ChatClientRequestSpec, CallResponseSpec>{
	
	protected final ChatClient chatClient;

	public OpenAIPort(String apiKey, Options options) {
		super(apiKey, options);
		OpenAiApi openAiApi = OpenAiApi.builder()
				.baseUrl(url)
	            .apiKey(apiKey)
	            .build();
		
		OpenAiChatOptions openAiChatOptions = OpenAiChatOptions.builder()
	            .model(model)
	            .temperature(0.7)
	            .maxTokens(maxInputTokens)
	            .maxCompletionTokens(maxOutputTokens)
	            .build();
		
		OpenAiChatModel chatModel = OpenAiChatModel.builder()
				.openAiApi(openAiApi)
				.defaultOptions(openAiChatOptions)
				.build();
		
		this.chatClient = addTools(ChatClient.builder(chatModel))
				.build();
	}
	
	public ChatClient.Builder addTools(ChatClient.Builder builder) {
		return builder;
	}

	@Override
	protected CallResponseSpec doRequest(ChatClientRequestSpec request) {
		return request.call();
	}

	@Override
	protected List<String> getChoices(CallResponseSpec response) {
		return List.of(response.content());
	}

	@Override
	protected ChatClientRequestSpec buildRequest(String prompt) {
		return this.chatClient.prompt()
                .user(prompt);
	}

}
